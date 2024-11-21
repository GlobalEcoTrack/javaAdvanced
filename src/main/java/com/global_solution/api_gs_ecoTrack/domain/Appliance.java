package com.global_solution.api_gs_ecoTrack.domain;

import com.global_solution.api_gs_ecoTrack.domain.dto.ApplianceDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "eco_track_tb_appliance")
public class Appliance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String name;
    private Double kwPerHour;

    @OneToMany(mappedBy = "appliance", cascade = CascadeType.ALL)
    private List<UserAppliance> userAppliances;

    public Appliance(ApplianceDTO applianceDTO) {
        this.id = applianceDTO.getId();
        this.name = applianceDTO.getName();
        this.kwPerHour = applianceDTO.getKwPerHour();
    }
}
