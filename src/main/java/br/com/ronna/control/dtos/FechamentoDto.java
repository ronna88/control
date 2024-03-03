package br.com.ronna.control.dtos;

import br.com.ronna.control.enums.FechamentoStatus;
import br.com.ronna.control.models.VisitaModel;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Data
public class FechamentoDto {
    
    private UUID clienteLocalId;
    
    private LocalDateTime fechamentoInicio;
    
    private LocalDateTime fechamentoFinal;
    
    private Set<VisitaModel> visitas;
    
    private Double fechamentoValorProdutos;
    
    private Double fechamentoValorServicos;
    
}