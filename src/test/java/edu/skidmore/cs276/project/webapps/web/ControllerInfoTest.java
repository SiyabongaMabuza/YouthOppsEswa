package edu.skidmore.cs276.project.webapps.web;

import static org.junit.Assert.*;

import org.junit.Test;

public class ControllerInfoTest {
    
    @Test
    public void testGetMappedPath() {
        assertEquals("LOGIN path should be /login", "/login", ControllerInfo.LOGIN.getMappedPath());
        assertEquals("OPPORTUNITIES_VIEW path should be /viewopportunities", "/viewopportunities", ControllerInfo.OPPORTUNITIES_VIEW.getMappedPath());
        assertEquals("ADD_RESOURCE path should be /addresource", "/addresource", ControllerInfo.ADD_RESOURCE.getMappedPath());
        assertEquals("GRANTS path should be /grants", "/grants", ControllerInfo.GRANTS.getMappedPath());
        assertEquals("SCHOLARSHIPS path should be /scholarships", "/scholarships", ControllerInfo.SCHOLARSHIPS.getMappedPath());
        assertEquals("JOBS path should be /jobs", "/jobs", ControllerInfo.JOBS.getMappedPath());
    }

    @Test
    public void testToString() {
        assertEquals("LOGIN toString should match getMappedPath", ControllerInfo.LOGIN.getMappedPath(), ControllerInfo.LOGIN.toString());
        assertEquals("OPPORTUNITIES_VIEW toString should match getMappedPath", ControllerInfo.OPPORTUNITIES_VIEW.getMappedPath(), ControllerInfo.OPPORTUNITIES_VIEW.toString());
        assertEquals("ADD_RESOURCE toString should match getMappedPath", ControllerInfo.ADD_RESOURCE.getMappedPath(), ControllerInfo.ADD_RESOURCE.toString());
        assertEquals("GRANTS toString should match getMappedPath", ControllerInfo.GRANTS.getMappedPath(), ControllerInfo.GRANTS.toString());
        assertEquals("SCHOLARSHIPS toString should match getMappedPath", ControllerInfo.SCHOLARSHIPS.getMappedPath(), ControllerInfo.SCHOLARSHIPS.toString());
        assertEquals("JOBS toString should match getMappedPath", ControllerInfo.JOBS.getMappedPath(), ControllerInfo.JOBS.toString());
    }
    
}
