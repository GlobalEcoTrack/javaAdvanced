package com.global_solution.api_gs_ecoTrack.controllers;

import com.global_solution.api_gs_ecoTrack.domain.dto.ReportDTO;
import com.global_solution.api_gs_ecoTrack.domain.dto.UserApplianceDTO;
import com.global_solution.api_gs_ecoTrack.domain.projections.UserAppliancesByMonthYearReportProjection;
import com.global_solution.api_gs_ecoTrack.services.UserApplianceService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

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
        appliance.add(linkTo(methodOn(UserApplianceController.class).findById(appliance.getId())).withSelfRel())
                .add(linkTo(methodOn(UserApplianceController.class).delete(appliance.getId())).withRel("delete"))
                .add(linkTo(methodOn(UserApplianceController.class).getUserAppliancesReport()).withRel("report"))
                .add(linkTo(methodOn(UserApplianceController.class).getMonthYearReport()).withRel("reportMonthYear"))
                .add(linkTo(methodOn(UserApplianceController.class).findAll()).withRel("findAll"));
        return ResponseEntity.created(uri).body(appliance);
    }

    @GetMapping("/user/appliance/{applianceId}")
    public ResponseEntity<List<UserApplianceDTO>> findAllByUserIdAndApplianceId(@PathVariable Long applianceId) {
        return ResponseEntity.ok(userApplianceService.findAllByUserIdAndApplianceId(applianceId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserApplianceDTO> findById(@PathVariable Long id) {
        UserApplianceDTO userApplianceDTO = userApplianceService.findById(id);
        return ResponseEntity.ok(userApplianceDTO
                .add(linkTo(methodOn(UserApplianceController.class).findAll()).withRel("findAll"))
                .add(linkTo(methodOn(UserApplianceController.class).delete(id)).withRel("delete"))
                .add(linkTo(methodOn(UserApplianceController.class).getUserAppliancesReport()).withRel("report"))
                .add(linkTo(methodOn(UserApplianceController.class).getMonthYearReport()).withRel("reportMonthYear"))
                .add(linkTo(methodOn(UserApplianceController.class).insert(userApplianceDTO)).withRel("insert")));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userApplianceService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/report")
    public ResponseEntity<ReportDTO> getUserAppliancesReport() {
        return ResponseEntity.ok(userApplianceService.getUserAppliancesReport());
    }

    @GetMapping("/report/monthYear")
    public ResponseEntity<List<UserAppliancesByMonthYearReportProjection>> getMonthYearReport() {
        return ResponseEntity.ok(userApplianceService.getUserAppliancesReportByMonthYear());
    }

}
