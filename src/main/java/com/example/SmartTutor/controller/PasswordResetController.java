package com.example.SmartTutor.controller;

import com.example.SmartTutor.dto.ForgotPasswordRequest;
import com.example.SmartTutor.dto.ResetPasswordRequest;
import com.example.SmartTutor.dto.PasswordResetResponse;
import com.example.SmartTutor.model.PasswordResetToken;
import com.example.SmartTutor.model.User;
import com.example.SmartTutor.repository.PasswordResetTokenRepository;
import com.example.SmartTutor.service.EmailService;
import com.example.SmartTutor.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Base64;

@RestController
@RequestMapping("/api/auth")
public class PasswordResetController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordResetTokenRepository tokenRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;

    // Step 1: Request reset
    @PostMapping("/forgot-password")
    public ResponseEntity<PasswordResetResponse> forgotPassword(@RequestBody ForgotPasswordRequest request) {
        User user = userService.findByEmail(request.getEmail());

        // Delete old tokens for this user
        tokenRepository.deleteByUserId(user.getId());

        // Generate new token
        String token = generateToken();
        PasswordResetToken resetToken = new PasswordResetToken(
                user.getId(),
                token,
                LocalDateTime.now().plusMinutes(15)
        );
        tokenRepository.save(resetToken);

        // Send email
        emailService.sendPasswordResetEmail(user.getEmail(), token);

        return ResponseEntity.ok(new PasswordResetResponse("Password reset token sent to your email."));
    }

    // Step 2: Reset password
    @PostMapping("/reset-password")
    public ResponseEntity<PasswordResetResponse> resetPassword(@RequestBody ResetPasswordRequest request) {
        PasswordResetToken resetToken = tokenRepository.findByToken(request.getToken())
                .orElseThrow(() -> new RuntimeException("Invalid token"));

        if (resetToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            return ResponseEntity.badRequest().body(new PasswordResetResponse("Token expired"));
        }
        if (request.getNewPassword() == null || request.getNewPassword().isEmpty()) {
            return ResponseEntity.badRequest().body(new PasswordResetResponse("New password cannot be empty"));
        }


        User user = userService.findById(resetToken.getUserId());
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userService.saveUser(user);

        tokenRepository.delete(resetToken);

        return ResponseEntity.ok(new PasswordResetResponse("Password reset successful"));
    }

    private String generateToken() {
        byte[] randomBytes = new byte[32];
        new SecureRandom().nextBytes(randomBytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes);
    }
}
