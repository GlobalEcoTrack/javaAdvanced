package com.global_solution.api_gs_ecoTrack.services;

import com.global_solution.api_gs_ecoTrack.domain.Appliance;
import com.global_solution.api_gs_ecoTrack.domain.UserAppliance;
import com.global_solution.api_gs_ecoTrack.domain.dto.ReportDTO;
import com.global_solution.api_gs_ecoTrack.domain.dto.UserApplianceDTO;
import com.global_solution.api_gs_ecoTrack.domain.projections.UserAppliancesByMonthYearReportProjection;
import com.global_solution.api_gs_ecoTrack.repositories.UserApplianceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class UserApplianceService {

    @Autowired
    private UserApplianceRepository userApplianceRepository;

    @Autowired
    private ApplianceService applianceService;

    @Autowired
    private UserService userService;

    @Transactional
    public UserApplianceDTO insert(UserApplianceDTO userApplianceDTO) {
        UserAppliance userAppliance = new UserAppliance(userApplianceDTO);
        userAppliance.setAppliance(new Appliance(applianceService.findById(userApplianceDTO.getAppliance_id())));

        userAppliance.setUser(userService.getUserContext());

        userAppliance.setAssociationDate(LocalDateTime.now());

        Double totalConsumption = (userAppliance.getMinutesUsedPerDay() / 60) * (userAppliance.getDaysUsedPerWeek() * 4) * userAppliance.getAppliance().getKw();
        Double totalCost = totalConsumption * userAppliance.getUser().getState().getPrice_kwh();

        userAppliance.setTotalConsumption(totalConsumption);
        userAppliance.setTotalCost(totalCost);
        userAppliance = userApplianceRepository.save(userAppliance);
        return new UserApplianceDTO(userAppliance);
    }

    @Transactional
    public UserApplianceDTO insertWithProcedure(UserApplianceDTO userApplianceDTO) {
        UserAppliance userAppliance = new UserAppliance(userApplianceDTO);
        userAppliance.setAppliance(new Appliance(applianceService.findById(userApplianceDTO.getAppliance_id())));

        userAppliance.setUser(userService.getUserContext());

        userAppliance.setAssociationDate(LocalDateTime.now());

        Double totalConsumption = (userAppliance.getMinutesUsedPerDay() / 60) * (userAppliance.getDaysUsedPerWeek() * 4) * userAppliance.getAppliance().getKw();
        Double totalCost = totalConsumption * userAppliance.getUser().getState().getPrice_kwh();

        userAppliance.setTotalConsumption(totalConsumption);
        userAppliance.setTotalCost(totalCost);
        userAppliance = userApplianceRepository.eco_track_insert_user_appliance(
                userAppliance.getAssociationDate(),
                userAppliance.getMinutesUsedPerDay(),
                userAppliance.getDaysUsedPerWeek(),
                userAppliance.getTotalConsumption(),
                userAppliance.getTotalCost(),
                userAppliance.getUser().getId(),
                userAppliance.getAppliance().getId()
        );
        return new UserApplianceDTO(userAppliance);
    }

    @Transactional(readOnly = true)
    public List<UserApplianceDTO> findAll() {
        return userApplianceRepository.findAll().stream().map(UserApplianceDTO::new).toList();
    }

    @Transactional(readOnly = true)
    public List<UserApplianceDTO> findAllByUserIdAndApplianceId(Long applianceId) {
        return userApplianceRepository.findAllByUserIdAndApplianceId(this.userService.getUserContext().getId(), applianceId).stream().map(UserApplianceDTO::new).toList();
    }

    @Transactional(readOnly = true)
    public UserApplianceDTO findById(Long id) {
        UserAppliance userAppliance = userApplianceRepository.findById(id).orElseThrow(() -> new NoSuchElementException("UserAppliance not found"));
        return new UserApplianceDTO(userAppliance);
    }

    @Transactional(readOnly = true)
    public ReportDTO getUserAppliancesReport() {
        ReportDTO reportDTO = new ReportDTO();
        reportDTO.setReportUserApplianceList(userApplianceRepository.getAppliancesReport(userService.getUserContext().getId()));
        reportDTO.getReportUserApplianceList().forEach(userAppliance -> {
            reportDTO.setTotalConsumption(reportDTO.getTotalConsumption() + userAppliance.getTotalConsumption());
            reportDTO.setTotalCost(reportDTO.getTotalCost() + userAppliance.getTotalCost());
        });
        return reportDTO;
    }

    @Transactional(readOnly = true)
    public List<UserAppliancesByMonthYearReportProjection> getUserAppliancesReportByMonthYear() {
        return this.userApplianceRepository.getMonthYearReport(userService.getUserContext().getId());
    }

    @Transactional
    public void delete(Long id) {
        UserApplianceDTO userApplianceDTO = findById(id);
        userApplianceRepository.deleteById(userApplianceDTO.getId());
    }

}
