package br.com.caelum.ingresso.validacao;

import java.time.Duration;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.com.caelum.ingresso.model.Filme;
import br.com.caelum.ingresso.model.Sala;
import br.com.caelum.ingresso.model.Sessao;

public class GerenciadorDeSessaoTest {

	private Filme rougeOne;
	private Sala sala3d;
	private Sessao sessaoDasDez;
	private Sessao sessaoDasTreze;
	private Sessao sessaoDasDezoito;
	
	@Before
	public void preparaTestes(){
		rougeOne = new Filme("Rouge One", Duration.ofMinutes(120),"SCI-FI");
		sala3d = new Sala("Sala 3D");
		
		sessaoDasDez = new Sessao (LocalTime.parse("10:00:00"), rougeOne, sala3d);
		sessaoDasTreze = new Sessao (LocalTime.parse("13:00:00"), rougeOne, sala3d);
		sessaoDasDezoito = new Sessao (LocalTime.parse("18:00:00"), rougeOne, sala3d);
	}
	
	@Test
	public void garanteQueNaoDevePermitirSessaoNoMesmoHorario(){
		List<Sessao> sessoes = Arrays.asList(sessaoDasDez);
		GerenciadorDeSessao gs = new GerenciadorDeSessao(sessoes);
		Assert.assertFalse(gs.cabe(sessaoDasDez));
	}
	
	@Test
	public void garanteQueNaoDevePermitirSessoesTerminandoDentroDoHorarioDeUmaSessaoJaExistente(){
		List<Sessao> sessoes = Arrays.asList(sessaoDasDez);
		Sessao sessao = new Sessao(sessaoDasDez.getHorario().minusHours(1), rougeOne, sala3d);
		GerenciadorDeSessao gs = new GerenciadorDeSessao(sessoes);
		Assert.assertFalse(gs.cabe(sessao));

	}
	@Test
	public void garanteQueNaoDevePermitirSessoesIniciandoDentroDoHorarioDeUmaSessaoJaExistente(){
		List<Sessao> sessoes = Arrays.asList(sessaoDasDez);
		Sessao sessao = new Sessao(sessaoDasDez.getHorario().plusHours(1), rougeOne, sala3d);
		GerenciadorDeSessao gs = new GerenciadorDeSessao(sessoes);
		Assert.assertFalse(gs.cabe(sessao));
	}
	
	@Test
	public void garanteQueDevePermitirUmaSessaoEntreDoisFilmes(){
		List<Sessao> sessoes = Arrays.asList(sessaoDasDez, sessaoDasDezoito);
		GerenciadorDeSessao gs = new GerenciadorDeSessao(sessoes);
		Assert.assertTrue(gs.cabe(sessaoDasTreze));

	}
}
