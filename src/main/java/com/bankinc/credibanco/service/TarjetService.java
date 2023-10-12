package com.bankinc.credibanco.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.stereotype.Service;

import com.bankinc.credibanco.jwt.JwtAuthenticationFilter;
import com.bankinc.credibanco.model.Tarjet;
import com.bankinc.credibanco.model.TypeTarjet;
import com.bankinc.credibanco.model.dao.ITarjetDao;
import com.bankinc.credibanco.model.dao.IUserDao;
import com.bankinc.credibanco.request.TarjetRequest;
import com.bankinc.credibanco.response.TarjetResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TarjetService {
	private static final Logger log=LoggerFactory.getLogger(JwtAuthenticationFilter.class);
	private final ITarjetDao tarjetDao;
	private final IUserDao userDao;
   /* public TarjetResponse getCardsById(Integer idUser){
    	/*if(!userDao.existsById(idUser)) {
    		throw new ConfigDataResourceNotFoundException("Not found Tutorial with id = " + idUser);
    	}
    	List<Tarjet> list = new ArrayList<>();
    	//Optional<Tarjet> tarjet ;
    	//TarjetResponse res;
    	/*try {
    	List<Tarjet> tarjet = tarjetDao.findByTitular(idUser);
    	/*if(tarjet.isPresent()) {
    		list.add(tarjet.get());
    	}
    	return TarjetResponse.builder().tarjet(tarjet).build();
    	//return new ResponseEntity<>(res,HttpStatus.OK);
    	/*}catch (Exception e) {
			// TODO: handle exception
		}
    }*/
    public TarjetResponse createTarjet(TarjetRequest request) {
    	List<Tarjet> list = new ArrayList<>();
    	log.info("request.getTypeTarjet() "+request.getTypeTarjet());
    	Random numAleatorio = new Random();
    	Calendar date = Calendar.getInstance();
    	date.add(Calendar.YEAR, 3);
    	Long dato = numAleatorio.nextLong(99999-10000+1)+10000;    	
        Tarjet tarjet = Tarjet.builder()
        		.idProducto(dato)
        		.saldo(0)
        		.cupo(request.getTypeTarjet().equals("credit")?1000:0)
        		.fechaVencimiento(date)
        		.typeTarget(request.getTypeTarjet().equals("debit")?TypeTarjet.DEBIT:TypeTarjet.CREDIT)
        		.titular(request.getUser())
                .build();
    	tarjetDao.save(tarjet);
    	return TarjetResponse.builder().tarjet(list).build();
    }
    	
}
