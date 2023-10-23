
package com.bankinc.credibanco.model;

import java.io.Serializable;
import java.util.Calendar;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity

@AllArgsConstructor

@NoArgsConstructor

@Builder

@Getter

@Setter

@Table(name = "transaction ")
public class Transaction implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer id;

	@Column(nullable = false)
	Calendar fechaTransaccion;
	
	@Column(nullable = false)
	Integer totalPrice;

	@Enumerated(EnumType.STRING)
	TransactionState state;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="usuario_id", nullable = false)
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	User usuario;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="tarjet_id", nullable = false)
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	Tarjet tarjet;
}
