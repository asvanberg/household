package com.svanberg.household.web.components.stateless;

import com.svanberg.household.web.test.WicketTest;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ParameterLinkTest extends WicketTest
{
    @Test
    public void testAddsNewParameter() throws Exception
    {
        ParameterLink link = new ParameterLink("id", DummyPage.class, "key", "value");
        tester().startComponentInPage(link);

        PageParameters parameters = tester().getLastRenderedPage().getPageParameters();
        assertTrue(parameters.get("key").isEmpty());

        tester().clickLink(link);

        parameters = tester().getLastRenderedPage().getPageParameters();
        assertFalse(parameters.get("key").isEmpty());
        assertEquals("value", parameters.get("key").toOptionalString());
    }

    public static class DummyPage extends WebPage
    {
        public DummyPage()
        {
        }
    }
}
