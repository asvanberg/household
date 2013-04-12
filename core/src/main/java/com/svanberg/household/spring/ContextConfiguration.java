package com.svanberg.household.spring;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author Andreas Svanberg (andreass) <andreas.svanberg@mensa.se>
 */
@Configuration
@ComponentScan("com.svanberg.household")
@Import(DatabaseConfiguration.class)
public class ContextConfiguration {
}
