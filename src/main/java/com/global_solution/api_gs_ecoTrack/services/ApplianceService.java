package com.global_solution.api_gs_ecoTrack.services;

import com.global_solution.api_gs_ecoTrack.domain.Appliance;
import com.global_solution.api_gs_ecoTrack.domain.dto.ApplianceDTO;
import com.global_solution.api_gs_ecoTrack.repositories.ApplianceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ApplianceService {

    @Autowired
    private ApplianceRepository applianceRepository;

    @Transactional
    public ApplianceDTO insert(ApplianceDTO applianceDTO) {
        Appliance appliance = new Appliance(applianceDTO);
        appliance = applianceRepository.save(appliance);
        return new ApplianceDTO(appliance);
    }

    @Transactional(readOnly = true)
    public List<ApplianceDTO> findAll() {
        return applianceRepository.findAll().stream().map(ApplianceDTO::new).toList();
    }

    @Transactional(readOnly = true)
    public ApplianceDTO findById(Long id) {
        Appliance appliance = applianceRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Appliance não encontrado !!"));
        return new ApplianceDTO(appliance);
    }

    @Transactional
    public void delete(Long id) {
        ApplianceDTO applianceDTO = findById(id);
        applianceRepository.deleteById(applianceDTO.getId());
    }

    @Transactional
    public ApplianceDTO update(Long id, ApplianceDTO applianceDTO) {
        Appliance appliance = new Appliance(findById(id));
        appliance.setName(applianceDTO.getName());
        appliance.setKw(applianceDTO.getKw());
        appliance = applianceRepository.save(appliance);
        return new ApplianceDTO(appliance);
    }
}
