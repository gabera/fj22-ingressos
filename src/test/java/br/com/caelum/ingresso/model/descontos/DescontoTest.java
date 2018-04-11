package br.com.caelum.ingresso.model.descontos;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalTime;

import org.junit.Assert;
import org.junit.Test;

import br.com.caelum.ingresso.model.Filme;
import br.com.caelum.ingresso.model.Ingresso;
import br.com.caelum.ingresso.model.Sala;
import br.com.caelum.ingresso.model.Sessao;


public class DescontoTest {
	
	@Test
	public void naoDeveConcederDescontoParaIngressoNormal(){
		Filme filme = new Filme("Rouge One", Duration.ofMinutes(120),"SCI-FI",BigDecimal.ONE);
		Sala sala = new Sala("Sala 3D",BigDecimal.TEN);
		
		Sessao sessao = new Sessao (LocalTime.parse("10:00:00"), filme, sala);
		Ingresso ingresso = new Ingresso(sessao, new SemDesconto());
		
		BigDecimal precoEsperado = sala.getPreco().add(filme.getPreco());
		System.out.println("precoEsperado="+precoEsperado);
		System.out.println("precoIngresso="+ingresso.getPreco());
		Assert.assertEquals(precoEsperado, ingresso.getPreco());
	}
}
