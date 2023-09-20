package com.plannerapp.controller;

import com.plannerapp.model.dto.LoginDTO;
import com.plannerapp.model.dto.RegistrationDTO;
import com.plannerapp.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
public class AuthController {

    private AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {this.authService = authService;}

    @ModelAttribute("registrationDTO")
    public RegistrationDTO registrationDTOInit(){
        return new RegistrationDTO();
    }

    @ModelAttribute("loginDTO")
    public LoginDTO loginDTOInit(){
        return new LoginDTO();
    }

    @ModelAttribute("badLogIn")
    public boolean badLogIn(){
        return false;
    }

    @GetMapping("/register")
    public String getRegister(){
        return "register";
    }

    @PostMapping("/register")
    public String postRegister(@Valid RegistrationDTO registrationDTO,
                               BindingResult bindingResult,
                               RedirectAttributes redirectAttributes){

        List<String> register = this.authService.validateRegister(registrationDTO);

        if(bindingResult.hasErrors() || !register.isEmpty()){

    redirectAttributes.addFlashAttribute("registrationDTO",registrationDTO);

    redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.registrationDTO"
            ,bindingResult);


       if(register.contains("username")){

           redirectAttributes.addFlashAttribute("badUsername","username");
       }
       if (register.contains("email")) {

           redirectAttributes.addFlashAttribute("badEmail","email");
       }
       if (register.contains("password")) {
           redirectAttributes.addFlashAttribute("badPassword","password");
    }

    return "redirect:/register";
}
this.authService.registerInfo(registrationDTO);
return "redirect:/login";
    }

    @GetMapping("/login")
    public String getLogin(){
        return "login";
    }

@PostMapping("/login")
public String postLogin(@Valid LoginDTO loginDTO,
                        BindingResult bindingResult,
                        RedirectAttributes redirectAttributes){

        if(bindingResult.hasErrors()){

            redirectAttributes.addFlashAttribute("loginDTO",loginDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.loginDTO",
                    bindingResult);

            return "redirect:/login";
        }

        if(!this.authService.logIn(loginDTO)){
            redirectAttributes.addFlashAttribute("loginDTO",loginDTO);
            redirectAttributes.addFlashAttribute("badLogIn",true);
            return "redirect:/login";
        }

        return "redirect:/home";
}

@GetMapping("/logout")
    public String getLogOut(){
        this.authService.logOut();
        return "redirect:/";
}
}
