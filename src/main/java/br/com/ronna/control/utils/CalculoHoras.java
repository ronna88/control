package br.com.ronna.control.utils;

import lombok.extern.log4j.Log4j2;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Log4j2
public class CalculoHoras {
    
    public Double diferencaHoras(LocalDateTime dataFinal, LocalDateTime dataInicial) {
        
        log.info("UNTIL: " + Double.valueOf(dataInicial.until(dataFinal, ChronoUnit.MINUTES)) / 60);
        return Double.valueOf(dataInicial.until(dataFinal, ChronoUnit.MINUTES)) / 60;
    }
}