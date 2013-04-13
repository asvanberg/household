package com.svanberg.household.web.expense;

import com.svanberg.household.domain.Expense;

import org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.export.AbstractExportableColumn;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.data.EmptyDataProvider;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.wicketeer.modelfactory.ModelFactory.from;
import static org.wicketeer.modelfactory.ModelFactory.model;

/**
 * @author Andreas Svanberg (andreass) <andreas.svanberg@mensa.se>
 */
public class ViewExpensesPanel extends Panel {
    private static final long serialVersionUID = -2947931645707959506L;

    public ViewExpensesPanel(final String id) {
        super(id);

        add(new DataTable<>(TABLE, getColumns(), getProvider(), getRowsPerPage()));
    }

    private long getRowsPerPage() {
        return 1;
    }

    private IDataProvider<Expense> getProvider() {
        return EmptyDataProvider.getInstance();
    }

    private List<? extends IColumn<Expense, String>> getColumns() {
        List<IColumn<Expense, String>> columns = new ArrayList<>();
        columns.add(new AbstractExportableColumn<Expense, String, Date>(new ResourceModel("date", "Date"), "date") {
            @Override
            public IModel<Date> getDataModel(final IModel<Expense> rowModel) {
                return model(from(rowModel).getDate());
            }
        });
        return columns;
    }

    static final String TABLE = "table";
}
