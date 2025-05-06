package edu.skidmore.cs276.project.webapps.web;

import static org.junit.Assert.*;

import org.junit.Test;

public class RequestAttributeTest {
    
    @Test
    public void testGetKey() {
        assertEquals("LOGIN_ID should return requestAttribLoginId", "requestAttribLoginId", RequestAttribute.LOGIN_ID.getKey());
        assertEquals("OPPS should return requestAttribOpps", "requestAttribOpps", RequestAttribute.OPPS.getKey());
        assertEquals("GRANTS should return requestAttribGrants", "requestAttribGrants", RequestAttribute.GRANTS.getKey());
        assertEquals("SCHOLARSHIPS should return requestAttribScholarships", "requestAttribScholarships", RequestAttribute.SCHOLARSHIPS.getKey());
        assertEquals("JOBS should return requestAttribJobs", "requestAttribJobs", RequestAttribute.JOBS.getKey());
        assertEquals("GRANTS_COUNT should return requestAttribGrantsCount", "requestAttribGrantsCount", RequestAttribute.GRANTS_COUNT.getKey());
        assertEquals("SCHOLARSHIPS_COUNT should return requestAttribScholarshipsCount", "requestAttribScholarshipsCount", RequestAttribute.SCHOLARSHIPS_COUNT.getKey());
        assertEquals("JOBS_COUNT should return requestAttribJobsCount", "requestAttribJobsCount", RequestAttribute.JOBS_COUNT.getKey());
        assertEquals("CSRF_TOKEN should return csrfToken", "csrfToken", RequestAttribute.CSRF_TOKEN.getKey());
        assertEquals("ERROR_MESSAGE should return requestAttribErrorMessage", "requestAttribErrorMessage", RequestAttribute.ERROR_MESSAGE.getKey());
    }

    @Test
    public void testToString() {
        assertEquals("LOGIN_ID toString should match getKey" , RequestAttribute.LOGIN_ID.getKey(), RequestAttribute.LOGIN_ID.toString());
        assertEquals("OPPS toString should match getKey" , RequestAttribute.OPPS.getKey(), RequestAttribute.OPPS.toString());
        assertEquals("GRANTS toString should match getKey" , RequestAttribute.GRANTS.getKey(), RequestAttribute.GRANTS.toString());
        assertEquals("SCHOLARSHIPS toString should match getKey" , RequestAttribute.SCHOLARSHIPS.getKey(), RequestAttribute.SCHOLARSHIPS.toString());
        assertEquals("JOBS toString should match getKey" , RequestAttribute.JOBS.getKey(), RequestAttribute.JOBS.toString());
        assertEquals("GRANTS_COUNT toString should match getKey" , RequestAttribute.GRANTS_COUNT.getKey(), RequestAttribute.GRANTS_COUNT.toString());
        assertEquals("SCHOLARSHIPS_COUNT toString should match getKey" , RequestAttribute.SCHOLARSHIPS_COUNT.getKey(), RequestAttribute.SCHOLARSHIPS_COUNT.toString());
        assertEquals("JOBS_COUNT toString should match getKey" , RequestAttribute.JOBS_COUNT.getKey(), RequestAttribute.JOBS_COUNT.toString());
        assertEquals("CSRF_TOKEN toString should match getKey" , RequestAttribute.CSRF_TOKEN.getKey(), RequestAttribute.CSRF_TOKEN.toString());
        assertEquals("ERROR_MESSAGE toString should match getKey" , RequestAttribute.ERROR_MESSAGE.getKey(), RequestAttribute.ERROR_MESSAGE.toString());
    }
}
