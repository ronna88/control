package br.com.ronna.control.dtos;

import br.com.ronna.control.models.AtivoModel;
import lombok.Data;
import java.util.Set;
import java.util.UUID;

@Data
public class ContratoDto {

    private String contratoDescricao;

    private double contratoValorVisita;

    private double contratoValorRemoto;

    private Set<AtivoModel> listaAtivos;

    private UUID cliente;
}
