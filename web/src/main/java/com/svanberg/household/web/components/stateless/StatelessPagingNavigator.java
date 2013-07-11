package com.svanberg.household.web.components.stateless;

import org.apache.wicket.markup.html.link.AbstractLink;
import org.apache.wicket.markup.html.navigation.paging.IPageable;
import org.apache.wicket.markup.html.navigation.paging.IPagingLabelProvider;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigation;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;
import org.apache.wicket.request.mapper.parameter.INamedParameters;

public class StatelessPagingNavigator extends PagingNavigator
{
    private static final long serialVersionUID = 8219151328688000388L;

    public static final String PAGING_PAGE_PARAMETER = "page";

    public StatelessPagingNavigator(String id, INamedParameters parameters, IPageable pageable)
    {
        this(id, parameters, pageable, null);
    }

    public StatelessPagingNavigator(String id, INamedParameters parameters, IPageable pageable, IPagingLabelProvider labelProvider)
    {
        super(id, pageable, labelProvider);

        pageable.setCurrentPage(parameters.get(PAGING_PAGE_PARAMETER).toLong(0));
    }

    @Override
    protected AbstractLink newPagingNavigationIncrementLink(String id, IPageable pageable, int increment)
    {
        long pageNumber = pageable.getCurrentPage() + increment;
        return newPageLink(id, pageNumber);
    }

    @Override
    protected AbstractLink newPagingNavigationLink(String id, IPageable pageable, int pageNumber)
    {
        // The base PagingNavigator uses -1 to refer to the last page
        if (pageNumber < 0)
        {
            pageNumber += pageable.getPageCount();
        }
        return newPageLink(id, pageNumber);
    }

    @Override
    protected PagingNavigation newNavigation(final String id, final IPageable pageable, final IPagingLabelProvider labelProvider)
    {
        return new PagingNavigation(id, pageable, labelProvider)
        {
            @Override
            protected AbstractLink newPagingNavigationLink(String id, IPageable pageable, long pageNumber)
            {
                return newPageLink(id, pageNumber);
            }
        };
    }

    @Override
    protected boolean getStatelessHint()
    {
        return true;
    }

    private AbstractLink newPageLink(String id, long pageNumber)
    {
        ParameterLink link = new ParameterLink(id, PAGING_PAGE_PARAMETER, pageNumber);
        link.setAutoEnable(false);
        return link;
    }
}
