package com.banco.controller;

import java.util.Date;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.banco.domain.Conta;
import com.banco.domain.Movimentacao;
import com.banco.dto.ContaResponseDto;
import com.banco.dto.MovimentacaoResponseDto;
import com.banco.repository.ContaRepository;
import com.banco.service.ContaService;
import com.banco.service.MovimentacaoService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "v1/movimentacao")
public class MoimentacaoController {
	
	private final MovimentacaoService movimentacaoService;
	
	@GetMapping
	public List<Movimentacao> procuraEntreDatas(@RequestParam Date dataInicio, @RequestParam Date dataFim) {
		return  movimentacaoService.procuraEntreDatas(dataInicio,dataFim);
	}
//		List<Movimentacao> movimentacao= movimentacaoService.procuraEntreDatas(dataInicio,dataFim);
//		return ResponseEntity.ok(movimentacao.toMovimentacaoDto());
//	}

}
