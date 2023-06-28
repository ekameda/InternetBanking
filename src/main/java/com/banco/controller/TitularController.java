package com.banco.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.banco.domain.Titular;
import com.banco.service.TitularService;


@RestController
@RequestMapping("/v1/titular")
public class TitularController {

	@Autowired
	private TitularService titularService;

	@GetMapping
	public List<Titular> ListaTitulares(){
		return titularService.listaTitulares();
	}
}
