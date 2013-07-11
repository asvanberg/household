package com.svanberg.household.web.components.stateless;

import org.apache.wicket.Page;
import org.apache.wicket.markup.html.link.AbstractLink;
import org.apache.wicket.markup.html.link.StatelessLink;
import org.apache.wicket.markup.html.navigation.paging.IPageable;
import org.apache.wicket.markup.html.navigation.paging.IPagingLabelProvider;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigation;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;
import org.apache.wicket.request.mapper.parameter.INamedParameters;
import org.apache.wicket.request.mapper.parameter.PageParameters;

public class StatelessPagingNavigator extends PagingNavigator
{
    private static final long serialVersionUID = 8219151328688000388L;

    public static final String PAGING_PAGE_PARAMETER = "page";

    private final IPageable pageable;

    public StatelessPagingNavigator(String id, INamedParameters parameters, IPageable pageable)
    {
        this(id, parameters, pageable, null);
    }

    public StatelessPagingNavigator(String id, INamedParameters parameters, IPageable pageable, IPagingLabelProvider labelProvider)
    {
        super(id, pageable, labelProvider);

        this.pageable = pageable;

        pageable.setCurrentPage(parameters.get(PAGING_PAGE_PARAMETER).toLong(0));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected AbstractLink newPagingNavigationIncrementLink(final String id, final IPageable pageable, final int increment)
    {
        return new StatelessIncrementPageLink(id, pageable.getCurrentPage(), increment);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected AbstractLink newPagingNavigationLink(final String id, final IPageable pageable, final int pageNumber)
    {
        return new StatelessPageLink(id, pageNumber);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected PagingNavigation newNavigation(final String id, final IPageable pageable, final IPagingLabelProvider labelProvider)
    {
        return new StatelessPagingNavigation(id, pageable, labelProvider);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean getStatelessHint()
    {
        return true;
    }

    private class StatelessPagingNavigation extends PagingNavigation
    {
        private static final long serialVersionUID = 66475276441317319L;

        public StatelessPagingNavigation(final String id, final IPageable pageable, final IPagingLabelProvider labelProvider)
        {
            super(id, pageable, labelProvider);
        }

        @Override
        protected AbstractLink newPagingNavigationLink(final String id, final IPageable pageable, final long pageIndex)
        {
            return new StatelessPageLink(id, pageIndex);
        }
    }

//    private static class PageLink extends ParameterLink
//    {
//        public PageLink(String id, long pageIndex)
//        {
//            super(id, PAGING_PAGE_PARAMETER, "" + pageIndex);
//        }
//    }

    private class StatelessPageLink extends StatelessLink<Void>
    {
        private static final long serialVersionUID = -7655478552068484458L;

        protected final long pageNumber;

        private StatelessPageLink(final String id, final long pageNumber)
        {
            super(id);
            this.pageNumber = pageNumber;
            setAutoEnable(false);
        }

        @Override
        public void onClick()
        {
            // Do nothing
        }

        @Override
        protected boolean linksTo(final Page page)
        {
            return getPageNumber() == pageable.getCurrentPage();
        }

        @Override
        protected CharSequence getURL()
        {
            PageParameters pp = new PageParameters(getPage().getPageParameters());
            pp.set(PAGING_PAGE_PARAMETER, getPageNumber());
            return urlFor(getPage().getPageClass(), pp);
        }

        protected long getPageNumber()
        {
            long p = pageNumber;
            if (p < 0)
            {
                p += pageable.getPageCount();
            }
            if (p >= pageable.getPageCount()) {
                p -= pageable.getPageCount();
            }
            return p;
        }
    }

    private class StatelessIncrementPageLink extends StatelessPageLink
    {
        private static final long serialVersionUID = -3462188018169682223L;

        private final long increment;

        private StatelessIncrementPageLink(final String id, final long pageNumber, final long increment)
        {
            super(id, pageNumber);
            this.increment = increment;
        }

        @Override
        protected long getPageNumber()
        {
            long p = pageNumber + increment;
            return Math.max(0, Math.min(p, pageable.getPageCount() - 1));
        }
    }
}
