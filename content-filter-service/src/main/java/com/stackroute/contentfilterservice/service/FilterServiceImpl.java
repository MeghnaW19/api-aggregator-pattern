package com.stackroute.contentfilterservice.service;

import com.stackroute.contentfilterservice.dto.BlogDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * Implement the service methods
 * Annotate the class with @Service annotation
 *
 * @Service - is an annotation that annotates classes at the service layer and
 **/
@Service
public class FilterServiceImpl implements FilterService {

    /**
     * This method is used to validate the Blog
     *
     * @param blogDto
     * @return BlogDto
     **/


    @Override
    public BlogDto validateBlog(BlogDto blogDto) {

        int counter = 0;
        ArrayList<String> contentArray = new ArrayList();

        contentArray.add("hi");
        contentArray.add("hey");
        contentArray.add("hello");
        contentArray.add("dude");
        contentArray.add("babes");
        contentArray.add("rocks");
        String content = blogDto.getContent();
        boolean flag = false;
        for (String testStr : contentArray) {
            if (content.contains(testStr)) {
                counter++;
                flag = true;
            }
        }
        if (flag) {
            blogDto.setStatusMessage("Invalid content");
            blogDto.setCountOfInformalWords(counter);
        } else {
            blogDto.setStatusMessage("Valid content");
        }


        return blogDto;
    }


}
