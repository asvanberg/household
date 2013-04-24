package com.svanberg.household.web.test;

import com.svanberg.household.web.HouseholdWebApplication;

import org.apache.wicket.Component;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.apache.wicket.spring.test.ApplicationContextMock;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.lang.reflect.Field;

/**
 * @author Andreas Svanberg (andreass) <andreas.svanberg@mensa.se>
 */
@RunWith(MockitoJUnitRunner.class)
public abstract class WicketTest {

    private WicketTester tester;
    private ApplicationContextMock ctx;

    @Before
    public final void init() throws Exception {
        ctx = new ApplicationContextMock();

        populateContext();

        tester = new BetterWicketTester(new HouseholdWebApplication() {
            @Override
            protected SpringComponentInjector getInjector() {
                return new SpringComponentInjector(this, ctx);
            }
        });
    }

    protected WicketTester tester() {
        return tester;
    }

    private static class BetterWicketTester extends WicketTester {
        private BetterWicketTester(final WebApplication application) {
            super(application);
        }

        protected String createPageMarkup(final String componentId) {
            return "<html><head></head><body><div wicket:id='" + componentId +
                    "'></div></body></html>";
        }
    }

    protected String path(Object... parts) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < parts.length; i++) {
            if (parts[i] instanceof Component) {
                sb.append(((Component) parts[i]).getId());
            } else {
                sb.append(parts[i]);
            }
            if (i + 1 < parts.length)
                sb.append(Component.PATH_SEPARATOR);
        }
        return sb.toString();
    }

    private void populateContext() throws Exception {
        for (Class<?> clazz = getClass(); clazz != null; clazz = clazz.getSuperclass()) {
            for (Field f : clazz.getDeclaredFields()) {
                boolean wasAccessible = f.isAccessible();
                f.setAccessible(true);
                if (f.getAnnotation(Mock.class) != null) {
                    ctx.putBean(f.get(this));
                }
                f.setAccessible(wasAccessible);
            }
        }
    }
}
