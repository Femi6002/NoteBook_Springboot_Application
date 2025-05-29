package com.example.notebook.service;

import com.example.notebook.auth.model.User;
import com.example.notebook.dto.RegisterDTO;

public interface RegisterService {

    User newUser(RegisterDTO registerDTO);

}
