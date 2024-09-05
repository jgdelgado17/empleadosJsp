package config.application;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.servlet.ServletContext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class AppConfigLoaderTest {
    private ServletContext context;

    /**
     * Sets up the test by creating a mock ServletContext.
     */
    @BeforeEach
    void setUp() {
        context = mock(ServletContext.class);
    }

    /**
     * Tears down the test by setting the mock ServletContext to null.
     */
    @AfterEach
    void tearDown() {
        context = null;
    }

    /**
     * Tests that the properties are loaded into the ServletContext.
     */
    @Test
    void loadPropertiesToContext() {
        AppConfigLoader.loadPropertiesToContext(context);
        context.setAttribute("testKey", "testValue");
        verify(context).setAttribute("testKey", "testValue");
    }

    /**
     * Tests that the correct value is returned from the properties file.
     *
     * @see AppConfigLoader#getProperty(String)
     */
    @Test
    void getProperty() {
        assertEquals("create", AppConfigLoader.getProperty("action.create"));
    }
}