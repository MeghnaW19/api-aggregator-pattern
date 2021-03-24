package com.stackroute.contentformattingservice.service;

import com.stackroute.contentformattingservice.dto.BlogDto;
import org.springframework.stereotype.Service;

import java.util.Formatter;

/**
 * Implement the service methods
 * Annotate the class with @Service annotation
 *
 * @Service - is an annotation that annotates classes at the service layer and
 **/

@Service
public class FormatServiceImpl implements FormatService {

    /**
     * This method is used to format the Blog
     *
     * @param blogDto
     * @return BlogDto
     **/

    @Override

    public BlogDto formatBlog(BlogDto blogDto) {

        Formatter formatter = new Formatter();

        /**
         * Change the content to Uppercase
         * */
        String content = formatter.format(blogDto.getContent().toUpperCase()).toString();
        blogDto.setContent(content);
        return blogDto;
    }

}
