package com.global_solution.api_gs_ecoTrack.repositories;

import com.global_solution.api_gs_ecoTrack.domain.State;
import com.global_solution.api_gs_ecoTrack.domain.dto.StateDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface StateRepository extends JpaRepository<State, Long> {
    Optional<State> findByAbbreviation(String abbreviation);

    @Procedure(name = "eco_track_insert_state")
    State eco_track_insert_state(
            @Param("p_name") String name,
            @Param("p_abbreviation") String abbreviation,
            @Param("p_price_kwh") Double price_kwh
    );

}
