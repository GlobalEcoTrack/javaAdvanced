package com.global_solution.api_gs_ecoTrack.services;

import com.global_solution.api_gs_ecoTrack.domain.State;
import com.global_solution.api_gs_ecoTrack.domain.dto.StateDTO;
import com.global_solution.api_gs_ecoTrack.repositories.StateRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Transactional(readOnly = true)
    public StateDTO findByAbbreviation(String abbreviation) {
        State state = stateRepository.findByAbbreviation(abbreviation).orElseThrow(() -> new NoSuchElementException("Estado não encontrado"));
        return new StateDTO(state);
    }

    @Transactional(readOnly = true)
    public List<StateDTO> findAll() {
        return stateRepository.findAll().stream().map(StateDTO::new).toList();
    }
}
