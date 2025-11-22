package com.project.stockmaster.Controller;



import com.project.stockmaster.Service.AuthService;
import com.project.stockmaster.dto.AuthRequest;
import com.project.stockmaster.dto.AuthResponse;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public String register(@RequestBody AuthRequest request) {
        return authService.register(request);
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest request) {
        return authService.login(request);
    }

    @PostMapping("/send-otp")
    public String sendOtp(@RequestBody Map<String, String> request) {
        return authService.sendOtp(request.get("username"));
    }

    @PostMapping("/reset-password")
    public String resetPassword(@RequestBody Map<String, String> request) {
        return authService.resetPassword(
                request.get("username"),
                request.get("otp"),
                request.get("newPassword")
        );
    }
}
