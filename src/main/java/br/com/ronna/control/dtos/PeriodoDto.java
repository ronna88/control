package br.com.ronna.control.dtos;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PeriodoDto {

    private LocalDateTime periodoInicio;

    private LocalDateTime periodoFinal;
}
