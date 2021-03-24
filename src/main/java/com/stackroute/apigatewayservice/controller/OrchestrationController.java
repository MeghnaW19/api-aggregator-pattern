package com.stackroute.apigatewayservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stackroute.apigatewayservice.dto.BlogDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

/**
 * As in this assignment, we are working with creating Microservices, hence annotate
 * the class with @RestController annotation. A class annotated with @Controller annotation
 * has handler methods which returns a view. However, if we use @ResponseBody annotation along
 * with @Controller annotation, it will return the data directly in a serialized
 * format. Starting from Spring 4 and above, we can use @RestController annotation which
 * is equivalent to using @Controller and @ResponseBody. Annotate the class also with
 *
 * @PropertySource annotation.
 * @PropertySource annotation is a convenient and declarative mechanism for adding a
 * PropertySource to Spring's Environment.
 * Please note that the default path to use this controller should be "/api/v1"
 */


@RequestMapping("/api/v1")
@PropertySource("classpath:application.properties")
@RestController
public class OrchestrationController {


    private static Logger logger = LoggerFactory.getLogger(OrchestrationController.class);

    private Environment environment;

    /**
     * Constructor autowiring should be implemented for the  Environment
     * without using the new keyword
     **/
    @Autowired
    OrchestrationController(Environment environment) {
        this.environment = environment;
    }

    /**
     * API Version: 1.0
     * Define a handler method which will post a blog to the Content filter controller to validates the
     * blog and then formats the content using Format controller reading the Serialized
     * BlogDto object from request body .The response from the two services is aggregated and
     * returned back to the client.Use rest template to post data internally to both the services
     * If the content contains informal words,A message  'Invalid content' is returned back to the client.
     * Thus client makes only one request and can expect one response.APIGateway service Orchestrates the
     * requests , aggregates the response and sends it back to the client.
     * This handler method should return the status messages basis on
     * different situations:
     * 1. 200(OK - In case of successful validation/formatting of the blog
     * 2. 400(Bad Request - In case the request is not readable
     * This handler method should map to the URL "/api/v1/postBlog" using HTTP POST
     * method".
     */

    @PostMapping("/postBlog")
    public ResponseEntity<BlogDto> saveUser(@RequestBody BlogDto blogDto) {

        String filterUri = environment.getProperty("filter.uri");
        String formatUri = environment.getProperty("formatter.uri");
        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = null;


        try {
            RestTemplate restTemplate = new RestTemplate();
            BlogDto filteredResponse = restTemplate.postForObject(filterUri, blogDto, BlogDto.class);
            if (filteredResponse.getStatusMessage().equalsIgnoreCase("Valid content")) {
                BlogDto formattedResponse = restTemplate.postForObject(formatUri, blogDto, BlogDto.class);
                jsonInString = mapper.writeValueAsString(formattedResponse.getContent());
            } else
                jsonInString = mapper.writeValueAsString(filteredResponse.getStatusMessage() + "@" + filteredResponse.getCountOfInformalWords());
        } catch (JsonProcessingException e) {
            logger.info("json.parser.error", e.getMessage());

        } catch (ResourceAccessException rae) {

            logger.info("mocks.invoked");

        } catch (Exception ex) {
            logger.info(ex.getMessage());
        }

        logger.info("Blog Validated");
        return new ResponseEntity(jsonInString, HttpStatus.OK);
    }

}
