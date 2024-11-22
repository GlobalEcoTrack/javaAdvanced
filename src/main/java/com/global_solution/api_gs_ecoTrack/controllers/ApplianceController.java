package com.global_solution.api_gs_ecoTrack.controllers;

import com.global_solution.api_gs_ecoTrack.domain.dto.ApplianceDTO;
import com.global_solution.api_gs_ecoTrack.services.ApplianceService;
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
@RequestMapping("/appliance")
public class ApplianceController {

    @Autowired
    private ApplianceService applianceService;

    @GetMapping
    public ResponseEntity<List<ApplianceDTO>> findAll() {
        List<ApplianceDTO> appliances = applianceService.findAll();
        appliances.forEach(appliance -> appliance.add(linkTo(methodOn(ApplianceController.class).findById(appliance.getId())).withRel("Find by id"))
                .add(linkTo(methodOn(ApplianceController.class).delete(appliance.getId())).withRel("Delete appliance"))
                .add(linkTo(methodOn(ApplianceController.class).update(appliance.getId(), new ApplianceDTO())).withRel("Update appliance"))
                .add(linkTo(methodOn(ApplianceController.class).insert(new ApplianceDTO())).withRel("Insert appliance")));
        return ResponseEntity.ok(appliances);
    }

    @PostMapping
    public ResponseEntity<ApplianceDTO> insert(@RequestBody @Valid ApplianceDTO applianceDTO) {
        ApplianceDTO appliance = this.applianceService.insert(applianceDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(appliance.getId()).toUri();
        appliance.add(linkTo(methodOn(ApplianceController.class).findById(appliance.getId())).withRel("Find by id"))
                .add(linkTo(methodOn(ApplianceController.class).findAll()).withRel("List of appliances"))
                .add(linkTo(methodOn(ApplianceController.class).delete(appliance.getId())).withRel("Delete appliance"))
                .add(linkTo(methodOn(ApplianceController.class).update(appliance.getId(), new ApplianceDTO())).withRel("Update appliance"));

        return ResponseEntity.created(uri).body(appliance);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApplianceDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(applianceService.findById(id)
                .add(linkTo(methodOn(ApplianceController.class).findAll()).withRel("List of appliances"))
                .add(linkTo(methodOn(ApplianceController.class).delete(id)).withRel("Delete appliance"))
                .add(linkTo(methodOn(ApplianceController.class).update(id, new ApplianceDTO())).withRel("Update appliance"))
                .add(linkTo(methodOn(ApplianceController.class).insert(new ApplianceDTO())).withRel("Insert appliance")));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        applianceService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApplianceDTO> update(@PathVariable Long id, @RequestBody @Valid ApplianceDTO applianceDTO) {
        return ResponseEntity.ok(applianceService.update(id, applianceDTO)
                .add(linkTo(methodOn(ApplianceController.class).findById(id)).withRel("Find by id"))
                .add(linkTo(methodOn(ApplianceController.class).findAll()).withRel("List of appliances"))
                .add(linkTo(methodOn(ApplianceController.class).delete(id)).withRel("Delete appliance"))
                .add(linkTo(methodOn(ApplianceController.class).insert(applianceDTO)).withRel("Insert appliance")));
    }

}
