package com.global_solution.api_gs_ecoTrack.controllers;

import com.global_solution.api_gs_ecoTrack.domain.dto.StateDTO;
import com.global_solution.api_gs_ecoTrack.services.StateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/state")
public class StateController {

    @Autowired
    private StateService stateService;

    @GetMapping
    public ResponseEntity<List<StateDTO>> findAll() {
        List<StateDTO> states = stateService.findAll();
        states.forEach(state -> {
            state.add(linkTo(methodOn(StateController.class).findById(state.getId())).withRel("Find by id"));
            state.add(linkTo(methodOn(StateController.class).deleteById(state.getId())).withRel("Delete by id"));
            state.add(linkTo(methodOn(StateController.class).update(state.getId(), state)).withRel("Update by id"));
            state.add(linkTo(methodOn(StateController.class).insert(state)).withRel("Insert"));
        });
        return ResponseEntity.ok(states);
    }

    @GetMapping("/pageable")
    public ResponseEntity<Page<StateDTO>> findAllPageable(@RequestParam(value = "page", defaultValue = "0") int page,
                                                          @RequestParam(value = "size", defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<StateDTO> statesPage = stateService.findAllPageable(pageable);

        statesPage.forEach(state -> {
            state.add(linkTo(methodOn(StateController.class).findById(state.getId())).withRel("Find by id"));
            state.add(linkTo(methodOn(StateController.class).deleteById(state.getId())).withRel("Delete by id"));
            state.add(linkTo(methodOn(StateController.class).update(state.getId(), state)).withRel("Update by id"));
            state.add(linkTo(methodOn(StateController.class).insert(state)).withRel("Insert"));
        });

        return ResponseEntity.ok(statesPage);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        stateService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<StateDTO> findById(@PathVariable Long id) {
        StateDTO stateDTO = stateService.findById(id);
        stateDTO.add(linkTo(methodOn(StateController.class).deleteById(stateDTO.getId())).withRel("Delete by id"));
        stateDTO.add(linkTo(methodOn(StateController.class).update(stateDTO.getId(), stateDTO)).withRel("Update by id"));
        stateDTO.add(linkTo(methodOn(StateController.class).findAll()).withRel("List of states"));
        stateDTO.add(linkTo(methodOn(StateController.class).insert(stateDTO)).withRel("Insert"));
        return ResponseEntity.ok(stateDTO);
    }

    @PostMapping
    public ResponseEntity<StateDTO> insert(@RequestBody StateDTO stateDTO) {
        StateDTO state = this.stateService.insert(stateDTO);
        state.add(linkTo(methodOn(StateController.class).findById(state.getId())).withRel("Find by id"));
        state.add(linkTo(methodOn(StateController.class).findAll()).withRel("List of states"));
        state.add(linkTo(methodOn(StateController.class).deleteById(state.getId())).withRel("Delete by id"));
        state.add(linkTo(methodOn(StateController.class).update(state.getId(), state)).withRel("Update by id"));
        return ResponseEntity.created(linkTo(methodOn(StateController.class).findById(state.getId())).toUri()).body(state);
    }

    @PutMapping("/{id}")
    public ResponseEntity<StateDTO> update(@PathVariable Long id, @RequestBody StateDTO stateDTO) {
        StateDTO state = this.stateService.update(id, stateDTO);
        state.add(linkTo(methodOn(StateController.class).findById(state.getId())).withRel("Find by id"));
        state.add(linkTo(methodOn(StateController.class).findAll()).withRel("List of states"));
        state.add(linkTo(methodOn(StateController.class).deleteById(state.getId())).withRel("Delete by id"));
        state.add(linkTo(methodOn(StateController.class).insert(state)).withRel("Insert"));
        return ResponseEntity.ok(state);
    }

}
