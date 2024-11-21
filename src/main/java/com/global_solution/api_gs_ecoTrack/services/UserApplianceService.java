package com.global_solution.api_gs_ecoTrack.services;

import com.global_solution.api_gs_ecoTrack.domain.Appliance;
import com.global_solution.api_gs_ecoTrack.domain.UserAppliance;
import com.global_solution.api_gs_ecoTrack.domain.dto.UserApplianceDTO;
import com.global_solution.api_gs_ecoTrack.repositories.UserApplianceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

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


        userAppliance = userApplianceRepository.save(userAppliance);
        return new UserApplianceDTO(userAppliance);
    }

    @Transactional(readOnly = true)
    public List<UserApplianceDTO> findAll() {
        return userApplianceRepository.findAll().stream().map(UserApplianceDTO::new).toList();
    }

    @Transactional(readOnly = true)
    public UserApplianceDTO findById(Long id) {
        UserAppliance userAppliance = userApplianceRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("UserAppliance not found"));
        return new UserApplianceDTO(userAppliance);
    }

}
