package br.com.ronna.control.dtos;

import br.com.ronna.control.models.ClienteModel;
import br.com.ronna.control.models.LocalModel;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class FechamentoResponseDto {

    private UUID visitaId;
    private LocalDateTime visitaInicio;
    private LocalDateTime visitaFinal;
    private Double visitaValorProdutos;
    private Double visitaTotalAbono;
    private boolean visitaRemoto;
    private ClienteModel clienteModel;
    private LocalModel localModel;
    private Double contratoValorVisita;
    private Double contratoValorRemoto;

}
