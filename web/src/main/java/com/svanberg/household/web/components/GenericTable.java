package com.svanberg.household.web.components;

import com.svanberg.household.domain.DomainObject;
import com.svanberg.household.web.spring.DataProviderPage;
import de.agilecoders.wicket.markup.html.bootstrap.table.TableBehavior;
import org.apache.wicket.extensions.markup.html.repeater.data.table.*;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.springframework.data.domain.Pageable;

import java.util.*;

/**
 * Generic table component for displaying a list of entities.
 *
 * @author Andreas Svanberg (andreass) <andreas.svanberg@mensa.se>
 */
public abstract class GenericTable<T extends DomainObject> extends Panel {
    private static final long serialVersionUID = 2853256830432178448L;

    private final List<IColumn<T, String>> columns = new ArrayList<>();
    private boolean selectionColumn = false;
    private SelectionColumn<T, String> selectColumn;
    private IModel<String> caption = null;

    public GenericTable(final String id) {
        super(id);
    }

    /**
     * Returns the entities that should be on the given page.
     *
     * @param pageable the page
     * @return the entities that should be on the page
     */
    protected abstract Iterable<T> getEntities(final Pageable pageable);

    /**
     * Add your columns here.
     *
     * @see #addColumn(org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn)
     */
    protected abstract void addColumns();

    /**
     * Returns the total number of entities in this table.
     *
     * @return the total number of entities in this table
     */
    protected abstract long count();

    /**
     * Sets the caption of the table.
     *
     * @param caption the caption
     * @return this
     * @see org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable#getCaptionModel()
     */
    public GenericTable<T> caption(final IModel<String> caption) {
        this.caption = caption;
        return this;
    }

    /**
     * Set if a selection column should be included in the table. It will be
     * the first column.
     *
     * @param selectionColumn {@code true} to include the column
     * @return this
     */
    public final GenericTable<T> selectionColumn(final boolean selectionColumn) {
        this.selectionColumn = selectionColumn;
        return this;
    }

    /**
     * Returns the currently selected entities.
     *
     * @return the currently selected entities
     */
    @SuppressWarnings("unchecked")
    public final Collection<T> getSelection() {
        return selectColumn != null ? selectColumn.getSelection() : Collections.EMPTY_SET;
    }

    /**
     * Adds a column to the table.
     *
     * @param column column to add
     * @see #addColumns()
     */
    protected final void addColumn(final IColumn<T, String> column) {
        columns.add(column);
    }

    /**
     * Returns the number of rows per page.
     *
     * @return the number of rows per page
     */
    protected long getRowsPerPage() {
        return 20;
    }

    private ISortableDataProvider<T, String> getProvider() {
        return new TableProvider();
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        if (selectionColumn) {
            addColumn(selectColumn = new SelectionColumn<>());
        }

        addColumns();

        ISortableDataProvider<T, String> provider = getProvider();

        DataTable<T, String> table = new DataTable<T, String>(TABLE, columns, provider, getRowsPerPage()) {
            @Override
            protected IModel<String> getCaptionModel() {
                return caption;
            }
        };
        //table.addTopToolbar(new HeadersToolbar<>(table, provider));
        table.addBottomToolbar(new NoRecordsToolbar(table));
        table.add(new TableBehavior().hover().striped());
        add(table);

        StatelessPagingNavigator paging = new StatelessPagingNavigator(PAGING, table)
        {
            @Override
            protected void onConfigure()
            {
                super.onConfigure();
                setVisibilityAllowed(count() > getRowsPerPage());
            }
        };
        add(paging);

        add(new NavigatorLabel(RESULTS, table) {
            @Override
            protected void onConfigure() {
                super.onConfigure();
                setVisibilityAllowed(count() > 0);
            }
        });
    }

    private class TableProvider extends SortableDataProvider<T, String> {
        private static final long serialVersionUID = 603419089968507278L;

        @Override
        public Iterator<? extends T> iterator(final long first, final long count) {
            return getEntities(new DataProviderPage(first, count, getSort())).iterator();
        }

        @Override
        public long size() {
            return count();
        }

        @Override
        public IModel<T> model(final T object) {
            return new EntityModel<>(object);
        }
    }

    public static final String TABLE = "table";
    public static final String PAGING = "paging";
    public static final String RESULTS = "results";
}
