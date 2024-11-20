package com.global_solution.api_gs_ecoTrack.domain;

import com.global_solution.api_gs_ecoTrack.domain.dto.UserDTO;
import com.global_solution.api_gs_ecoTrack.domain.enums.UserRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "eco_track_tb_user")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private LocalDate birthDate;
    @Column(unique = true)
    private String email;
    private String password;

    @ManyToOne
    private State state;

    @Enumerated(EnumType.STRING)
    private UserRole role;

//    @ManyToMany(fetch = FetchType.EAGER)
    @ManyToMany
    @JoinTable(name = "eco_track_tb_user_appliance",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "appliance_id"))
    private Set<Appliance> appliances;

    public User(UserDTO userDTO) {
        this.id = userDTO.getId();
        this.name = userDTO.getName();
        this.birthDate = userDTO.getBirthDate();
        this.email = userDTO.getEmail();
        this.password = userDTO.getPassword();
        this.role = userDTO.getRole();
        this.state = userDTO.getState();
    }

    public User(String email, String password, String name, LocalDate birthDate, UserRole role) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.birthDate = birthDate;
        this.role = role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getUsername() {
        return this.getEmail();
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}
