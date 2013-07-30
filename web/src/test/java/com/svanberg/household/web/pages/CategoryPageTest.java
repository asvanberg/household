package com.svanberg.household.web.pages;

import com.svanberg.household.service.CategoryService;
import com.svanberg.household.web.test.SpringWicketTest;
import org.apache.wicket.util.tester.FormTester;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static com.svanberg.household.web.pages.CategoryPage.*;
import static com.svanberg.household.web.test.Assert.assertStateless;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

public class CategoryPageTest extends SpringWicketTest
{
    public static final String NAME_INPUT = path(NAME, NAME + "_body", INPUT);
    public static final String DESCRIPTION_INPUT = path(DESCRIPTION, DESCRIPTION + "_body", INPUT);

    @Mock private CategoryService categoryService;

    private CategoryPage page;

    @Before
    public void setUp() throws Exception {
        page = tester().startPage(CategoryPage.class);
    }

    @Test
    public void renders() throws Exception {
        tester().assertRenderedPage(CategoryPage.class);
    }

    @Test
    public void stateless() throws Exception
    {
        assertStateless(page);
    }

    @Test
    public void create_category() throws Exception
    {
        String name = "Some name";
        String description = "Some description";

        FormTester formTester = tester().newFormTester(FORM);

        formTester.setValue(NAME_INPUT, name);
        formTester.setValue(DESCRIPTION_INPUT, description);

        formTester.submit();

        verify(categoryService).create(name, description);
    }

    @Test
    public void clears_form_after_creation() throws Exception
    {
        FormTester formTester = tester().newFormTester(FORM);

        formTester.setValue(NAME_INPUT, "Some name");
        formTester.setValue(DESCRIPTION_INPUT, "Some description");

        formTester.submit();

        tester().assertModelValue(path(FORM, NAME_INPUT), null);
        tester().assertModelValue(path(FORM, DESCRIPTION_INPUT), null);
    }

    @Test
    public void name_and_description_required() throws Exception
    {
        FormTester formTester = tester().newFormTester(FORM);

        formTester.submit();

        verify(categoryService, never()).create(anyString(), anyString());
    }
}
