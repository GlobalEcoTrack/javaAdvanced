package com.global_solution.api_gs_ecoTrack.repositories;

import com.global_solution.api_gs_ecoTrack.domain.Appliance;
import com.global_solution.api_gs_ecoTrack.domain.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

public interface ApplianceRepository extends JpaRepository<Appliance, Long> {
    @Procedure(name = "eco_track_insert_appliance")
    Appliance eco_track_insert_appliance(
            @Param("p_name") String name,
            @Param("p_kw") Double kw
    );
}
