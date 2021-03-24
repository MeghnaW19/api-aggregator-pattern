package com.stackroute.contentfilterservice.service;

import com.stackroute.contentfilterservice.dto.BlogDto;


/**
 * This is the interface for FilterService
 **/

public interface FilterService {

    BlogDto validateBlog(BlogDto blogDto);
}
