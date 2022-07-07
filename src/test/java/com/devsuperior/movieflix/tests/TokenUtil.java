
package com.devsuperior.movieflix.tests;

import com.devsuperior.movieflix.dto.UserAuthenticationDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@Component
public class TokenUtil {
    @Autowired
    private ObjectMapper objectMapper;

    public String obtainAccessToken(MockMvc mockMvc, String username, String password) throws Exception {

        UserAuthenticationDto dto = new UserAuthenticationDto(username, password);
        String jsonBody = objectMapper.writeValueAsString(dto);

        ResultActions result = mockMvc
                .perform(post("/oauth/token")
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        String resultString = result.andReturn().getResponse().getContentAsString();

        JacksonJsonParser jsonParser = new JacksonJsonParser();
        return jsonParser.parseMap(resultString).get("Authorization").toString();
    }
}