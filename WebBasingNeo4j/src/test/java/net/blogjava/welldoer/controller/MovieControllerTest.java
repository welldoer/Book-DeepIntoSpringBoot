package net.blogjava.welldoer.controller;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
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
	
	@Captor
	private ArgumentCaptor<MovieNeo4j> movieArgument;
	
	@Mock
	private MovieNeo4j mockMovie;

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
		String formSubmittedText = "name=西游记&createDate=2019-12-13 9:33:18";
		Date expectDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2019-12-13 9:33:18");
		
		MvcResult mvcResult = mockMvc.perform(
				MockMvcRequestBuilders.post("/movie/save")
					.contentType(MediaType.APPLICATION_FORM_URLENCODED)
					.content(formSubmittedText)
				)
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andDo(MockMvcResultHandlers.print())
			.andReturn();
		
		assertThat(mvcResult.getResponse().getContentAsString()).isEqualTo("1");
		Mockito.verify(mockMovieRepository).save(movieArgument.capture());
		assertThat(movieArgument.getValue().getName()).isEqualTo("西游记");
		assertThat(movieArgument.getValue().getCreateDate()).isEqualTo(expectDate);
	}
	
	@Test
	void testUrlShow() throws Exception {
		MovieNeo4j movie = new MovieNeo4j();
		movie.setName("testShow");
		Optional<MovieNeo4j> optionalMovie = Optional.of(movie);
		Mockito.when(mockMovieRepository.findById(1L)).thenReturn(optionalMovie);

		mockMvc.perform(
				MockMvcRequestBuilders.get("/movie/1")
					.contentType(MediaType.APPLICATION_JSON)
				)
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andDo(MockMvcResultHandlers.print());
	}

	@Test
	void testUrlEdit() throws Exception {
		MovieNeo4j movie = new MovieNeo4j();
		movie.setName("testEdit");
		Optional<MovieNeo4j> optionalMovie = Optional.of(movie);
		Mockito.when(mockMovieRepository.findById(1L)).thenReturn(optionalMovie);

		mockMvc.perform(
				MockMvcRequestBuilders.get("/movie/edit/1")
					.contentType(MediaType.APPLICATION_JSON)
				)
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andDo(MockMvcResultHandlers.print());
	}

	@Test
	void testUrlUpdate() throws Exception {
		String formSubmittedText = "id=5&name=西游记&createDate=2019-12-16 16:56:18";
		Date expectDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2019-12-16 16:56:18");
		
		when(mockMovieRepository.findById(5L)).thenReturn(Optional.of(mockMovie));
		when(mockMovie.getId()).thenReturn(5L);
		when(mockMovie.getName()).thenReturn("西游记");
		when(mockMovie.getCreateDate()).thenReturn(expectDate);
		
		MvcResult mvcResult = mockMvc.perform(
				MockMvcRequestBuilders.post("/movie/update")
					.contentType(MediaType.APPLICATION_FORM_URLENCODED)
					.content(formSubmittedText)
				)
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andDo(MockMvcResultHandlers.print())
			.andReturn();
		
		assertThat(mvcResult.getResponse().getContentAsString()).isEqualTo("1");
		Mockito.verify(mockMovieRepository).save(movieArgument.capture());
		assertThat(movieArgument.getValue().getId()).isEqualTo(5);
		assertThat(movieArgument.getValue().getName()).isEqualTo("西游记");
		assertThat(movieArgument.getValue().getCreateDate()).isEqualTo(expectDate);
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
