package com.plannerapp.service;

import com.plannerapp.model.dto.LoginDTO;
import com.plannerapp.model.dto.RegistrationDTO;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.List;

@Service
public interface AuthService {

    List<String> validateRegister(RegistrationDTO registrationDTO);

    void registerInfo(RegistrationDTO registrationDTO);

    boolean logIn(LoginDTO loginDTO);

    void logOut();

}
