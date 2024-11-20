package com.global_solution.api_gs_ecoTrack.domain.dto;

import com.global_solution.api_gs_ecoTrack.domain.State;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StateDTO extends RepresentationModel<StateDTO> {
    private Long id;
    @NotBlank(message = "Nome do estado é obrigatório")
    private String name;
    @NotBlank(message = "Sigla do estado é obrigatória")
    private String abbreviation;
    @NotNull(message = "Preço do kwh é obrigatório")
    private Double price_kwh;

    public StateDTO(State state) {
        this.id = state.getId();
        this.name = state.getName();
        this.abbreviation = state.getAbbreviation();
        this.price_kwh = state.getPrice_kwh();
    }
}
