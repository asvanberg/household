package com.svanberg.household.web.components.stateless;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractColumn;
import org.apache.wicket.extensions.model.AbstractCheckBoxModel;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import java.util.Collection;
import java.util.HashSet;
import java.util.UUID;

/**
 * Column that renders checkboxes to enable selection of columns. The table
 * <em>must</em> be wrapped in a {@link org.apache.wicket.markup.html.form.Form}
 * to process the checkboxes and update the selection.
 *
 * @author Andreas Svanberg (andreass) <andreas.svanberg@mensa.se>
 */
public class SelectColumn<T, S> extends AbstractColumn<T, S>
{
    private static final long serialVersionUID = -3750337129631785186L;

    private final String uuid = UUID.randomUUID().toString();
    private final transient Collection<IModel<T>> selection = new HashSet<>();

    /**
     * No display model since the header renders a (de)select all checkbox.
     */
    public SelectColumn()
    {
        super(null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void populateItem(final Item<ICellPopulator<T>> cellItem, final String componentId, final IModel<T> rowModel)
    {
        cellItem.add(new Cell(componentId, new SelectModel(rowModel)));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Component getHeader(final String componentId)
    {
        Cell cell = new Cell(componentId, Model.of(Boolean.FALSE));
        Component checkbox = cell.get(CHECKBOX);
        checkbox.add(new Behavior()
        {
            @Override
            public void onComponentTag(final Component component, final ComponentTag tag)
            {
                tag.put("onclick", getToggleJavascript());
            }
        });
        return cell;
    }

    /**
     * Returns the current selection. Should only be used after the wrapping
     * form has been processed, before that it will always return an empty
     * collection.
     *
     * @return selected rows at submit
     */
    public Collection<IModel<T>> getSelection()
    {
        return selection;
    }

    /**
     * Returns the javascript used by the header checkbox to toggle selection
     * of all rows.
     *
     * @return javascript to toggle all rows
     */
    protected String getToggleJavascript()
    {
        return String.format("var val=$(this).prop('checked'); $('.%s').each(function() { $(this).prop('checked', val); })", uuid);
    }

    /**
     * Factory method for the checkboxes to render inside the cells.
     *
     * @param componentId id of the checkbox
     * @param model       model the checkbox must use
     * @return the checkbox
     */
    protected CheckBox newCheckBox(final String componentId, final IModel<Boolean> model)
    {
        return new CheckBox(componentId, model);
    }

    private class Cell extends Panel
    {
        private static final long serialVersionUID = -7034080503986706786L;

        public Cell(final String id, final IModel<Boolean> model)
        {
            super(id);

            add(newCheckBox(CHECKBOX, model).add(new AttributeAppender("class", uuid)));
        }
    }

    private class SelectModel extends AbstractCheckBoxModel
    {
        private static final long serialVersionUID = -5985234926184976432L;

        private final IModel<T> rowModel;

        private SelectModel(final IModel<T> rowModel)
        {
            this.rowModel = rowModel;
        }

        @Override
        public boolean isSelected()
        {
            return selection.contains(rowModel);
        }

        @Override
        public void select()
        {
            selection.add(rowModel);
        }

        @Override
        public void unselect()
        {
            selection.remove(rowModel);
        }

        @Override
        public void detach()
        {
            rowModel.detach();
        }
    }

    // wicket ids for testing
    public static final String CHECKBOX = "checkbox";
}
