package com.project.stockmaster.Service;









import com.project.stockmaster.Models.User;
import com.project.stockmaster.Models.enums.Role;
import com.project.stockmaster.dto.AuthRequest;
import com.project.stockmaster.dto.AuthResponse;
import com.project.stockmaster.repository.UserRepository;
import com.project.stockmaster.security.JwtUtil;
import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final EmailService emailService;

    private final Map<String, String> otpStore = new HashMap<>();
    private final Random random = new Random();

    // REGISTER
    public String register(AuthRequest request) {

        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username already exists");
        }

        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .fullName(request.getFullName())
                .email(request.getEmail())
                .role(Role.valueOf(request.getRole()))
                .build();

        userRepository.save(user);

        return "User registered successfully";
    }

    // LOGIN
    public AuthResponse login(AuthRequest request) {

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("Invalid username"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        String token = jwtUtil.generateToken(user.getUsername());

        return new AuthResponse(token, user.getUsername(), user.getRole().name());
    }

    // SEND OTP TO EMAIL
    public String sendOtp(String username) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String otp = String.format("%06d", random.nextInt(999999));
        otpStore.put(username, otp);

        String subject = "StockMaster Password Reset OTP";
        String body = "Your OTP for password reset is: " + otp + "\n\nValid for 5 minutes.";

        emailService.sendEmail(user.getEmail(), subject, body);

        return "OTP sent to your email";
    }

    // RESET PASSWORD USING OTP
    public String resetPassword(String username, String otp, String newPassword) {

        if (!otpStore.containsKey(username)) {
            throw new RuntimeException("OTP not generated");
        }

        if (!otpStore.get(username).equals(otp)) {
            throw new RuntimeException("Invalid OTP");
        }

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        otpStore.remove(username);

        return "Password updated successfully";
    }
}