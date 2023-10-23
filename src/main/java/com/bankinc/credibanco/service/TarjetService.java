package com.bankinc.credibanco.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.bankinc.credibanco.jwt.JwtAuthenticationFilter;
import com.bankinc.credibanco.model.Tarjet;
import com.bankinc.credibanco.model.TypeTarjet;
import com.bankinc.credibanco.model.User;
import com.bankinc.credibanco.model.dao.ITarjetDao;
import com.bankinc.credibanco.model.dao.IUserDao;
import com.bankinc.credibanco.request.TarjetRequest;
import com.bankinc.credibanco.request.TarjetValidateCardRequest;
import com.bankinc.credibanco.response.TarjetResponse;
import com.bankinc.credibanco.response.TarjetValidateResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TarjetService {
	private static final Logger log = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
	private final ITarjetDao tarjetDao;
	private final IUserDao userDao;

	/// <summary>
	/// Obtener todas las trajetas ligadas al usuario logeado
	/// </summary>
	/// <param name="idUser"></param>
	/// <returns></returns>
	public TarjetResponse getCardsById(Integer idUser) {
		log.info("entro getCardsById: ");
		User getUser = userDao.findById(idUser).get();
		List<Tarjet> tarjet = tarjetDao.findByTitular(getUser);
		return TarjetResponse.builder().tarjet(tarjet).build();
	}

	/// <summary>
	/// Crear una nueva tarjeta
	/// </summary>
	/// <param name="request"></param>
	/// <returns></returns>
	public TarjetResponse createTarjet(TarjetRequest request) {
		// TODO:evaluar campos que vienen vacios
		log.info("request.getTypeTarjet() " + request.getTypeTarjet());
		List<Tarjet> list = new ArrayList<>();
		Random numAleatorio = new Random();
		Calendar date = Calendar.getInstance();
		date.add(Calendar.YEAR, 3);
		Long aleat1 = numAleatorio.nextLong(99999 - 10000 + 1) + 10000;
		Long aleat2 = numAleatorio.nextLong(99999 - 10000 + 1) + 10000;
		String idproducto = "202310" + aleat1 + aleat2;
		Tarjet tarjet = Tarjet.builder().idProducto(Long.parseLong(idproducto))
				.saldo(request.getTypeTarjet().equals("credit") ? 1000 : 0)
				.cupo(request.getTypeTarjet().equals("credit") ? 1000 : 0).fechaVencimiento(date)
				.typeTarget(request.getTypeTarjet().equals("debit") ? TypeTarjet.DEBIT : TypeTarjet.CREDIT)
				.titular(request.getUser()).build();
		// Tarjet tarjetSave =
		tarjetDao.save(tarjet);
		/*
		 * if( tarjetSave != null ) { list.add(tarjetSave); }
		 */
		return TarjetResponse.builder().tarjet(list).build();
	}

	/// <summary>
	/// Actualizar saldo de la tarjeta
	/// </summary>
	/// <param name="tarjet"></param>
	/// <param name="id"></param>
	/// <returns></returns>
	public TarjetResponse updateBalance(TarjetRequest tarjet, Integer id) {
		List<Tarjet> list = new ArrayList<>();
		Integer newBalance;
		try {
			Optional<Tarjet> tarjetUpdate = tarjetDao.findById(id);
			log.info("tarjetUpdate " + tarjetUpdate);
			if (tarjetUpdate.isPresent()) {
				newBalance=tarjetUpdate.get().getSaldo()+tarjet.getSaldo();
				tarjetUpdate.get().setSaldo(newBalance);
				Tarjet tarjetSave = tarjetDao.save(tarjetUpdate.get());
				if (tarjetSave != null) {
					list.add(tarjetSave);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return TarjetResponse.builder().tarjet(list).build();
	}

	/// <summary>
	/// Validar datos de trajeta de pago
	/// </summary>
	/// <param name="tarjet"></param>
	/// <returns></returns>
	public TarjetValidateResponse validateCard(TarjetValidateCardRequest tarjet) {
		HashMap<String, String> resValid = new HashMap<String, String>();
		HashMap<String, HttpStatus> resRequest = new HashMap<String, HttpStatus>();
		Calendar date = new GregorianCalendar();
		DateFormat formateador = new SimpleDateFormat("MM/YYYY");
		String name;
		try {
			Tarjet tarjetObj = tarjetDao.findByIdProducto(tarjet.getIdProducto());
			if (tarjetObj != null) {
				date = tarjetObj.getFechaVencimiento();
				name = tarjetObj.getTitular().getFirstname() + " " + tarjetObj.getTitular().getLastname();
				if (!name.equals(tarjet.getName())
						|| !formateador.format(date.getTime()).equals(tarjet.getExpiryDate())) {
					resValid.put("tipo", "Respuesta nok");
					resValid.put("codigo", "01");
					resValid.put("message",
							"01Invalid card details. You have not been charged. Please enter your card details exactly as they appear on your card and try again.");
					resRequest.put("request", HttpStatus.NOT_FOUND);
					return TarjetValidateResponse.builder().resValid(resValid).resRequest(resRequest).build();
				} else {
					resValid.put("tipo", "Respuesta ok");
					resValid.put("codigo", "00");
					resValid.put("message", "Respuesta exitosa");
					resValid.put("tarjetId", Integer.toString(tarjetObj.getId()));
					resValid.put("userId", Integer.toString(tarjetObj.getTitular().getId()));
					resRequest.put("request", HttpStatus.OK);
				}
			} else {
				resValid.put("tipo", "Respuesta nok");
				resValid.put("codigo", "01");
				resValid.put("message",
						"Invalid card details. You have not been charged. Please enter your card details exactly as they appear on your card and try again.");
				resRequest.put("request", HttpStatus.NOT_FOUND);
				return TarjetValidateResponse.builder().resValid(resValid).resRequest(resRequest).build();
			}
		} catch (Exception e) {
			// TODO: handle exception
			resRequest.put("request", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return TarjetValidateResponse.builder().resValid(resValid).resRequest(resRequest).build();
	}

}
