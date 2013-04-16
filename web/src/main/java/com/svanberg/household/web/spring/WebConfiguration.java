package com.svanberg.household.web.spring;

import com.svanberg.household.spring.CoreConfiguration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author Andreas Svanberg (andreass) <andreas.svanberg@mensa.se>
 */
@Configuration
@Import(CoreConfiguration.class)
public class WebConfiguration {
}
