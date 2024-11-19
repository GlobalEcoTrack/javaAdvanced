package com.global_solution.api_gs_ecoTrack.services;

import com.global_solution.api_gs_ecoTrack.domain.State;
import com.global_solution.api_gs_ecoTrack.domain.User;
import com.global_solution.api_gs_ecoTrack.domain.dto.*;
import com.global_solution.api_gs_ecoTrack.domain.enums.UserRole;
import com.global_solution.api_gs_ecoTrack.infra.security.TokenService;
import com.global_solution.api_gs_ecoTrack.repositories.UserRepository;
import com.global_solution.api_gs_ecoTrack.services.exceptions.InvalidCredentialsException;
import com.global_solution.api_gs_ecoTrack.services.exceptions.UserAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class AuthService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private StateService stateService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) {
        return userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Conta não encontrada !!"));
    }

    @Transactional(readOnly = true)
    public LoginResponseDTO login(LoginRequestDTO authDTO) {
        User user = (User) this.loadUserByUsername(authDTO.email());
        if (passwordEncoder.matches(authDTO.password(), user.getPassword())) {
            String token = this.tokenService.generateToken(user);
            return new LoginResponseDTO(user.getEmail(), token);
        } else {
            throw new InvalidCredentialsException("Senha incorreta!!");
        }
    }

    @Transactional
    public RegisterReponseDTO signup(RegisterRequestDTO authDTO) {
        if (this.userRepository.findByEmail(authDTO.email()).isEmpty()) {
            String encryptedPassword = passwordEncoder.encode(authDTO.password());
            UserDTO user = new UserDTO(new User(authDTO.email(), encryptedPassword, authDTO.name(), authDTO.birthDate(), UserRole.ADMIN));
            user.setState(new State(this.stateService.findByAbbreviation(authDTO.abbreviationState())));
            this.userService.insert(user);
            return new RegisterReponseDTO(authDTO.email(), authDTO.name());
        } else {
            throw new UserAlreadyExistsException("Conta já existente com este email.");
        }
    }

}
