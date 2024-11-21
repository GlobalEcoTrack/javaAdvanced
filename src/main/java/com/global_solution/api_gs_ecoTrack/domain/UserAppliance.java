package com.global_solution.api_gs_ecoTrack.domain;

import com.global_solution.api_gs_ecoTrack.domain.dto.UserApplianceDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "eco_track_tb_user_appliance")
public class UserAppliance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "appliance_id", nullable = false)
    private Appliance appliance;

    private LocalDateTime associationDate;

    private Double hoursUsedPerDay;

    private Integer daysUsedPerWeek;

    public UserAppliance(UserApplianceDTO userApplianceDTO) {
        this.id = userApplianceDTO.getId();
        this.associationDate = userApplianceDTO.getAssociationDate();
        this.hoursUsedPerDay = userApplianceDTO.getHoursUsedPerDay();
        this.daysUsedPerWeek = userApplianceDTO.getDaysUsedPerWeek();
    }
}