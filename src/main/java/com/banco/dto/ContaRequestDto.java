package com.banco.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContaRequestDto {

	private int agencia;
	private String nome;
	private String cpf;
	private int codigo;
	private String chave;
	private Date dataNascimento;
    private Boolean planoExclusivo;

}