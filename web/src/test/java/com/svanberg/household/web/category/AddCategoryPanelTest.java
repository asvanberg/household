package com.svanberg.household.web.category;

import com.svanberg.household.web.test.WicketTest;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * @author Andreas Svanberg (andreass) <andreas.svanberg@mensa.se>
 */
public class AddCategoryPanelTest extends WicketTest {

    AddCategoryPanel panel;

    @Before
    public void setUp() throws Exception {
        panel = tester.startComponentInPage(AddCategoryPanel.class);
    }

    @Test
    public void testRenders() throws Exception {
        assertNotNull("Does not render", panel);
    }
}
