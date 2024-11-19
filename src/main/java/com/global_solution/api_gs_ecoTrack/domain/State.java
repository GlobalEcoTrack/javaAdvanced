package com.global_solution.api_gs_ecoTrack.domain;

import com.global_solution.api_gs_ecoTrack.domain.dto.StateDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_state")
public class State {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String name;
    @Column(unique = true)
    private String abbreviation;

    @OneToMany(mappedBy = "state")
    private List<User> users;

    public State(StateDTO stateDTO) {
        this.id = stateDTO.getId();
        this.name = stateDTO.getName();
        this.abbreviation = stateDTO.getAbbreviation();
    }
}
