package com.global_solution.api_gs_ecoTrack.domain.dto;

import com.global_solution.api_gs_ecoTrack.domain.projections.UserApplianceReportProjection;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReportDTO {
    private Double totalConsumption = 0.0;
    private Double totalCost = 0.0;
    private List<UserApplianceReportProjection> reportUserApplianceList;
}
