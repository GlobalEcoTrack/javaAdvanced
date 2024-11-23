package com.global_solution.api_gs_ecoTrack.services;

import com.global_solution.api_gs_ecoTrack.domain.User;
import com.global_solution.api_gs_ecoTrack.domain.dto.UserDTO;
import com.global_solution.api_gs_ecoTrack.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public UserDTO insert(UserDTO userDTO) {
        User user = new User(userDTO);
        user = userRepository.save(user);
        return new UserDTO(user);
    }

    @Transactional
    public UserDTO insertWithProcedure(UserDTO userDTO) {
        User user = new User(userDTO);
        user = userRepository.eco_track_insert_user(
                user.getName(),
                user.getBirthDate(),
                user.getEmail(),
                user.getPassword(),
                user.getRole().name(),
                user.getState().getId()
        );
        return new UserDTO(user);
    }

    @Transactional(readOnly = true)
    public UserDTO findById(Long id) {
        return new UserDTO(userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("Conta não encontrada !!")));
    }

    @Transactional(readOnly = true)
    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Conta não encontrada !!"));
    }

    public User getUserContext() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

}
