package com.banco.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.banco.domain.Conta;
import com.banco.domain.Movimentacao;
import com.banco.dto.ContaRequestDto;
import com.banco.dto.ContaResponseDto;
import com.banco.repository.ContaRepository;
import com.banco.service.ContaService;
import com.banco.service.MovimentacaoService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "v1/conta")
public class ContaController {

	private final ContaRepository contaRepository;
	private final ContaService contaService;
	private final MovimentacaoService movimentacaoService;

	// Serviço de Criar Conta
	// Ex. Postman Post localhost:8080/v1/contas
	// Body raw Json
	// {
	// "agencia": "1234",
	// "nome": "Edu",
	// "cpf": "76345",
	// "codigo": "110",
	// "chave": "{{$randomUUID}}"
	//

	@PostMapping
	public ContaResponseDto criarConta(@RequestBody ContaRequestDto requestDto) {
		Conta conta = contaService.criarConta(requestDto);
		return conta.toContaDto();
	}

	// Serviço de Recuperar Contas
	// Ex. Postman http://localhost:8080/v1/conta

	@GetMapping()
	public List<Conta> procuraContas() {
		return contaService.procuraContas();
	}

	// Serviço de Recuperar Conta po Id
	// Ex. Postman Get http://localhost:8080/v1/conta/1

	@GetMapping("/{id}")
	public ResponseEntity<ContaResponseDto> procuraContaPorIdSemTry(@PathVariable Long id) {
		Conta conta = contaService.procuraConta(id);
		return ResponseEntity.ok(conta.toContaDto());
	}

	// Serviço Credito por Id
	// Ex. Postman Post http://localhost:8080/v1/conta/1/credito/2000

	@PostMapping("/{idConta}/credito/{valor}")
	public ResponseEntity<ContaResponseDto> creditarConta(@PathVariable Long idConta, @PathVariable BigDecimal valor) {
		BigDecimal valorAnterior = movimentacaoService.valorAnterio(idConta);
		Conta conta = contaService.creditarConta(idConta, valor);
		Movimentacao movimentacao = movimentacaoService.creditaConta(idConta, valor,valorAnterior);
		log.info(movimentacao.toString());
		return ResponseEntity.ok(conta.toContaDto());
	}

	// Serviço de Debito por Id
	// Ex. Postman Post http://localhost:8080/v1/conta/1/debito/100.5

	@PostMapping("/{idConta}/debito/{valor}")
	public ResponseEntity<ContaResponseDto> debitaConta(@PathVariable Long idConta, @PathVariable BigDecimal valor) {
		BigDecimal valorAnterior = movimentacaoService.valorAnterio(idConta);
		Conta conta = contaService.debitaConta(idConta, valor);
		Movimentacao movimentacao = movimentacaoService.debita(idConta, valor,valorAnterior);
		log.info(movimentacao.toString());
		return ResponseEntity.ok(conta.toContaDto());
	}

}
