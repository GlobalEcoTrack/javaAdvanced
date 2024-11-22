package com.global_solution.api_gs_ecoTrack.services;

import com.global_solution.api_gs_ecoTrack.domain.State;
import com.global_solution.api_gs_ecoTrack.domain.dto.StateDTO;
import com.global_solution.api_gs_ecoTrack.repositories.StateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class StateService {

    @Autowired
    private StateRepository stateRepository;

    @Transactional
    public StateDTO insert(StateDTO stateDTO) {
        State state = new State(stateDTO);
        state = stateRepository.save(state);
        return new StateDTO(state);
    }

    @Transactional
    public StateDTO insertWithProcedure(StateDTO stateDTO) {
        State state = new State(stateDTO);
        state = stateRepository.eco_track_insert_state(state.getName(), state.getAbbreviation(), state.getPrice_kwh());
        return new StateDTO(state);
    }

    @Transactional(readOnly = true)
    public StateDTO findByAbbreviation(String abbreviation) {
        State state = stateRepository.findByAbbreviation(abbreviation).orElseThrow(() -> new NoSuchElementException("Estado não encontrado"));
        return new StateDTO(state);
    }

    @Transactional(readOnly = true)
    public List<StateDTO> findAll() {
        return stateRepository.findAll().stream().map(StateDTO::new).toList();
    }

    @Transactional(readOnly = true)
    public Page<StateDTO> findAllPageable(Pageable pageable) {
        return stateRepository.findAll(pageable)
                .map(StateDTO::new);
    }

    @Transactional
    public void deleteById(Long id) {
        StateDTO stateDTO = findById(id);
        stateRepository.deleteById(stateDTO.getId());
    }

    @Transactional(readOnly = true)
    public StateDTO findById(Long id) {
        State state = stateRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Estado não encontrado"));
        return new StateDTO(state);
    }

    @Transactional
    public StateDTO update(Long id, StateDTO stateDTO) {
        State state = stateRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Estado não encontrado"));
        state.setAbbreviation(stateDTO.getAbbreviation());
        state.setName(stateDTO.getName());
        state.setPrice_kwh(stateDTO.getPrice_kwh());
        state = stateRepository.save(state);
        return new StateDTO(state);
    }
}
