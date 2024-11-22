package com.global_solution.api_gs_ecoTrack.repositories;

import com.global_solution.api_gs_ecoTrack.domain.UserAppliance;
import com.global_solution.api_gs_ecoTrack.domain.projections.UserApplianceReportProjection;
import com.global_solution.api_gs_ecoTrack.domain.projections.UserAppliancesByMonthYearReportProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserApplianceRepository extends JpaRepository<UserAppliance, Long> {
    List<UserAppliance> findByUserId(Long userId);

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
