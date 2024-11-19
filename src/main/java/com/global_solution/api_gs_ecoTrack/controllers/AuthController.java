package com.global_solution.api_gs_ecoTrack.controllers;

import com.global_solution.api_gs_ecoTrack.domain.dto.LoginRequestDTO;
import com.global_solution.api_gs_ecoTrack.domain.dto.LoginResponseDTO;
import com.global_solution.api_gs_ecoTrack.domain.dto.RegisterReponseDTO;
import com.global_solution.api_gs_ecoTrack.domain.dto.RegisterRequestDTO;
import com.global_solution.api_gs_ecoTrack.services.AuthService;
import com.global_solution.api_gs_ecoTrack.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO data) {
        return ResponseEntity.ok(this.authService.login(data));
    }

    @PostMapping("/signup")
    public ResponseEntity<RegisterReponseDTO> register(@Valid @RequestBody RegisterRequestDTO data) {
        RegisterReponseDTO account = this.authService.signup(data);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{email}")
                .buildAndExpand(account.email()).toUri();
        return ResponseEntity.created(uri).body(account);
    }

}
