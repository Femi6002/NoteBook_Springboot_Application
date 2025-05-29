package com.example.notebook.dto;

import lombok.Data;

@Data

public class LoginDTO {

    private String username;

    private String email;

    private String password;

    public LoginDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public static LoginDTO fromUsername(String username, String password) {
        LoginDTO dto = new LoginDTO(null, password); // email is null
        dto.username = username;
        return dto;
    }


}
