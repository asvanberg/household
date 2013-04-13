package com.svanberg.household.web.test;

import com.svanberg.household.web.HouseholdWebApplication;

import org.apache.wicket.Component;
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
public abstract class WebUnitTest {

    protected WicketTester tester;
    protected ApplicationContextMock ctx = new ApplicationContextMock();

    @Before
    public void setUpTester() throws Exception {
        populateContext();

        tester = new WicketTester(new HouseholdWebApplication() {
            @Override
            protected SpringComponentInjector getInjector() {
                return new SpringComponentInjector(this, ctx);
            }
        });
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
