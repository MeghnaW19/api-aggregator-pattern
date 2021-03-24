package com.stackroute.apigatewayservice.commander;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stackroute.apigatewayservice.ApiGatewayServiceApplication;
import com.stackroute.apigatewayservice.dto.BlogDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(SpringExtension.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = ApiGatewayServiceApplication.class)
@AutoConfigureMockMvc
class ApiGatewayServiceApplicationTests {

    private BlogDto blogDto;

    private TestSuiteHelper testSuiteHelper;

    @Autowired
    private MockMvc mockMvc;

    @LocalServerPort
    private int port;

    private TestRestTemplate restTemplate;

    ApiGatewayServiceApplicationTests() throws Exception {
        TestSuiteHelper.startBackends();
    }

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);


        restTemplate = new TestRestTemplate();

        blogDto = new BlogDto(1, "recent movies", "Kabir Singh rocks", "anuchoudhry", "content submitted");
    }

    @AfterEach
    void tearDown() throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
       TestSuiteHelper.stopAllBackends();
        blogDto = null;
    }

    @Test
    void givenObjectWhenPostBlogWithInformalWordsThenReturnString() throws Exception {

        /**
         * All test cases are executed in one test case in order to avoid
         * running services multiple times
         * First case:To check count of informal words
         * and send message to user
         **/

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("http://localhost:8764/api/v1/postBlog")
                .accept(MediaType.APPLICATION_JSON).content(asJsonString(blogDto))
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.OK.value(), response.getStatus());

        ResponseEntity<BlogDto> validatedResp = restTemplate.postForEntity("http://localhost:" + 8345 +
                "/api/v1/validateBlog", blogDto, BlogDto.class);
        assertEquals("Invalid content", validatedResp.getBody().getStatusMessage());
        assertEquals(validatedResp.getStatusCode(), HttpStatus.OK);

        /**
         * Case 2 When the blog has no Informal words,
         * then it has to be checked first anf formatted
         */

        blogDto.setContent("Kabir Singh");
        RequestBuilder newRequestBuilder = MockMvcRequestBuilders
                .post("http://localhost:8764/api/v1/postBlog")
                .accept(MediaType.APPLICATION_JSON).content(asJsonString(blogDto))
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult newResult = mockMvc.perform(newRequestBuilder).andReturn();
        MockHttpServletResponse newResponse = newResult.getResponse();
        assertEquals(HttpStatus.OK.value(), newResponse.getStatus());
        ResponseEntity<BlogDto> validatedResposne = restTemplate.postForEntity("http://localhost:" + 8345 +
                "/api/v1/validateBlog", blogDto, BlogDto.class);
        assertEquals(validatedResposne.getStatusCode(), HttpStatus.OK);
        System.out.println("content obtained :\t" + response.getContentAsString());
        assertEquals("Valid content", validatedResposne.getBody().getStatusMessage());
        ResponseEntity<BlogDto> formattedResponses = restTemplate.postForEntity("http://localhost:" + 8346 +
                "/api/v1/formatContent", blogDto, BlogDto.class);
        System.out.println("Status message \t" + formattedResponses.getBody().getContent());//toString());
        assertEquals(formattedResponses.getStatusCode(), HttpStatus.OK);
            String value = "KABIR SINGH";
        assertEquals(value, formattedResponses.getBody().getContent());
        assertNull(response.getErrorMessage());

        /**
        * Case 3 Negetive case where wrong method is used.
        */

        RequestBuilder requestBuilders = MockMvcRequestBuilders
                .put("http://localhost:8764/api/v1/postBlog")
                .accept(MediaType.APPLICATION_JSON).content(asJsonString(blogDto))
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult results = mockMvc.perform(requestBuilders).andReturn();
        MockHttpServletResponse responses = results.getResponse();
        assertEquals(HttpStatus.METHOD_NOT_ALLOWED.value(), responses.getStatus());
        assertNotNull(responses.getContentAsString());
        assertEquals("Request method 'PUT' not supported", responses.getErrorMessage());


        /**
         * Case 4 Negetive case where wrong url is used.
         */
        RequestBuilder reqBuilder = MockMvcRequestBuilders
                .post("http://localhost:8764/log")
                .accept(MediaType.APPLICATION_JSON).content(asJsonString(blogDto))
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult res = mockMvc.perform(reqBuilder).andReturn();
        MockHttpServletResponse resp = res.getResponse();
        assertEquals(HttpStatus.NOT_FOUND.value(), resp.getStatus());
        assertNotNull(resp.getContentAsString());
        assertNull(resp.getErrorMessage());

    }

    private static String asJsonString(final Object obj) throws Exception {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new Exception();
        }

    }


}
