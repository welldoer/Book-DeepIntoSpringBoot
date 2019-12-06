package net.blogjava.welldoer.controller;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.google.gson.Gson;

import net.blogjava.welldoer.entity.MovieNeo4j;
import net.blogjava.welldoer.repository.ActorNeo4jRepository;
import net.blogjava.welldoer.repository.MovieNeo4jRepository;
import net.blogjava.welldoer.service.Neo4jPagesService;

@WebMvcTest
class MovieControllerTest {
	private static Logger logger = LoggerFactory.getLogger(MovieControllerTest.class);
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private MovieNeo4jRepository mockMovieRepository;
	@MockBean
	private ActorNeo4jRepository mockActorRepository;
	@MockBean
	private Neo4jPagesService<MovieNeo4j> mockPagesService;

	@Test
	void testUrlNew() throws Exception {
		mockMvc.perform(
				MockMvcRequestBuilders.get("/movie/new")
					.accept(MediaType.APPLICATION_JSON)
				)
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andDo(MockMvcResultHandlers.print());
	}

	@Test
	void testUrlSave() throws Exception {
		MovieNeo4j movie = new MovieNeo4j();
		movie.setName("西游记");
		movie.setPhoto("animal.jpg");
		Gson gson = new Gson();
		String json = gson.toJson(movie);
		mockMvc.perform(
				MockMvcRequestBuilders.post("/movie/save")
					.contentType(MediaType.APPLICATION_JSON)
					.content(json)
				)
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andDo(MockMvcResultHandlers.print());
	}
	
	@Test
	void testUrlShow() throws Exception {
		mockMvc.perform(
				MockMvcRequestBuilders.get("/movie/1")
					.contentType(MediaType.APPLICATION_JSON)
				)
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andDo(MockMvcResultHandlers.print());
	}

	@Test
	void testUrlEdit() throws Exception {
		mockMvc.perform(
				MockMvcRequestBuilders.get("/movie/edit/1")
					.contentType(MediaType.APPLICATION_JSON)
				)
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andDo(MockMvcResultHandlers.print());
	}

	@Test
	void testUrlUpdate() throws Exception {
		MovieNeo4j movie = new MovieNeo4j();
		movie.setName("西游记");
		movie.setPhoto("animal.jpg");
		Gson gson = new Gson();
		String json = gson.toJson(movie);
		mockMvc.perform(
				MockMvcRequestBuilders.post("/movie/update")
					.contentType(MediaType.APPLICATION_JSON)
					.content(json)
				)
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andDo(MockMvcResultHandlers.print());
	}

	@Test
	void testUrlDelete() throws Exception {
		mockMvc.perform(
				MockMvcRequestBuilders.get("/movie/delete/1")
					.contentType(MediaType.APPLICATION_JSON)
				)
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andDo(MockMvcResultHandlers.print());
	}

	@Test
	void testUrlList() throws Exception {
		mockMvc.perform(
				MockMvcRequestBuilders.get("/movie/list")
					.contentType(MediaType.APPLICATION_JSON)
				)
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andDo(MockMvcResultHandlers.print());
	}
}
