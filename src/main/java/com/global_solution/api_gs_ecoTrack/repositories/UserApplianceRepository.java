package com.global_solution.api_gs_ecoTrack.repositories;

import com.global_solution.api_gs_ecoTrack.domain.State;
import com.global_solution.api_gs_ecoTrack.domain.UserAppliance;
import com.global_solution.api_gs_ecoTrack.domain.projections.UserApplianceReportProjection;
import com.global_solution.api_gs_ecoTrack.domain.projections.UserAppliancesByMonthYearReportProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface UserApplianceRepository extends JpaRepository<UserAppliance, Long> {
    List<UserAppliance> findByUserId(Long userId);

    List<UserAppliance> findAllByUserIdAndApplianceId(Long userId, Long applianceId);

    @Procedure(name = "eco_track_insert_user_appliance")
    UserAppliance eco_track_insert_user_appliance(
            @Param("p_association_date") LocalDateTime association_date,
            @Param("p_minutes_used_per_day") Double minutes_used_per_day,
            @Param("p_days_used_per_week") Integer days_used_per_week,
            @Param("p_total_consumption") Double total_consumption,
            @Param("p_total_cost") Double total_cost,
            @Param("p_user_id") Long user_id,
            @Param("p_appliance_id") Long appliance_id
    );

    @Query(nativeQuery = true, value = """
                    SELECT
                        APPLIANCE_ID,
                        COUNT(*) AS QUANTITY,
                        ROUND(SUM(TOTAL_CONSUMPTION), 2) AS TOTAL_CONSUMPTION,
                        ROUND(SUM(TOTAL_COST), 2) AS TOTAL_COST
                    FROM
                        ECO_TRACK_TB_USER_APPLIANCE
                    WHERE
                        USER_ID = :userId
                    GROUP BY
                        APPLIANCE_ID
                    ORDER BY
                        APPLIANCE_ID
            """)
    List<UserApplianceReportProjection> getAppliancesReport(Long userId);

    @Query(nativeQuery = true, value = """
            SELECT
                  TO_CHAR(ASSOCIATION_DATE, 'YYYY-MM') AS MONTH_YEAR,
                  ROUND(SUM(TOTAL_CONSUMPTION), 2) AS TOTAL_CONSUMPTION,
                  ROUND(SUM(TOTAL_COST), 2) AS TOTAL_COST
              FROM
                  ECO_TRACK_TB_USER_APPLIANCE
              WHERE
                  USER_ID = :userId
              GROUP BY
                  TO_CHAR(ASSOCIATION_DATE, 'YYYY-MM')
              ORDER BY
                  TO_CHAR(ASSOCIATION_DATE, 'YYYY-MM')
            """)
    List<UserAppliancesByMonthYearReportProjection> getMonthYearReport(Long userId);

}
