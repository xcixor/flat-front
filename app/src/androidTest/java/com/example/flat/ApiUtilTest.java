package com.example.flat;

import org.junit.Before;
import org.junit.Test;

import java.net.URL;

import static org.junit.Assert.*;

public class ApiUtilTest {
    
    private URL mExpectedBaseURL;

    @Before
    public void setUp(){
        String endPoint = "/";
        ApiQueryBuilder queryBuilder = new ApiQueryBuilder.QueryBuilder(endPoint).build();
        mExpectedBaseURL = queryBuilder.buildUrl();
    }

    @Test
    public void buildUrl() {
        String expectedStringUrl = this.mExpectedBaseURL.toString();
        String fullUrl = "http://35.208.96.236/rooms/";
        assertEquals(expectedStringUrl, fullUrl);
    }

    @Test
    public void assertBuiltUrlIsURL(){
        assertTrue(this.mExpectedBaseURL instanceof URL);
    }
}