package com.bankinc.credibanco.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bankinc.credibanco.request.TarjetRequest;
import com.bankinc.credibanco.request.TransactionRequest;
import com.bankinc.credibanco.response.TarjetResponse;
import com.bankinc.credibanco.response.TransactionResponse;
import com.bankinc.credibanco.service.TransactionService;

import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = {"http://localhost:4200"})
@RequestMapping("/v1")
@RestController
@RequiredArgsConstructor
public class TransactionController {
	private static final Logger log=LoggerFactory.getLogger(TransactionController.class);
	private final TransactionService transactionService;
	
    @GetMapping("/transactions/{id}")
    public ResponseEntity<TransactionResponse> getTransactionsByIdUser(@PathVariable Integer id)
    { 
        return ResponseEntity.ok(transactionService.getTransactionsByIdUser(id));
    }
	
    @PostMapping(value = "transactions")
    public ResponseEntity<TransactionResponse> createTransaction(@RequestBody TransactionRequest request)
    { 
        return ResponseEntity.ok(transactionService.createTransaction(request));
    }
    
	@GetMapping("/transactions/state/{id}")
	public ResponseEntity<TransactionResponse> changeStateTransaction (@PathVariable Integer id) {
		return ResponseEntity.ok(transactionService.changeStateTransaction(id));
	}
}
