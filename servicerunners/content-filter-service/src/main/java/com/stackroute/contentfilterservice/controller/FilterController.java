package com.stackroute.contentfilterservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stackroute.contentfilterservice.dto.BlogDto;
import com.stackroute.contentfilterservice.service.FilterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.ResourceAccessException;


/**
 * As in this assignment, we are working with creating Microservices, hence annotate
 * the class with @RestController annotation. A class annotated with @Controller annotation
 * has handler methods which returns a view. However, if we use @ResponseBody annotation along
 * with @Controller annotation, it will return the data directly in a serialized
 * format. Starting from Spring 4 and above, we can use @RestController annotation which
 * is equivalent to using @Controller and @ResponseBody. Annotate the class also with
 * Please note that the default path to use this controller should be "/api/v1"
 */
@RequestMapping("/api/v1/")
@RestController
public class FilterController {

    private static Logger logger = LoggerFactory.getLogger(FilterController.class);


    FilterService filterService;

    /**
     * Constructor autowiring should be implemented for the FilterService
     * without using the new keyword
     **/

    @Autowired
    FilterController(FilterService filterService) {
        this.filterService = filterService;
    }

    /**
     * API Version: 1.0
     * Define a handler method which will validate the
     * blog  reading the Serialized BlogDto object from request body sent by  APIGateWay service.
     * This handler method should return the status messages basis on
     * different situations:
     * 1. 200(OK - In case of successful validation of the blog
     * 2. 400(Bad Request - In case the request is not readable
     * This handler method should map to the URL "/api/v1/validateBlog" using HTTP POST
     * method".
     */

    @PostMapping("validateBlog")
    public ResponseEntity<String> validateBogPost(@RequestBody BlogDto blogDto) {

        BlogDto dto = filterService.validateBlog(blogDto);

        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = null;


        try {

            jsonInString = mapper.writeValueAsString(dto);
        } catch (JsonProcessingException e) {
            logger.info("json.parser.error", e.getMessage());

        } catch (ResourceAccessException rae) {

            logger.info("mocks.invoked");

        } catch (Exception ex) {
            logger.info(ex.getMessage());
        }

        return new ResponseEntity(jsonInString, HttpStatus.OK);
    }

}


