package net.blogjava.welldoer.controller;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import net.blogjava.welldoer.entity.ActorNeo4j;
import net.blogjava.welldoer.repository.ActorNeo4jRepository;

@RestController
@RequestMapping("/actor")
public class ActorController {
	private static Logger logger = LoggerFactory.getLogger(ActorController.class);
	
	@Autowired
	private ActorNeo4jRepository actorRepository;
	
	@GetMapping("/index")
	public ModelAndView index() {
		return new ModelAndView("/actor/index");
	}
	
	@GetMapping("/{id}")
	public ModelAndView show(ModelMap model, @PathVariable Long id) {
		Optional<ActorNeo4j> tmpActor = actorRepository.findById(id);
		if (tmpActor.isPresent()) {
			model.addAttribute("actor", tmpActor.get());
		}
		
		return new ModelAndView("actor/show");
	}
	
	@GetMapping("/new")
	public ModelAndView create() {
		return new ModelAndView("/actor/new");
	}
	
	@PostMapping("/save")
	public String save(ActorNeo4j actor) {
		actorRepository.save(actor);
		logger.info("新增 -> ID = {}", actor.getId());
		
		return "1";
	}
	
	@GetMapping("/edit/{id}")
	public ModelAndView edit(ModelMap model, @PathVariable Long id) {
		Optional<ActorNeo4j> tmpActor = actorRepository.findById(id);
		if (tmpActor.isPresent()) {
			model.addAttribute("actor", tmpActor.get());
		}
		
		return new ModelAndView("/actor/edit");
	}
	
	@PostMapping("/update")
	public String update(ActorNeo4j actor) {
		actorRepository.save(actor);
		logger.info("修改 -> ID = {}", actor.getId());
		
		return "1";
	}
	
	@GetMapping("/delete/{id}")
	public String delete(@PathVariable Long id) {
		actorRepository.deleteById(id);
		logger.info("删除 -> ID = {}", id);
		
		return "1";
	}
	
	@GetMapping("/list")
	public Page<ActorNeo4j> list(HttpServletRequest request) {
		String page = request.getParameter("page");
		String size = request.getParameter("size");
		Pageable pageable = PageRequest.of(
				page == null ? 0 : Integer.parseInt(page),
				size == null ? 10 : Integer.parseInt(size),
				Sort.by(Sort.Direction.DESC, "id"));
		
		return actorRepository.findAll(pageable);
	}
}
