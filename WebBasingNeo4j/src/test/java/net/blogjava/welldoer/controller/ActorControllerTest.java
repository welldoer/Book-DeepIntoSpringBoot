package net.blogjava.welldoer.controller;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
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

import net.blogjava.welldoer.entity.ActorNeo4j;
import net.blogjava.welldoer.entity.MovieNeo4j;
import net.blogjava.welldoer.repository.ActorNeo4jRepository;
import net.blogjava.welldoer.repository.MovieNeo4jRepository;
import net.blogjava.welldoer.service.Neo4jPagesService;

@WebMvcTest
class ActorControllerTest {
	private static Logger logger = LoggerFactory.getLogger(ActorControllerTest.class);

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private ActorNeo4jRepository mockActorRepository;
	@MockBean
	private MovieNeo4jRepository mockMovieRepository;
	@MockBean
	private Neo4jPagesService<MovieNeo4j> mockPagesService;
	
	@Mock
	private ActorNeo4j mockActor;

	@Captor
	private ArgumentCaptor<ActorNeo4j> actorArgument;
	
	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void testUrlIndex() throws Exception {
		mockMvc.perform(
				MockMvcRequestBuilders.get("/actor/index"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andDo(MockMvcResultHandlers.print());
	}

	@Test
	void testUrlShow() throws Exception {
		Optional<ActorNeo4j> tmpActor = Optional.of(mockActor);
		when(mockActorRepository.findById(1L)).thenReturn(tmpActor);
		when(mockActor.getName()).thenReturn("月亮");
		when(mockActor.getSex()).thenReturn(2);
		when(mockActor.getBorn()).thenReturn(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2019-12-15 8:6:18"));
		
		MvcResult mvcResult = mockMvc.perform(
				MockMvcRequestBuilders.get("/actor/1")
				)
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andDo(MockMvcResultHandlers.print())
			.andReturn();
		
		assertThat(mvcResult.getResponse().getContentAsString()).contains("月亮");
		assertThat(mvcResult.getResponse().getContentAsString()).contains("2019-12-15 08:06:18");
		assertThat(mvcResult.getResponse().getContentAsString()).contains("selected\">女");
	}
	
	@Test
	void testUrlNew() throws Exception {
		mockMvc.perform(
				MockMvcRequestBuilders.get("/actor/new")
				)
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andDo(MockMvcResultHandlers.print());
	}
	
	@Test
	void testUrlSave() throws Exception {
		String formSubmittedText = "name=悟空&born=2019-12-16 9:51:28";
		Date expectDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2019-12-16 9:51:28");
		
		MvcResult mvcResult = mockMvc.perform(
				MockMvcRequestBuilders.post("/actor/save")
					.contentType(MediaType.APPLICATION_FORM_URLENCODED)
					.content(formSubmittedText)
				)
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andDo(MockMvcResultHandlers.print())
			.andReturn();
		
		assertThat(mvcResult.getResponse().getContentAsString()).isEqualTo("1");
		Mockito.verify(mockActorRepository).save(actorArgument.capture());
		assertThat(actorArgument.getValue().getName()).isEqualTo("悟空");
		assertThat(actorArgument.getValue().getBorn()).isEqualTo(expectDate);
	}
	
	@Test
	void testUrlEdit() throws Exception {
		Optional<ActorNeo4j> tmpActor = Optional.of(mockActor);
		when(mockActorRepository.findById(2L)).thenReturn(tmpActor);
		when(mockActor.getName()).thenReturn("地面");
		when(mockActor.getSex()).thenReturn(1);
		when(mockActor.getBorn()).thenReturn(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2019-12-16 10:15:38"));
		
		MvcResult mvcResult = mockMvc.perform(
				MockMvcRequestBuilders.get("/actor/edit/2")
				)
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andDo(MockMvcResultHandlers.print())
			.andReturn();
		
		assertThat(mvcResult.getResponse().getContentAsString()).contains("地面");
		assertThat(mvcResult.getResponse().getContentAsString()).contains("2019-12-16 10:15:38");
		assertThat(mvcResult.getResponse().getContentAsString()).contains("selected\">男");
	}

	@Test
	void testUrlUpdate() throws Exception {
		String formSubmittedText = "name=八戒&born=2019-12-16 10:30:8";
		Date expectDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2019-12-16 10:30:8");
		
		MvcResult mvcResult = mockMvc.perform(
				MockMvcRequestBuilders.post("/actor/update")
					.contentType(MediaType.APPLICATION_FORM_URLENCODED)
					.content(formSubmittedText)
				)
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andDo(MockMvcResultHandlers.print())
			.andReturn();
		
		assertThat(mvcResult.getResponse().getContentAsString()).isEqualTo("1");
		Mockito.verify(mockActorRepository).save(actorArgument.capture());
		assertThat(actorArgument.getValue().getName()).isEqualTo("八戒");
		assertThat(actorArgument.getValue().getBorn()).isEqualTo(expectDate);
	}
	
	@Test
	void testUrlDelete() throws Exception {
		MvcResult mvcResult = mockMvc.perform(
				MockMvcRequestBuilders.get("/actor/delete/3")
				)
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andDo(MockMvcResultHandlers.print())
			.andReturn();
		
		assertThat(mvcResult.getResponse().getContentAsString()).isEqualTo("1");
	}
	
	@Test
	void testUrlList() throws Exception {
		mockMvc.perform(
				MockMvcRequestBuilders.get("/actor/list")
				)
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andDo(MockMvcResultHandlers.print());
	}
}
