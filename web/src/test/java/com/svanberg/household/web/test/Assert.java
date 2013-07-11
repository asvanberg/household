package com.svanberg.household.web.test;

import org.apache.wicket.Component;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.util.visit.IVisit;
import org.apache.wicket.util.visit.IVisitor;

import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * @author Andreas Svanberg (andreass) <andreas.svanberg@mensa.se>
 */
public final class Assert
{
    private Assert()
    {
        throw new RuntimeException();
    }

    public static void assertSameDay(Date d1, Date d2)
    {
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();

        c1.setTime(d1);
        c2.setTime(d2);

        assertEquals("Wrong year", c1.get(Calendar.YEAR), c2.get(Calendar.YEAR));
        assertEquals("Wrong month", c1.get(Calendar.MONTH), c2.get(Calendar.MONTH));
        assertEquals("Wrong day", c1.get(Calendar.DATE), c2.get(Calendar.DATE));
    }

    public static void assertStateless(MarkupContainer container)
    {
        assertTrue(container + " is stateful", container.isStateless());

        Component stateful = container.visitChildren(new IVisitor<Component, Component>()
        {
            @Override
            public void component(Component object, IVisit<Component> visit)
            {
                if (!object.isStateless())
                {
                    visit.stop(object);
                }
            }
        });
        assertNull(stateful + " is stateful", stateful);
    }
}
