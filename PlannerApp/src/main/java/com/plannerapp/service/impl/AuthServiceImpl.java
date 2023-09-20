package com.plannerapp.service.impl;

import com.plannerapp.model.dto.LoginDTO;
import com.plannerapp.model.dto.RegistrationDTO;
import com.plannerapp.model.entity.User;
import com.plannerapp.repo.UserRepository;
import com.plannerapp.service.AuthService;
import com.plannerapp.session.LoggedUser;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {

    private UserRepository userRepository;

    private ModelMapper modelMapper;

    private LoggedUser loggedUser;

    @Autowired
    public AuthServiceImpl(UserRepository userRepository,
                           ModelMapper modelMapper,
                           LoggedUser loggedUser) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.loggedUser = loggedUser;
    }

    public List<String> validateRegister(RegistrationDTO registrationDTO){


            Optional<User> findByEmail = this.userRepository.findByEmail(registrationDTO.getEmail());
            Optional<User> findByUsername = this.userRepository.findByUsername(registrationDTO.getUsername());

            List<String> invalidFields = new ArrayList<>();


            if (findByEmail.isPresent()) {
                invalidFields.add("email");
            }
            if (findByUsername.isPresent()) {
                invalidFields.add("username");
            }
            if (!registrationDTO.getPassword().equals(registrationDTO.getConfirmPassword())) {
                invalidFields.add("password");
            }
                return invalidFields;
    }
    public void registerInfo(RegistrationDTO registrationDTO){

        this.userRepository.save(this.modelMapper.map(registrationDTO,User.class));
    }

    public boolean logIn(LoginDTO loginDTO){

        Optional<User>findByUsername = this.userRepository.findByUsername(loginDTO.getUsername());

        if(findByUsername.isEmpty()){
            return false;
        }
        if(!findByUsername.get().getPassword().equals(loginDTO.getPassword())){
            return false;
        }

       User user =  findByUsername.get();

        this.loggedUser.logIn(user.getId(), user.getUsername());

        return true;
    }

    public void logOut(){
        this.loggedUser.logOut();
    }
}
