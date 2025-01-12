package com.global_solution.api_gs_ecoTrack.domain.dto;

import com.global_solution.api_gs_ecoTrack.domain.Appliance;
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
public class ApplianceDTO extends RepresentationModel<ApplianceDTO> {
    private Long id;
    @NotBlank(message = "Nome do eletrodoméstico é obrigatório")
    private String name;
    @NotNull(message = "Consumo de energia por hora é obrigatório")
    private Double kw;

    public ApplianceDTO(Appliance appliance) {
        this.id = appliance.getId();
        this.name = appliance.getName();
        this.kw = appliance.getKw();
    }
}
