package com.hacking.MemeService;

import com.hacking.MemeService.data.MemeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.skyscreamer.jsonassert.comparator.ArraySizeComparator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

//Run using 'mvn test -Dtest=MemeServiceApplicationTestsIT'

@AutoConfigureMockMvc
@SpringBootTest
@ContextConfiguration(classes = {MemeRepository.class, MemeServiceApplication.class, MemeRestController.class})
class MemeServiceApplicationTestsIT {

	@Autowired
	MockMvc mockMvc;

	@BeforeEach
	void setUp() throws Exception {
		deleteAllMemes();
	}

	@Test
	@DisplayName("Can get the Kenobi quote...ITS SPELLED KENOBI CHRIS")
	void getHello() throws Exception {
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/memes/hello")
                .contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
            .andReturn();
        assertEquals("hello there", result.getResponse().getContentAsString());
	}

	@Test
	@DisplayName("Getting all memes is empty when the application starts")
	void getAllMemes() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/api/memes")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.content().json("[]"))
			.andReturn();
	}
	
	@Test
	@DisplayName("Memes can be loaded in bulk.")
	void memesLoadCorrectly() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/api/memes/loadAllMemes")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.content().string(""))
			.andReturn();
		MvcResult resultOfLoad = mockMvc.perform(MockMvcRequestBuilders.get("/api/memes")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andReturn();

		String content = resultOfLoad.getResponse().getContentAsString();
		JSONAssert.assertEquals("[28]", content, new ArraySizeComparator(JSONCompareMode.LENIENT));
	}
	
	@Test
	@DisplayName("Can save a single meme.")
	void canSaveAMeme() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/api/memes/")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content("{\"id\": \"5\",\"title\": \"teset\",\"author\": \"test\",\"link\": \"testtt\",\"points\": 7}"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.content().string(""))
			.andReturn();
		mockMvc.perform(MockMvcRequestBuilders.get("/api/memes")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.content().json("[{\"id\": \"5\",\"title\": \"teset\",\"author\": \"test\",\"link\": \"testtt\",\"points\": 7}]"))
			.andReturn();
	}

	private void deleteAllMemes() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.delete("/api/memes/deleteAllMemes")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andReturn();
	}

	@Test
	@DisplayName("Students can submit an answer to a question.")
	void canPostAnAnswer() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/api/answers/1")
				.contentType(MediaType.APPLICATION_JSON)
				.header("studentName", "Johnny Bravo")
				.header("studentEmail", "email")
				.content("{}"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andReturn();
	}

	@Test
	@DisplayName("No student name header? BAD REQUEST!")
	void returnsBadRequestWhenStudentNameHeaderMissing() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/api/answers/1")
				.contentType(MediaType.APPLICATION_JSON)
				.header("studentEmail", "email")
				.content("{}"))
			.andExpect(MockMvcResultMatchers.status().isBadRequest())
			.andExpect(MockMvcResultMatchers.content().string("Required header 'studentName' is missing"))
			.andReturn();
	}

	@Test
	@DisplayName("No student email header? BAD REQUEST!")
	void returnsBadRequestWhenStudentEmailHeaderMissing() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/api/answers/1")
				.contentType(MediaType.APPLICATION_JSON)
				.header("studentName", "Johnny Bravo")
				.content("{}"))
			.andExpect(MockMvcResultMatchers.status().isBadRequest())
			.andExpect(MockMvcResultMatchers.content().string("Required header 'studentEmail' is missing"))
			.andReturn();
	}
}
