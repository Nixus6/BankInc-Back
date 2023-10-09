package com.bankinc.credibanco.service;

import org.springframework.http.ResponseEntity;

import com.bankinc.credibanco.model.Card;
import com.bankinc.credibanco.response.CardResponseRest;

public interface ICardService {
	
	public ResponseEntity<CardResponseRest> searchCards();
	public ResponseEntity<CardResponseRest> getCardByUser(Long id);
	public ResponseEntity<CardResponseRest> postCard(Card card);
	public ResponseEntity<CardResponseRest> updateCredit(Long credit, Long id);
	
}
