package com.global_solution.api_gs_ecoTrack.domain.projections;

public interface UserApplianceReportProjection {
    Long getApplianceId();

    Integer getQuantity();

    Double getTotalConsumption();

    Double getTotalCost();
}
