package com.global_solution.api_gs_ecoTrack.controllers;

import com.global_solution.api_gs_ecoTrack.domain.dto.UserApplianceDTO;
import com.global_solution.api_gs_ecoTrack.services.UserApplianceService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/userAppliance")
public class UserApplianceController {

    @Autowired
    private UserApplianceService userApplianceService;

    @GetMapping
    public ResponseEntity<List<UserApplianceDTO>> findAll() {
        return ResponseEntity.ok(userApplianceService.findAll());
    }

    @PostMapping
    public ResponseEntity<UserApplianceDTO> insert(@RequestBody @Valid UserApplianceDTO applianceDTO) {
        UserApplianceDTO appliance = this.userApplianceService.insert(applianceDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(appliance.getId()).toUri();
        return ResponseEntity.created(uri).body(appliance);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserApplianceDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(userApplianceService.findById(id));
    }

}
