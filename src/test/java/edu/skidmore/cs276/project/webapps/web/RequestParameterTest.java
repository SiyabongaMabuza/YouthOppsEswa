package edu.skidmore.cs276.project.webapps.web;

import static org.junit.Assert.*;

import org.junit.Test;

public class RequestParameterTest {
    @Test
    public void testGetKey() {
        assertEquals("LOGIN_ID key should be loginId", "loginId", RequestParameter.LOGIN_ID.getKey());
        assertEquals("USERNAME key should be username", "username", RequestParameter.USERNAME.getKey());
        assertEquals("LOGIN_PASSWORD key should be loginPassword", "loginPassword", RequestParameter.LOGIN_PASSWORD.getKey());
        assertEquals("CATEGORY key should be category", "category", RequestParameter.CATEGORY.getKey());
        assertEquals("RESOURCE_TITLE key should be resourceTitle", "resourceTitle", RequestParameter.RESOURCE_TITLE.getKey());
        assertEquals("RESOURCE_URL key should be resourceURL", "resourceURL", RequestParameter.RESOURCE_URL.getKey());
        assertEquals("RESOURCE_DESCRIPTION key should be resourceDecription", "resourceDecription", RequestParameter.RESOURCE_DESCRIPTION.getKey());
        assertEquals("CSRF_TOKEN key should be csrfToken", "csrfToken", RequestParameter.CSRF_TOKEN.getKey());
        assertEquals("CAPTCHA_VALUE key should be captchaValue", "captchaValue", RequestParameter.CAPTCHA_VALUE.getKey());
    }

    @Test
    public void testToString() {
        assertEquals("LOGIN_ID toString should match getKey", RequestParameter.LOGIN_ID.getKey(), RequestParameter.LOGIN_ID.toString());
        assertEquals("USERNAME toString should match getKey", RequestParameter.USERNAME.getKey(), RequestParameter.USERNAME.toString());
        assertEquals("LOGIN_PASSWORD toString should match getKey", RequestParameter.LOGIN_PASSWORD.getKey(), RequestParameter.LOGIN_PASSWORD.toString());
        assertEquals("CATEGORY toString should match getKey", RequestParameter.CATEGORY.getKey(), RequestParameter.CATEGORY.toString());
        assertEquals("RESOURCE_TITLE toString should match getKey", RequestParameter.RESOURCE_TITLE.getKey(), RequestParameter.RESOURCE_TITLE.toString());
        assertEquals("RESOURCE_URL toString should match getKey", RequestParameter.RESOURCE_URL.getKey(), RequestParameter.RESOURCE_URL.toString());
        assertEquals("RESOURCE_DESCRIPTION toString should match getKey", RequestParameter.RESOURCE_DESCRIPTION.getKey(), RequestParameter.RESOURCE_DESCRIPTION.toString());
        assertEquals("CSRF_TOKEN toString should match getKey", RequestParameter.CSRF_TOKEN.getKey(), RequestParameter.CSRF_TOKEN.toString());
        assertEquals("CAPTCHA_VALUE toString should match getKey", RequestParameter.CAPTCHA_VALUE.getKey(), RequestParameter.CAPTCHA_VALUE.toString());
    }
}
