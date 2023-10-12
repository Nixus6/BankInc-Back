package com.bankinc.credibanco.response;

import java.util.List;

import com.bankinc.credibanco.model.Tarjet;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TarjetResponse {
	//private Optional<Tarjet> tarjet;
	List<Tarjet> tarjet;
}
