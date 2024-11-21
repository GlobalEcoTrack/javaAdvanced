package com.global_solution.api_gs_ecoTrack.domain.projections;

public interface UserAppliancesByMonthYearReportProjection {
    String getMonthYear();

    Double getTotalConsumption();

    Double getTotalCost();
}
