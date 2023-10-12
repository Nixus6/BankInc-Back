package com.bankinc.credibanco.controller;

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
	private final TarjetService tarjetService;
	
    /*@GetMapping("/tarjets/{id}")
    public ResponseEntity<TarjetResponse> login(@PathVariable Integer id)
    { 
        return ResponseEntity.ok(tarjetService.getCardsById(id));
    }*/
    
    @PostMapping(value = "targets")
    public ResponseEntity<TarjetResponse> login(@RequestBody TarjetRequest request)
    { 
        return ResponseEntity.ok(tarjetService.createTarjet(request));
    }
}
