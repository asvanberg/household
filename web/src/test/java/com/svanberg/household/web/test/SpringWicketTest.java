package com.svanberg.household.web.test;

import com.svanberg.household.web.HouseholdWebApplication;

import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.apache.wicket.spring.test.ApplicationContextMock;
import org.apache.wicket.util.tester.WicketTester;
import org.mockito.Mock;

import java.lang.reflect.Field;

/**
 * @author Andreas Svanberg (andreass) <andreas.svanberg@mensa.se>
 */
public abstract class SpringWicketTest extends WicketTest
{
    private ApplicationContextMock ctx = new ApplicationContextMock();

    @Override
    protected final WicketTester initWicketTester() throws Exception
    {
        populateContext();

        HouseholdWebApplication application = new HouseholdWebApplication();

        SpringComponentInjector.setDefaultContext(application, ctx);

        return new BetterWicketTester(application);
    }

    private void populateContext() throws Exception
    {
        for (Class<?> clazz = getClass(); clazz != null; clazz = clazz.getSuperclass())
        {
            for (Field f : clazz.getDeclaredFields())
            {
                boolean wasAccessible = f.isAccessible();
                f.setAccessible(true);
                if (f.getAnnotation(Mock.class) != null)
                {
                    ctx.putBean(f.get(this));
                }
                f.setAccessible(wasAccessible);
            }
        }
    }
}
