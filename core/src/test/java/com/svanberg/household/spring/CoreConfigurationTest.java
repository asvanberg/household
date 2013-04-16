package com.svanberg.household.spring;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;
import javax.persistence.EntityManagerFactory;

import static org.junit.Assert.assertNotNull;

/**
 * @author Andreas Svanberg (andreass) <andreas.svanberg@mensa.se>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = CoreConfiguration.class)
public class CoreConfigurationTest {

    @Inject
    EntityManagerFactory entityManagerFactory;

    @Test
    public void testConfiguration() throws Exception {
        assertNotNull("Failed to inject entity manager factory, configuration faulty", entityManagerFactory);
    }
}
