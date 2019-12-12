package net.blogjava.welldoer.controller;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.neo4j.ogm.cypher.Filter;
import org.neo4j.ogm.cypher.Filters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import net.blogjava.welldoer.entity.ActorNeo4j;
import net.blogjava.welldoer.entity.MovieNeo4j;
import net.blogjava.welldoer.repository.ActorNeo4jRepository;
import net.blogjava.welldoer.repository.MovieNeo4jRepository;
import net.blogjava.welldoer.service.Neo4jPagesService;

@RestController
@RequestMapping("/movie")
public class MovieController {
	private static Logger logger = LoggerFactory.getLogger(MovieController.class);
	
	@Autowired
	private MovieNeo4jRepository movieRepository;
	@Autowired
	private ActorNeo4jRepository actorRepository;
	@Autowired
	private Neo4jPagesService<MovieNeo4j> pagesService;

	@GetMapping("/index")
	public ModelAndView index() {
		return new ModelAndView("movie/index");
	}
	
	@GetMapping("/new")
	public ModelAndView create(ModelMap model) {
		String[] files = {"/images/movie/西游记.jpg", "/images/movie/西游记续集.jpg"};
		model.addAttribute("files", files);
		
		return new ModelAndView("movie/new");
	}
	
	@PostMapping(value = "/save")
	public String save(@RequestBody MovieNeo4j movie) {
		movieRepository.save(movie);
		logger.info("新增 -> ID = {}", movie.getId());
		
		return "1";
	}
	
	@GetMapping("/{id}")
	public ModelAndView show(ModelMap model, @PathVariable Long id) {
		Optional<MovieNeo4j> movie = movieRepository.findById(id);
		model.addAttribute("movie", movie);
		
		return new ModelAndView("movie/show");
	}
	
	@GetMapping("/edit/{id}")
	public ModelAndView update(ModelMap model, @PathVariable Long id) {
		Optional<MovieNeo4j> movie = movieRepository.findById(id);
		String[] files = {"/images/movie/西游记.jpg", "/images/movie/西游记续集.jpg"};
		String[] rolelist = {"唐僧", "孙悟空", "猪八戒", "沙僧"};
		Iterable<ActorNeo4j> actors = actorRepository.findAll();
		
		model.addAttribute("files", files);
		model.addAttribute("rolelist", rolelist);
		model.addAttribute("movie", movie);
		model.addAttribute("actors", actors);
		
		return new ModelAndView("movie/edit");
	}
	
	@PostMapping(value = "/update")
	public String update(MovieNeo4j movie, HttpServletRequest request) {
		String rolename = request.getParameter("rolename");
		String actorid = request.getParameter("actorid");
		
		Optional<MovieNeo4j> origMovie = movieRepository.findById(movie.getId());
		if (origMovie.isPresent()) {
			MovieNeo4j orig = origMovie.get();
			orig.setName(movie.getName());
			orig.setPhoto(movie.getPhoto());
			orig.setCreateDate(movie.getCreateDate());
			
			if (!StringUtils.isEmpty(rolename) && !StringUtils.isEmpty(actorid)) {
				Optional<ActorNeo4j> actor = actorRepository.findById(Long.getLong(actorid));
				if (actor.isPresent()) {
					orig.addRole(actor.get(), rolename);
				}
			}
			movieRepository.save(orig);
			logger.info("修改 -> ID: {}", orig.getId());
		}
		
		return "1";
	}
	
	@GetMapping("/delete/{id}")
	public String delete(@PathVariable Long id) {
		movieRepository.deleteById(id);
		logger.info("删除 -> ID: {}", id);
		
		return "1";
	}
	
	@GetMapping("/list")
	public Page<MovieNeo4j> list(HttpServletRequest request) {
		String name = request.getParameter("name");
		String page = request.getParameter("page");
		String size = request.getParameter("size");
		Pageable pageable = PageRequest.of(
				page == null ? 0 : Integer.parseInt(page),
				size == null ? 10 : Integer.parseInt(size),
				Sort.by(Sort.Direction.DESC, "id"));
		
		Filters filters = new Filters();
		if (!StringUtils.isEmpty(name)) {
			Filter filter = new Filter(null);
			Filter.setNameFromProperty(filter, "name");
			filters.add(filter);
		}
		
		return pagesService.findAll(MovieNeo4j.class, pageable, filters);
	}
}
