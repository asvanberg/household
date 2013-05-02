package com.svanberg.household.web.category;

import com.svanberg.household.service.CategoryService;
import com.svanberg.household.web.test.SpringWicketTest;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.util.tester.FormTester;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * @author Andreas Svanberg (andreass) <andreas.svanberg@mensa.se>
 */
public class AddCategoryPanelTest extends SpringWicketTest
{

    @Mock CategoryService service;

    AddCategoryPanel panel;

    @Before
    public void setUp() throws Exception {
        panel = tester().startComponentInPage(AddCategoryPanel.class);
    }

    @Test
    public void testRenders() throws Exception {
        assertNotNull("Does not render", panel);
    }

    @Test
    public void testContainsForm() throws Exception {
        tester().assertComponent(AddCategoryPanel.FORM, Form.class);
    }

    @Test
    public void testContainsInput() throws Exception {
        tester().assertComponent(path(AddCategoryPanel.FORM, AddCategoryPanel.NAME_GROUP, AddCategoryPanel.NAME_GROUP + "_body", AddCategoryPanel.NAME), TextField.class);
        tester().assertComponent(path(AddCategoryPanel.FORM, AddCategoryPanel.DESCRIPTION_GROUP, AddCategoryPanel.DESCRIPTION_GROUP + "_body", AddCategoryPanel.DESCRIPTION), TextArea.class);
    }

    @Test
    public void testSubmittingFormCreatesCategory() throws Exception {
        // given
        String name = "Name";
        String description = "Description";

        // when
        FormTester formTester = tester().newFormTester(AddCategoryPanel.FORM);
        formTester.setValue(path(AddCategoryPanel.NAME_GROUP, AddCategoryPanel.NAME_GROUP + "_body", AddCategoryPanel.NAME), name);
        formTester.setValue(path(AddCategoryPanel.DESCRIPTION_GROUP, AddCategoryPanel.DESCRIPTION_GROUP + "_body", AddCategoryPanel.DESCRIPTION), description);
        formTester.submit();

        // then
        verify(service, times(1)).create(eq(name), eq(description));
    }
}
