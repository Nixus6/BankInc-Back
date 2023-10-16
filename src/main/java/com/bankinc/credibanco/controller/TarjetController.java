package com.bankinc.credibanco.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bankinc.credibanco.request.TarjetRequest;
import com.bankinc.credibanco.response.TarjetResponse;
import com.bankinc.credibanco.service.TarjetService;

import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class TarjetController {
	private static final Logger log=LoggerFactory.getLogger(CardController.class);
	private final TarjetService tarjetService;
	
    @GetMapping("/tarjets/{id}")
    public ResponseEntity<TarjetResponse> getTarjetByIdTitular(@PathVariable Integer id)
    { 
    	log.info("entro tarjets id: ");
        return ResponseEntity.ok(tarjetService.getCardsById(id));
    }
    
    @PostMapping(value = "tarjets")
    public ResponseEntity<TarjetResponse> createTarjet(@RequestBody TarjetRequest request)
    { 
        return ResponseEntity.ok(tarjetService.createTarjet(request));
    }
}
