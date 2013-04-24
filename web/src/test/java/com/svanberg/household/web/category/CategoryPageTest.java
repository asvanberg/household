package com.svanberg.household.web.category;

import com.svanberg.household.service.CategoryService;
import com.svanberg.household.web.test.WicketTest;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.junit.Assert.assertNotNull;

/**
 * @author Andreas Svanberg (andreass) <andreas.svanberg@mensa.se>
 */
public class CategoryPageTest extends WicketTest {

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
