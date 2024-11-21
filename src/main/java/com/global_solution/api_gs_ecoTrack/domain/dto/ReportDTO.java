package com.global_solution.api_gs_ecoTrack.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReportDTO {
    private Long appliance_id;
    private Integer quantity;
    private Double totalConsumption;
    private Double totalCost;
}
