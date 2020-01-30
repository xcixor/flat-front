package com.example.flat;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.net.URL;

import static org.junit.Assert.*;

public class ApiUtilTest {
    
    static URL sExpectedBaseURL;
    static String BaseEndPoint = "/";
    static ApiQueryBuilder sQueryBuilder;

//    run before all tests
    @BeforeClass
    public static void classSetup(){
        sQueryBuilder = new ApiQueryBuilder.QueryBuilder(BaseEndPoint).build();
    }

//    run before each test
    @Before
    public void setUp(){
        sExpectedBaseURL = sQueryBuilder.buildUrl();
    }

    @Test
    public void buildUrl() {
        String expectedStringUrl = sExpectedBaseURL.toString();
        String fullUrl = "http://35.208.96.236/rooms/";
        assertEquals(expectedStringUrl, fullUrl);
    }

    @Test
    public void assertBuiltUrlIsURL(){
        assertTrue(sExpectedBaseURL instanceof URL);
    }
}