package br.com.caelum.ingresso.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import br.com.caelum.ingresso.dao.FilmeDao;
import br.com.caelum.ingresso.dao.SalaDao;
import br.com.caelum.ingresso.dao.SessaoDao;
import br.com.caelum.ingresso.model.Sessao;
import br.com.caelum.ingresso.model.form.SessaoForm;
import br.com.caelum.ingresso.validacao.GerenciadorDeSessao;

@Controller //anotacao do spring MVC
public class SessaoController {
	@Autowired // @autowired (inversao de controle, IoC), faz o spring dar new na classe 
	private SalaDao salaDao;
	@Autowired
	private FilmeDao filmeDao;
	
	@GetMapping("admin/sessao")
	public ModelAndView form(@RequestParam("salaId") Integer salaId, SessaoForm sessaoForm){
		
		sessaoForm.setSalaId(salaId);
		
		ModelAndView mv = new ModelAndView("sessao/sessao");
		mv.addObject("sala", salaDao.findOne(salaId));
		mv.addObject("filmes",filmeDao.findAll());
		mv.addObject("form", sessaoForm);
		return mv;
		
	}
	
	@Autowired SessaoDao sessaoDao;
	@PostMapping("/admin/sessao")
    @Transactional
    // spring valida o objeto Sessao (validacao com anotacoes do hibernate) e salva o resultado no BindingResult pra ser usado no metodo
    public ModelAndView salva(@Valid SessaoForm sessaoForm, BindingResult result){

        if (result.hasErrors()) {
            return form(sessaoForm.getSalaId(),sessaoForm);
        }
        Sessao sessao = sessaoForm.toSessao(salaDao, filmeDao);
        List<Sessao> sessoesDaSala = sessaoDao.buscaSessoesDaSala(sessao.getSala());
        GerenciadorDeSessao gerenciador = new GerenciadorDeSessao(sessoesDaSala);
        if (gerenciador.cabe(sessao)){
        	sessaoDao.save(sessao);
            ModelAndView view = new ModelAndView("redirect:/admin/sala/"+sessao.getSala().getId()+"/sessoes");
            return view;
        }
        
        return form(sessaoForm.getSalaId(),sessaoForm);

        
    }
	
	
	
}
