package com.hacking.MemeService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.hacking.MemeService.data.Meme;
import com.hacking.MemeService.data.MemeRepository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

//Run using 'mvn test -Dtest=MemeServiceApplicationTestsIT'

@AutoConfigureMockMvc
@SpringBootTest
@ContextConfiguration(classes = {MemeRepository.class, MemeServiceApplication.class, MemeRestController.class})
class MemeServiceApplicationTestsIT {

	@Autowired
	MockMvc mockMvc;

	@Test
	@DisplayName("Can get the Kenobi quote...ITS SPELLED KENOBI CHRIS")
	void getHello() throws Exception {
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/memes/hello")
                .contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
                .andReturn();

        String resultText = result.getResponse().getContentAsString();
        assertNotNull(resultText);
        assertEquals("hello there", resultText);
	}

	@Test
	@DisplayName("Getting all memes is empty when the application starts")
	void getAllMemes() throws Exception {
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/memes")
		.contentType(MediaType.APPLICATION_JSON)
		.accept(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.content().json("[]"))
		.andReturn();
	}
	
	@Test
	@DisplayName("Memes can be loaded in bulk.")
	void memesLoadCorrectly() throws Exception {
		MvcResult loadMemes = mockMvc.perform(MockMvcRequestBuilders.get("/api/memes/loadAllMemes")
		.contentType(MediaType.APPLICATION_JSON)
		.accept(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.content().string(""))
		.andReturn();
		MvcResult resultOfLoad = mockMvc.perform(MockMvcRequestBuilders.get("/api/memes")
		.contentType(MediaType.APPLICATION_JSON)
		.accept(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.content().json("[{'id':'0','title':'Hello There','author':'Obi-Wan Kanobi','link':'https://www.youtube.com/watch?v=rEq1Z0bjdwc','points':9001},{'id':'1','title':'Hello There','author':'Obi-Wan Kanobi','link':'https://www.youtube.com/watch?v=rEq1Z0bjdwc','points':9001},{'id':'2','title':'Hello There','author':'Obi-Wan Kanobi','link':'https://www.youtube.com/watch?v=rEq1Z0bjdwc','points':9001},{'id':'3','title':'Hello There','author':'Obi-Wan Kanobi','link':'https://www.youtube.com/watch?v=rEq1Z0bjdwc','points':9001},{'id':'4','title':'Hello There','author':'Obi-Wan Kanobi','link':'https://www.youtube.com/watch?v=rEq1Z0bjdwc','points':9001}]"))
		.andReturn();
		MvcResult deleteMemes = mockMvc.perform(MockMvcRequestBuilders.delete("/api/memes/deleteAllMemes")
		.contentType(MediaType.APPLICATION_JSON)
		.accept(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andReturn();
		
	}
	
	@Test
	@DisplayName("Can save a single meme.")
	void canSaveAMeme() throws Exception {
		MvcResult saveMeme = mockMvc.perform(MockMvcRequestBuilders.post("/api/memes/")
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON)
			.content("{\"id\": \"5\",\"title\": \"teset\",\"author\": \"test\",\"link\": \"testtt\",\"points\": 7}"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.content().string(""))
			.andReturn();
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/memes")
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.content().json("[{\"id\": \"5\",\"title\": \"teset\",\"author\": \"test\",\"link\": \"testtt\",\"points\": 7}]"))
			.andReturn();
		MvcResult deleteMemes = mockMvc.perform(MockMvcRequestBuilders.delete("/api/memes/deleteAllMemes")
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andReturn();
	}

}
