package com.svanberg.household.web.spring;

import com.svanberg.household.spring.ContextConfiguration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author Andreas Svanberg (andreass) <andreas.svanberg@mensa.se>
 */
@Configuration
@Import(ContextConfiguration.class)
public class WebConfiguration {
}
