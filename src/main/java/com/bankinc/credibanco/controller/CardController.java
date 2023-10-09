package com.bankinc.credibanco.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bankinc.credibanco.model.Card;
import com.bankinc.credibanco.response.CardResponseRest;
import com.bankinc.credibanco.service.ICardService;

@RestController
@RequestMapping("/v1")
public class CardController {
	private static final Logger log=LoggerFactory.getLogger(CardController.class);
	@Autowired
	private ICardService service;
	
	@GetMapping("/cards")
	public ResponseEntity<CardResponseRest> getCards() {
		ResponseEntity<CardResponseRest> response = service.searchCards();
		log.info("response: "+ response );
		return response;
	}
	@GetMapping("/cards/{id}")
	public ResponseEntity<CardResponseRest> getCardByUser (@PathVariable Long id){
		
		ResponseEntity<CardResponseRest> response = service.getCardByUser(id);
		return response;
		
	}
	@PostMapping("/cards")
	public ResponseEntity<CardResponseRest> postCard(@RequestBody Card request){
		ResponseEntity<CardResponseRest> response = service.postCard(request);
		return response;
	}
	
	@PutMapping("/cards/{credit}/{id}")
	public ResponseEntity<CardResponseRest> updateCredit(@PathVariable Long credit,@PathVariable Long id){
		ResponseEntity<CardResponseRest> response = service.updateCredit(credit,id);
		return response;
	}
}
