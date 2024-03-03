package br.com.ronna.control.models;

import br.com.ronna.control.enums.FechamentoStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Entity
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "TB_FECHAMENTOS")
public class FechamentoModel {
    
    @Id
    @Column(columnDefinition = "varbinary(36)")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID fechamentoId;

    @Column(nullable = false)
    private LocalDateTime fechamentoInicio;
    
    @Column(nullable = false)
    private LocalDateTime fechamentoFinal;

    private Double fechamentoValorServicos;

    private Double fechamentoValorProdutos;

    @OneToOne
    private LocalModel local;

    @OneToMany
    private Set<VisitaModel> visitas;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FechamentoStatus fechamentoStatus;
    
    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private LocalDateTime createdDate;
    
    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private LocalDateTime updatedDate;
}