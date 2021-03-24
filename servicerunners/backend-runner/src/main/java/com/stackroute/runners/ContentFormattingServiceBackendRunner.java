package com.stackroute.runners;
import com.stackroute.contentformattingservice.ContentFormattingServiceApplication;

public class ContentFormattingServiceBackendRunner extends BackendRunner {
        public ContentFormattingServiceBackendRunner() {
        super(ContentFormattingServiceApplication.class, CustomizationBean2.class);
    }
}
