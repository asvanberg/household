package com.svanberg.household.web.spring;

import org.apache.wicket.extensions.markup.html.repeater.util.SortParam;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/**
 * Adapter class between Wicket {@link org.apache.wicket.markup.repeater.data.IDataProvider}
 * and Spring {@link org.springframework.data.domain.Pageable}.
 *
 * @author Andreas Svanberg (andreass) <andreas.svanberg@mensa.se>
 */
public class PageAdater implements Pageable {

    private final long offset;
    private final long count;
    private final SortParam<String> sort;

    public PageAdater(final long offset, final long count, final SortParam<String> sort) {
        this.offset = offset;
        this.count = count;
        this.sort = sort;
    }

    @Override
    public int getPageNumber() {
        return (int) (offset / count);
    }

    @Override
    public int getPageSize() {
        return (int) count;
    }

    @Override
    public int getOffset() {
        return (int) offset;
    }

    @Override
    public Sort getSort() {
        if (sort == null)
            return null;

        return new Sort(
                sort.isAscending() ? Sort.Direction.ASC : Sort.Direction.DESC,
                sort.getProperty());
    }
}
