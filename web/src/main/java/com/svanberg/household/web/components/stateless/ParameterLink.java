package com.svanberg.household.web.components.stateless;

import org.apache.wicket.Page;
import org.apache.wicket.markup.html.link.StatelessLink;
import org.apache.wicket.request.mapper.parameter.PageParameters;

public class ParameterLink extends StatelessLink<Void>
{
    private static final long serialVersionUID = 2017427030134271901L;

    private final Class<? extends Page> page;
    private final String parameter;
    private final String value;

    public ParameterLink(String id, Class<? extends Page> page, String parameter, String value)
    {
        super(id);

        this.page = page;
        this.parameter = parameter;
        this.value = value;
    }

    @Override
    public void onClick()
    {
        setResponsePage(page, getSortParameters());
    }

    @Override
    protected CharSequence getURL()
    {
        return urlFor(page, getSortParameters());
    }

    private PageParameters getSortParameters()
    {
        PageParameters sortParameters = new PageParameters();
        sortParameters.mergeWith(getPage().getPageParameters());
        sortParameters.set(parameter, value);
        return sortParameters;
    }
}
