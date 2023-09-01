package br.com.ronna.control.models;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "TB_CLIENTES_PJ")
public class PessoaJuridicaModel extends ClienteModel {

    private String clienteRazaoSocial;

    private String clienteCNPJ;

    private String clienteInscricaoEstadual;

}
