package com.project.stockmaster.dto;

import lombok.Data;

@Data
public class AuthRequest {
    private String username;
    private String password;
    private String fullName;  // only used during register
    private String role;
    private String email; // new field for signup

}
