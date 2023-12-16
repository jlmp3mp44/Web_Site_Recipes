package com.kpi.recipes.api.controller.auth;

import com.kpi.recipes.api.exception.UserAlreadyExistException;
import com.kpi.recipes.api.model.LoginBody;
import com.kpi.recipes.api.model.LoginResponse;
import com.kpi.recipes.api.model.RegistrationBody;
import com.kpi.recipes.model.User;
import com.kpi.recipes.service.JWTService;
import com.kpi.recipes.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    private UserService userService;
    private JWTService jwtService;

    public AuthenticationController(UserService userService, JWTService jwtService) {

        this.userService = userService;
        this.jwtService =  jwtService;
    }

    @PostMapping("/register")
    public ResponseEntity registerUser(@Valid @RequestBody RegistrationBody registrationBody) {
        try {
            userService.registerUser(registrationBody);
            return ResponseEntity.ok().build();
        } catch (UserAlreadyExistException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> loginUser(@Valid @RequestBody LoginBody loginBody){
        String jwt =  userService.loginUser(loginBody);
        if(jwt ==  null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        else {
            LoginResponse loginResponse =  new LoginResponse();
            loginResponse.setJwt(jwt);
            return ResponseEntity.ok(loginResponse);
        }
    }
    @GetMapping("/me")
    public User getLoggedInUserProfile(@AuthenticationPrincipal User user){
        return user;
    }
    @PostMapping("/logout")
    public ResponseEntity logout(HttpServletRequest request) {
        String token = extractTokenFromRequest(request);
        return ResponseEntity.ok("Logout successful");
    }

    private String extractTokenFromRequest(HttpServletRequest request) {
        String tokenHeader = request.getHeader("Authorization");
        if (tokenHeader != null && tokenHeader.startsWith("Bearer ")) {
            return tokenHeader.substring(7);
        }
        return null;
    }
}

