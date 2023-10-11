package com.bankinc.credibanco.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bankinc.credibanco.model.Card;
import com.bankinc.credibanco.model.dao.ICardDao;
import com.bankinc.credibanco.response.CardResponseRest;

@Service
public class CardSeviceImpl implements ICardService{
	
	private static final Logger log=LoggerFactory.getLogger(CardSeviceImpl.class);
	@Autowired
	private ICardDao cardDao;
	
	@Override
	@Transactional(readOnly=true)
	public ResponseEntity<CardResponseRest> searchCards() {
		// TODO Auto-generated method stub
		log.info("inicio metodo searchCards");
		
		CardResponseRest response = new CardResponseRest();
		try {
			
			List<Card> card=(List<Card>) cardDao.findAll();
			
			response.getCardResponse().setCard(card);
			
			response.setMetadata("Respuesta ok", "00", "Respuesta exitosa");
			
		} catch (Exception e) {
			// TODO: handle exception
			response.setMetadata("Respuesta error", "-1", "Error al consultar tarjetas");
			log.error("error al consultar cards: ", e.getMessage());
			e.getStackTrace();
			return new ResponseEntity<CardResponseRest>(response,HttpStatus.INTERNAL_SERVER_ERROR);//devuelve 500
		}
		return new ResponseEntity<CardResponseRest>(response,HttpStatus.OK);//devuelve 200
	}

	@Override
	@Transactional(readOnly = true)
	public ResponseEntity<CardResponseRest> getCardByUser(Long id) {
		// TODO Auto-generated method stub
		log.info("Inicio metodo buscar por Id User");
		CardResponseRest response = new CardResponseRest();
		List<Card> list = new ArrayList<>();
		try {
			//TODO: Traer por usuario y no por el id de la tarjeta
			Optional<Card> categoria = cardDao.findById(id);
			
			if(categoria.isPresent()) {
				list.add(categoria.get());
				response.getCardResponse().setCard(list);
				response.setMetadata("Respuesta ok", "00", "Respuesta exitosa");
			}else {
				log.error("Error en consultar categoria");
				response.setMetadata("Respuesta nok", "-1", "Tarjeta no encontrada");
				return new ResponseEntity<CardResponseRest>(response,HttpStatus.NOT_FOUND);//devuelve 404
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			log.error("Error en consultar categoria");
			response.setMetadata("Respuesta nok", "-1", "Error consultar tarjeta por usuario");
			return new ResponseEntity<CardResponseRest>(response,HttpStatus.INTERNAL_SERVER_ERROR);//devuelve 404
			
		}
		return new ResponseEntity<CardResponseRest>(response,HttpStatus.OK);//devuelve 200
	}

	@Override
	@Transactional
	public ResponseEntity<CardResponseRest> postCard(Card card) {
		// TODO Auto-generated method stub
		log.info("Inicio metodo buscar crar tarjeta");
		CardResponseRest response = new CardResponseRest();
		List<Card> list = new ArrayList<>();
		try {
			
			Card cardSave = cardDao.save(card);
			if(cardSave!= null) {
				list.add(cardSave);
				response.getCardResponse().setCard(list);
			}else {
				log.error("Error en crear tarjeta");
				response.setMetadata("Respuesta nok", "-1", "Tarjeta no creada");
				return new ResponseEntity<CardResponseRest>(response,HttpStatus.BAD_REQUEST);//devuelve 404
			}
			
			
		} catch (Exception e) {
			// TODO: handle exception
			log.error("Error en crear tarjeta");
			response.setMetadata("Respuesta nok", "-1", "Error crear tarjeta");
			return new ResponseEntity<CardResponseRest>(response,HttpStatus.INTERNAL_SERVER_ERROR);//devuelve 404
		}
		response.setMetadata("Respuesta ok", "00", "Tarjeta Creada");
		return new ResponseEntity<CardResponseRest>(response,HttpStatus.OK);//devuelve 200
	}

	@Override
	@Transactional
	public ResponseEntity<CardResponseRest> updateCredit(Long credit, Long id) {
		// TODO Auto-generated method stub
		log.info("Inicio metodo actualizar saldo");
		CardResponseRest response = new CardResponseRest();
		List<Card> list = new ArrayList<>();
		
		try {
			Optional<Card> cardSearch = cardDao.findById(id);
			if(cardSearch.isPresent()) {
				cardSearch.get().setSaldo(credit);
				Card cardUpdate = cardDao.save(cardSearch.get());
				if(cardUpdate!= null) {
					response.setMetadata("Respuesta ok", "00", "Tarjeta actualizada");
					list.add(cardUpdate);
					response.getCardResponse().setCard(list);
				}else {
					log.error("Error actualizar tarjeta");
					response.setMetadata("Respuesta nok", "-1", "Categoria no actualizada");
					return new ResponseEntity<CardResponseRest>(response,HttpStatus.BAD_REQUEST);
				}
			}else {
				log.error("Error actualizar tarjeta");
				response.setMetadata("Respuesta nok", "-1", "Categoria no actualizada");
				return new ResponseEntity<CardResponseRest>(response,HttpStatus.NOT_FOUND);//devuelve 404
			}
		} catch (Exception e) {
			// TODO: handle exception
			log.error("Error actualizar tarjeta ",e.getMessage());
			e.getStackTrace();
			response.setMetadata("Respuesta nok", "-1", "Categoria no actualizada");
			return new ResponseEntity<CardResponseRest>(response,HttpStatus.INTERNAL_SERVER_ERROR);//devuelve 500
		}
		return new ResponseEntity<CardResponseRest>(response,HttpStatus.OK);//devuelve 200
	}
	
	
	
	
	
}
