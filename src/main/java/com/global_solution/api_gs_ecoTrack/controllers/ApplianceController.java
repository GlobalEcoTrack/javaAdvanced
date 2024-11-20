package com.global_solution.api_gs_ecoTrack.controllers;

import com.global_solution.api_gs_ecoTrack.domain.dto.ApplianceDTO;
import com.global_solution.api_gs_ecoTrack.domain.dto.RegisterReponseDTO;
import com.global_solution.api_gs_ecoTrack.domain.dto.StateDTO;
import com.global_solution.api_gs_ecoTrack.services.ApplianceService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/appliance")
public class ApplianceController {

    @Autowired
    private ApplianceService applianceService;

    @GetMapping
    public ResponseEntity<List<ApplianceDTO>> findAll() {
        return ResponseEntity.ok(applianceService.findAll());
    }

    @PostMapping
    public ResponseEntity<ApplianceDTO> insert(@RequestBody @Valid ApplianceDTO applianceDTO) {
        ApplianceDTO appliance = this.applianceService.insert(applianceDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(appliance.getId()).toUri();
        return ResponseEntity.created(uri).body(appliance);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApplianceDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(applianceService.findById(id));
    }

}
