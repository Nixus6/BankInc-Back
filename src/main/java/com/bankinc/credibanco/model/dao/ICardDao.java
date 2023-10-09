package com.bankinc.credibanco.model.dao;

import org.springframework.data.repository.CrudRepository;

import com.bankinc.credibanco.model.Card;

public interface ICardDao extends CrudRepository<Card, Long> {

}
