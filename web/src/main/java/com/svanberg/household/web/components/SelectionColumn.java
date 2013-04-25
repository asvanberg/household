package com.svanberg.household.web.components;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxCheckBox;
import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractColumn;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

/**
 * @author Andreas Svanberg (andreass) <andreas.svanberg@mensa.se>
 */
public class SelectionColumn<T, S> extends AbstractColumn<T, S> {
    private static final long serialVersionUID = -3395307495003514249L;

    private Collection<IModel<T>> selection = new HashSet<>();
    private Collection<RowCheckbox> checkBoxes = new ArrayList<>();

    public SelectionColumn() {
        super(null);
    }

    public Collection<IModel<T>> getSelection() {
        return selection;
    }

    @Override
    public Component getHeader(final String componentId) {
        return new CellWrapper(componentId, new AjaxCheckBox(CellWrapper.COMPONENT_ID, Model.of(false)) {
            @Override
            protected void onUpdate(final AjaxRequestTarget target) {
                for (RowCheckbox checkbox : checkBoxes) {
                    target.add(checkbox);
                    checkbox.setModelObject(getModelObject());

                    if (getModelObject()) {
                        selection.add(checkbox.model);
                    }
                    else {
                        selection.remove(checkbox.model);
                    }
                }
            }
        });
    }

    @Override
    public void populateItem(final Item<ICellPopulator<T>> cellItem, final String componentId, final IModel<T> rowModel) {
        cellItem.add(new CellWrapper(componentId, new RowCheckbox(CellWrapper.COMPONENT_ID, rowModel)));
    }

    private class CellWrapper extends Panel {
        private static final long serialVersionUID = 1839684361291536337L;

        public static final String COMPONENT_ID = "checkbox";

        public CellWrapper(final String id, final Component component) {
            super(id);
            add(component);
        }
    }

    private class RowCheckbox extends AjaxCheckBox {
        private static final long serialVersionUID = -2631116435108003056L;

        private final IModel<T> model;

        public RowCheckbox(final String id, final IModel<T> model) {
            super(id, Model.of(false));

            this.model = model;

            checkBoxes.add(this);
        }

        @Override
        protected void onUpdate(final AjaxRequestTarget target) {
            if (getModelObject()) {
                selection.add(model);
            }
            else {
                selection.remove(model);
            }
        }
    }
}
