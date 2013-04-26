package com.svanberg.household.web.expense;

import com.svanberg.household.domain.Expense;
import com.svanberg.household.service.ExpenseService;
import com.svanberg.household.web.components.GenericTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.export.AbstractExportableColumn;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.springframework.data.domain.Pageable;

import java.util.Date;

import static org.wicketeer.modelfactory.ModelFactory.from;
import static org.wicketeer.modelfactory.ModelFactory.model;

/**
 * @author Andreas Svanberg (andreass) <andreas.svanberg@mensa.se>
 */
public class ExpenseTable extends GenericTable<Expense> {
    private static final long serialVersionUID = 4492167689934515266L;

    private @SpringBean ExpenseService expenseService;

    public ExpenseTable(final String id) {
        super(id);

        selectionColumn(true);
        caption(new ResourceModel("caption"));
    }

    @Override
    protected Iterable<Expense> getEntities(Pageable pageable) {
        return expenseService.findAll(pageable);
    }

    @Override
    protected long count() {
        return expenseService.count();
    }

    @Override
    protected void addColumns() {
        addColumn(new AbstractExportableColumn<Expense, String, Date>(new ResourceModel("date"), "date") {
            @Override
            public IModel<Date> getDataModel(final IModel<Expense> rowModel) {
                return model(from(rowModel).getDate());
            }
        });
        addColumn(new AbstractExportableColumn<Expense, String, String>(new ResourceModel("description"), "description") {
            @Override
            public IModel<String> getDataModel(IModel<Expense> rowModel) {
                return model(from(rowModel).getDescription());
            }
        });
        addColumn(new AbstractExportableColumn<Expense, String, String>(new ResourceModel("category"), "category") {
            @Override
            public IModel<String> getDataModel(IModel<Expense> rowModel) {
                return model(from(rowModel).getCategoryName());
            }
        });
        addColumn(new AbstractExportableColumn<Expense, String, Integer>(new ResourceModel("cost"), "cost") {
            @Override
            public IModel<Integer> getDataModel(IModel<Expense> rowModel) {
                return model(from(rowModel).getCost());
            }
        });
    }
}
