package com.global_solution.api_gs_ecoTrack.domain.dto;

import com.global_solution.api_gs_ecoTrack.domain.Appliance;
import com.global_solution.api_gs_ecoTrack.domain.User;
import com.global_solution.api_gs_ecoTrack.domain.UserAppliance;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserApplianceDTO {
    private Long id;
    private Long user_id;
    @NotNull(message = "Eletrodoméstico é obrigatório")
    private Long appliance_id;
    private LocalDateTime associationDate;
    @NotNull(message = "Horas de uso por dia é obrigatório")
    private Double hoursUsedPerDay;
    @NotNull(message = "Dias de uso por semana é obrigatório")
    private Integer daysUsedPerWeek;

    public UserApplianceDTO(UserAppliance userAppliance) {
        this.id = userAppliance.getId();
        this.user_id = userAppliance.getUser().getId();
        this.appliance_id = userAppliance.getAppliance().getId();
        this.associationDate = userAppliance.getAssociationDate();
        this.hoursUsedPerDay = userAppliance.getHoursUsedPerDay();
        this.daysUsedPerWeek = userAppliance.getDaysUsedPerWeek();
    }
}
