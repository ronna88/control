package br.com.ronna.control.dtos;

import br.com.ronna.control.models.FuncionarioModel;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
public class VisitaDto {

    private LocalDateTime visitaInicio;

    private LocalDateTime visitaFinal;

    private String visitaDescricao;

    private Double visitaValorProdutos;

    private List<FuncionarioModel> funcionarios;

    private UUID cliente;

}
