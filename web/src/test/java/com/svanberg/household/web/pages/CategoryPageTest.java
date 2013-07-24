package com.svanberg.household.web.pages;

import com.svanberg.household.service.CategoryService;
import com.svanberg.household.web.test.SpringWicketTest;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.junit.Assert.assertNotNull;

/**
 * @author Andreas Svanberg (andreass) <andreas.svanberg@mensa.se>
 */
public class CategoryPageTest extends SpringWicketTest
{

    @Mock private CategoryService categoryService;

    private CategoryPage page;

    @Before
    public void setUp() throws Exception {
        page = tester().startPage(CategoryPage.class);
    }

    @Test
    public void testName() throws Exception {
        assertNotNull("Page does not render", page);
    }
}
