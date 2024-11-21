package com.global_solution.api_gs_ecoTrack.domain.dto;

import com.global_solution.api_gs_ecoTrack.domain.UserAppliance;
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
    private Double minutesUsedPerDay;
    @NotNull(message = "Dias de uso por semana é obrigatório")
    private Integer daysUsedPerWeek;
    private Double totalConsumption;
    private Double totalCost;

    public UserApplianceDTO(UserAppliance userAppliance) {
        this.id = userAppliance.getId();
        this.user_id = userAppliance.getUser().getId();
        this.appliance_id = userAppliance.getAppliance().getId();
        this.associationDate = userAppliance.getAssociationDate();
        this.minutesUsedPerDay = userAppliance.getMinutesUsedPerDay();
        this.daysUsedPerWeek = userAppliance.getDaysUsedPerWeek();
        this.totalConsumption = userAppliance.getTotalConsumption();
        this.totalCost = userAppliance.getTotalCost();
    }
}
