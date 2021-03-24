package com.stackroute.apigatewayservice.test;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import com.stackroute.runners.ContentFilterServiceBackendRunner;
import com.stackroute.runners.ContentFormattingServiceBackendRunner;

public class TestSuiteHelper {
    private static final List<Backend> activeBackends = new ArrayList<>();

    public TestSuiteHelper() {
    }

    public static void startBackends() throws Exception {
        startBackend("content-filter-service", "com.stackroute.runners.ContentFilterServiceBackendRunner","contentfilterservice");
        startBackend("content-formatting-service", "com.stackroute.runners.ContentFormattingServiceBackendRunner","contentformattingservice");
    }

    private static void startBackend(final String backendProjectName,
                                     final String backendClassName,final String backendPackageName) throws Exception {

        URL backendRunnerUrl =  new File("servicerunners/backend-runner/target/classes/com/stackroute/runners")
                .toURI().toURL();
        URL runnerUrl = new File("servicerunners/" + backendProjectName
                + "/target/classes/com/stackroute/"+backendPackageName).toURI().toURL();
        URL backendUrl = new File(backendProjectName
                + "/target/classes/com/stackroute/"+backendPackageName).toURI().toURL();
        URL[] urls = new URL[] { backendUrl, backendRunnerUrl, runnerUrl };
        URLClassLoader cl = new URLClassLoader(urls,
                TestSuiteHelper.class.getClassLoader());
        Class<?> runnerClass = cl.loadClass(backendClassName);
        Object runnerInstance = runnerClass.newInstance();

        final Backend backend = new Backend(runnerClass, runnerInstance);
        activeBackends.add(backend);

        runnerClass.getMethod("run").invoke(runnerInstance);
    }

    public static void stopAllBackends()
            throws IllegalAccessException, InvocationTargetException,
            NoSuchMethodException {
        for(Backend b : activeBackends) {
            b.runnerClass.getMethod("stop").invoke(b.runnerInstance);
        }
    }

    private static class Backend {
        private Class<?> runnerClass;
        private Object runnerInstance;

        public Backend(final Class<?> runnerClass,
                       final Object runnerInstance) {
            this.runnerClass = runnerClass;
            this.runnerInstance = runnerInstance;

        }
    }


     public static void main(String args[]) throws Exception {

        TestSuiteHelper.startBackends();


    }

}
