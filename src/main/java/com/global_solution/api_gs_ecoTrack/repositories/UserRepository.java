package com.global_solution.api_gs_ecoTrack.repositories;

import com.global_solution.api_gs_ecoTrack.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    @Procedure(name = "eco_track_insert_user")
    User eco_track_insert_user(
            @Param("p_name") String name,
            @Param("p_birth_date") LocalDate birth_date,
            @Param("p_email") String email,
            @Param("p_password") String password,
            @Param("p_role") String role,
            @Param("p_state_id") Long state_id
    );
}
