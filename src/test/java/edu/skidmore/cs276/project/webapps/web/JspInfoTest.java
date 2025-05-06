package edu.skidmore.cs276.project.webapps.web;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class JspInfoTest {
    @Test
    public void testGetJspPath() {
        assertEquals("REGISTER path should be /WEB-INF/hiddenjsp/register.jsp", "/WEB-INF/hiddenjsp/register.jsp", JspInfo.REGISTER.getJspPath());
        assertEquals("LOGIN path should be /WEB-INF/hiddenjsp/login.jsp", "/WEB-INF/hiddenjsp/login.jsp", JspInfo.LOGIN.getJspPath());
        assertEquals("OPPORTUNITIES_VIEW path should be /WEB-INF/hiddenjsp/opportunities.jsp", "/WEB-INF/hiddenjsp/opportunities.jsp", JspInfo.OPPORTUNITIES_VIEW.getJspPath());
        assertEquals("ADD_RESOURCE path should be /WEB-INF/hiddenjsp/addresource.jsp", "/WEB-INF/hiddenjsp/addresource.jsp", JspInfo.ADD_RESOURCE.getJspPath());
        assertEquals("GRANTS path should be /WEB-INF/hiddenjsp/grants.jsp", "/WEB-INF/hiddenjsp/grants.jsp", JspInfo.GRANTS.getJspPath());
        assertEquals("SCHOLARSHIPS path should be /WEB-INF/hiddenjsp/scholarships.jsp", "/WEB-INF/hiddenjsp/scholarships.jsp", JspInfo.SCHOLARSHIPS.getJspPath());
        assertEquals("JOBS path should be /WEB-INF/hiddenjsp/jobs.jsp", "/WEB-INF/hiddenjsp/jobs.jsp", JspInfo.JOBS.getJspPath());
    }

    @Test
    public void testToString() {
        assertEquals("REGISTER toString should match getJspPath", JspInfo.REGISTER.getJspPath(), JspInfo.REGISTER.toString());
        assertEquals("LOGIN toString should match getJspPath", JspInfo.LOGIN.getJspPath(), JspInfo.LOGIN.toString());
        assertEquals("OPPORTUNITIES_VIEW toString should match getJspPath", JspInfo.OPPORTUNITIES_VIEW.getJspPath(), JspInfo.OPPORTUNITIES_VIEW.toString());
        assertEquals("ADD_RESOURCE toString should match getJspPath", JspInfo.ADD_RESOURCE.getJspPath(), JspInfo.ADD_RESOURCE.toString());
        assertEquals("GRANTS toString should match getJspPath", JspInfo.GRANTS.getJspPath(), JspInfo.GRANTS.toString());
        assertEquals("SCHOLARSHIPS toString should match getJspPath", JspInfo.SCHOLARSHIPS.getJspPath(), JspInfo.SCHOLARSHIPS.toString());
        assertEquals("JOBS toString should match getJspPath", JspInfo.JOBS.getJspPath(), JspInfo.JOBS.toString());
    }
}
