package br.com.caelum.ingresso.model;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalTime;

import org.junit.Assert;
import org.junit.Test;




public class SessaoTest {

	@Test
	public void oPrecoDaSessaoDeveSerIgualASoma(){
		Sala sala = new Sala("Eldorado - IMAX", new BigDecimal("22.5"));
		Filme filme = new Filme("Rouge One", Duration.ofMinutes(120),"SCI-FI", new BigDecimal("12.0"));
		
		Sessao sessao = new Sessao(LocalTime.parse("10:00:00"),filme,sala);
		
		Assert.assertEquals(sessao.getPreco(), filme.getPreco().add(sala.getPreco()));
	}
}
