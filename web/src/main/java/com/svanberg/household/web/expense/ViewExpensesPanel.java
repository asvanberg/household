package com.svanberg.household.web.expense;

import com.svanberg.household.domain.Expense;
import com.svanberg.household.service.ExpenseService;
import com.svanberg.household.web.spring.DataProviderPage;
import com.svanberg.household.web.wicket.EntityModel;

import de.agilecoders.wicket.markup.html.bootstrap.navigation.BootstrapPagingNavigator;
import de.agilecoders.wicket.markup.html.bootstrap.navigation.ajax.BootstrapAjaxPagingNavigator;
import de.agilecoders.wicket.markup.html.bootstrap.table.TableBehavior;
import org.apache.wicket.extensions.ajax.markup.html.repeater.data.table.AjaxFallbackHeadersToolbar;
import org.apache.wicket.extensions.markup.html.repeater.data.table.*;
import org.apache.wicket.extensions.markup.html.repeater.data.table.export.AbstractExportableColumn;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
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

    @SpringBean private ExpenseService expenseService;

    public ViewExpensesPanel(final String id) {
        super(id);

        ISortableDataProvider<Expense, String> provider = getProvider();

        DataTable<Expense, String> table = new DataTable<>(TABLE, getColumns(), provider, getRowsPerPage());
        table.addTopToolbar(new AjaxFallbackHeadersToolbar<>(table, provider));
        table.addBottomToolbar(new NoRecordsToolbar(table));
        table.add(new TableBehavior().hover().striped());
        add(table);

        BootstrapAjaxPagingNavigator paging = new BootstrapAjaxPagingNavigator(PAGING, table) {
            @Override
            protected void onConfigure() {
                setVisibilityAllowed(getPageable().getPageCount() > 1);
            }
        };
        paging.setPosition(BootstrapPagingNavigator.Position.Right);
        add(paging);
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
                return new EntityModel<>(object);
            }
        };
    }

    private List<? extends IColumn<Expense, String>> getColumns() {
        List<IColumn<Expense, String>> columns = new ArrayList<>();
        columns.add(new AbstractExportableColumn<Expense, String, Date>(new ResourceModel("date"), "date") {
            @Override
            public IModel<Date> getDataModel(final IModel<Expense> rowModel) {
                return model(from(rowModel).getDate());
            }
        });
        columns.add(new AbstractExportableColumn<Expense, String, String>(new ResourceModel("description"), "description") {
            @Override
            public IModel<String> getDataModel(IModel<Expense> rowModel) {
                return model(from(rowModel).getDescription());
            }
        });
        columns.add(new AbstractExportableColumn<Expense, String, String>(new ResourceModel("category"), "category") {
            @Override
            public IModel<String> getDataModel(IModel<Expense> rowModel) {
                return model(from(rowModel).getCategoryName());
            }
        });
        columns.add(new AbstractExportableColumn<Expense, String, Integer>(new ResourceModel("cost"), "cost") {
            @Override
            public IModel<Integer> getDataModel(IModel<Expense> rowModel) {
                return model(from(rowModel).getCost());
            }
        });
        return columns;
    }

    static final String TABLE = "table";
    static final String PAGING = "paging";
}
