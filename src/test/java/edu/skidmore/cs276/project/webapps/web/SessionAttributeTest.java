package edu.skidmore.cs276.project.webapps.web;

import static org.junit.Assert.*;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SessionAttributeTest {
    @Test
    public void testGetKey() {
        assertEquals("OPPS key should be sessionAttribOpps", "sessionAttribOpps", SessionAttribute.OPPS.getKey());
        assertEquals("USER key should be sessionAttribUser", "sessionAttribUser", SessionAttribute.USER.getKey());
        assertEquals("ROLE key should be sessionAttribRole", "sessionAttribRole", SessionAttribute.ROLE.getKey());
        assertEquals("JOBS key should be sessionAttribJobs", "sessionAttribJobs", SessionAttribute.JOBS.getKey());
        assertEquals("SCHOLARSHIPS key should be sessionAttribScholarships", "sessionAttribScholarships", SessionAttribute.SCHOLARSHIPS.getKey());
        assertEquals("GRANTS key should be sessionAttribGrants", "sessionAttribGrants", SessionAttribute.GRANTS.getKey());
        assertEquals("CSRF key should be csrfToken", "csrfToken", SessionAttribute.CSRF.getKey());
    }

    @Test
    public void testToString() {
        assertEquals("OPPS toString should match getKey", SessionAttribute.OPPS.getKey(), SessionAttribute.OPPS.toString());
        assertEquals("USER toString should match getKey", SessionAttribute.USER.getKey(), SessionAttribute.USER.toString());
        assertEquals("ROLE toString should match getKey", SessionAttribute.ROLE.getKey(), SessionAttribute.ROLE.toString());
        assertEquals("JOBS toString should match getKey", SessionAttribute.JOBS.getKey(), SessionAttribute.JOBS.toString());
        assertEquals("SCHOLARSHIPS toString should match getKey", SessionAttribute.SCHOLARSHIPS.getKey(), SessionAttribute.SCHOLARSHIPS.toString());
        assertEquals("GRANTS toString should match getKey", SessionAttribute.GRANTS.getKey(), SessionAttribute.GRANTS.toString());
        assertEquals("CSRF toString should match getKey", SessionAttribute.CSRF.getKey(), SessionAttribute.CSRF.toString());
    }


}
