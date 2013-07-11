package com.svanberg.household.web.components.stateless;

import org.apache.wicket.Page;
import org.apache.wicket.markup.html.link.StatelessLink;
import org.apache.wicket.request.mapper.parameter.PageParameters;

public class ParameterLink extends StatelessLink<Void>
{
    private static final long serialVersionUID = 2017427030134271901L;

    private final Class<? extends Page> page;
    private final String parameter;
    private final Object value;

    public ParameterLink(String id, String parameter, Object value)
    {
        this(id, parameter, value, null);
    }

    public ParameterLink(String id, String parameter, Object value, Class<? extends Page> page)
    {
        super(id);

        this.page = page;
        this.parameter = parameter;
        this.value = value;
    }

    @Override
    public void onClick()
    {
        setResponsePage(getPageClass(), getParameters());
    }

    @Override
    protected CharSequence getURL()
    {
        return urlFor(getPageClass(), getParameters());
    }

    private Class<? extends Page> getPageClass()
    {
        return page != null ? page : getPage().getPageClass();
    }

    private PageParameters getParameters()
    {
        PageParameters sortParameters = new PageParameters();
        sortParameters.mergeWith(getPage().getPageParameters());
        sortParameters.set(parameter, value);
        return sortParameters;
    }
}
