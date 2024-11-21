package com.global_solution.api_gs_ecoTrack.repositories;

import com.global_solution.api_gs_ecoTrack.domain.UserAppliance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserApplianceRepository extends JpaRepository<UserAppliance, Long> {
    List<UserAppliance> findByUserId(Long userId);
}
