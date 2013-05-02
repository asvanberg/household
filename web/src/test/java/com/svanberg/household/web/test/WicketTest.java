package com.svanberg.household.web.test;

import org.apache.wicket.Component;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * @author Andreas Svanberg (andreass) <andreas.svanberg@mensa.se>
 */
@RunWith(MockitoJUnitRunner.class)
public class WicketTest
{
    private WicketTester tester;

    @Before
    public void setUpTester()
    {
        tester = initWicketTester();
    }

    protected WicketTester initWicketTester()
    {
        return new WicketTester();
    }

    protected WicketTester tester()
    {
        return tester;
    }

    protected String path(Object... parts)
    {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < parts.length; i++)
        {
            if (parts[i] instanceof Component)
            {
                sb.append(((Component) parts[i]).getId());
            } else
            {
                sb.append(parts[i]);
            }
            if (i + 1 < parts.length)
                sb.append(Component.PATH_SEPARATOR);
        }
        return sb.toString();
    }

    protected static class BetterWicketTester extends WicketTester
    {
        protected BetterWicketTester(final WebApplication application)
        {
            super(application);
        }

        @Override
        protected String createPageMarkup(final String componentId)
        {
            return "<html><head></head><body><div wicket:id='" + componentId +
                    "'></div></body></html>";
        }
    }
}
