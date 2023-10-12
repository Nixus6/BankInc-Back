package com.bankinc.credibanco.model.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bankinc.credibanco.model.Tarjet;

public interface ITarjetDao extends JpaRepository<Tarjet,Integer> {
	//List<Tarjet> findByTitular(Integer titular_id); 
}
