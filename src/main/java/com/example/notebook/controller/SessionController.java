package com.example.notebook.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SessionController {

    @GetMapping("session")
    public String details(HttpServletRequest request){
        StringBuilder details = new StringBuilder();
        details.append("Session ID: ").append(request.getSession().getId());
        details.append("\nCsrf token: ");

        return details.toString() + request.getHeaders("X-CSRF-TOKEN");
    }

}
