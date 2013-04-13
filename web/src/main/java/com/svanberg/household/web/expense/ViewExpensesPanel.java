package com.svanberg.household.web.expense;

import com.svanberg.household.domain.Expense;
import com.svanberg.household.service.ExpenseService;
import com.svanberg.household.web.spring.DataProviderPage;

import org.apache.wicket.extensions.markup.html.repeater.data.table.DefaultDataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.ISortableDataProvider;
import org.apache.wicket.extensions.markup.html.repeater.data.table.export.AbstractExportableColumn;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import static org.wicketeer.modelfactory.ModelFactory.from;
import static org.wicketeer.modelfactory.ModelFactory.model;

/**
 * @author Andreas Svanberg (andreass) <andreas.svanberg@mensa.se>
 */
public class ViewExpensesPanel extends Panel {
    private static final long serialVersionUID = -2947931645707959506L;

    @SpringBean ExpenseService expenseService;

    public ViewExpensesPanel(final String id) {
        super(id);

        add(new DefaultDataTable<>(TABLE, getColumns(), getProvider(), getRowsPerPage()));
    }

    private int getRowsPerPage() {
        return 20;
    }

    private ISortableDataProvider<Expense, String> getProvider() {
        return new SortableDataProvider<Expense, String>() {
            @Override
            public Iterator<? extends Expense> iterator(final long first, final long count) {
                return expenseService.findAll(new DataProviderPage(first, count, getSort())).iterator();
            }

            @Override
            public long size() {
                return expenseService.count();
            }

            @Override
            public IModel<Expense> model(final Expense object) {
                return Model.of(object);
            }
        };
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
