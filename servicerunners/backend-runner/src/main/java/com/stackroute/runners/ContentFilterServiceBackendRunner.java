package com.stackroute.runners;

import com.stackroute.contentfilterservice.ContentFilterServiceApplication;

public class ContentFilterServiceBackendRunner extends BackendRunner {
    public ContentFilterServiceBackendRunner() {
        super(ContentFilterServiceApplication.class,CustomizationBean.class);
    }
}