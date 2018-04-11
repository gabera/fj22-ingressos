package br.com.caelum.ingresso.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Min;

@Entity // anotacao JPA - Java Persistence API
public class Sessao {

	@Id
	@GeneratedValue
	private Integer id;
	private LocalTime horario;
	
	@ManyToOne
	private Sala sala;
	@ManyToOne
	private Filme filme;
	
	@Min(0)
	private BigDecimal preco;
	
	public Sessao(LocalTime h, Filme f, Sala s){
		horario = h;
		filme = f;
		sala = s;
		preco = sala.getPreco().add(filme.getPreco()).setScale(2, RoundingMode.HALF_UP);
		
	}
	public BigDecimal getPreco() {
		return preco;
	}
	
	/**
	 * @deprecated //somente para o @Entity do Hibernate
	 */
	public Sessao(){
		
	}
	
	public LocalTime getHorarioTermino(){
		return horario.plusMinutes(filme.getDuracao().toMinutes());
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public LocalTime getHorario() {
		return horario;
	}
	public void setHorario(LocalTime horario) {
		this.horario = horario;
	}
	public Sala getSala() {
		return sala;
	}
	public void setSala(Sala sala) {
		this.sala = sala;
	}
	public Filme getFilme() {
		return filme;
	}
	public void setFilme(Filme filme) {
		this.filme = filme;
	}
	
	
}
