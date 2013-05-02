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
    private ApplicationContextMock ctx;


    @Override
    protected final WicketTester initWicketTester() throws Exception
    {
        setUpSpring();

        return new BetterWicketTester(new HouseholdWebApplication()
        {
            @Override
            protected SpringComponentInjector getInjector()
            {
                return new SpringComponentInjector(this, ctx);
            }
        });
    }

    private void setUpSpring() throws Exception
    {
        ctx = new ApplicationContextMock();

        populateContext();
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
