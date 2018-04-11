package br.com.caelum.ingresso.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import br.com.caelum.ingresso.model.Sala;
import br.com.caelum.ingresso.model.Sessao;

@Repository //anotacao do spring-context (gerencia hibernate e spring mvc) - beans do spring
public class SessaoDao {
	
	@PersistenceContext // anotacao do hibernate
	private EntityManager manager;
	
	public void save(Sessao s){
		manager.persist(s);
	}
	
	public List<Sessao> buscaSessoesDaSala(Sala sala){
		return manager.createQuery("select s from Sessao s where s.sala = :sala",
									Sessao.class)
						.setParameter("sala", sala).getResultList();
	}
	
	public void delete(Integer id) {
        manager.remove(findOne(id));
    }
	
	public Sessao findOne(Integer id) {
        return manager.find(Sessao.class, id);
    }

}
