package com.svanberg.household.web.test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.assertEquals;

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
}
