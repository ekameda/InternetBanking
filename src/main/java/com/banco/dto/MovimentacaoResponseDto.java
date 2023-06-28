package com.banco.dto;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MovimentacaoResponseDto {
	
	private Long id;
	private String tipoMovimentacao;
	private Long idConta;
	private BigDecimal valorContaAnterior;
	private BigDecimal valorContaApos;
	private BigDecimal valorMovimentacao;
	private Date dataMovimentacao;

}
