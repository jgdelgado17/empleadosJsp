package config.application;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

@WebServlet(name = "AppConfigServlet", urlPatterns = {}, loadOnStartup = 1)
public class AppConfigServlet extends HttpServlet {

    /**
     * Initializes the servlet by setting application scope attributes from the
     * <code>application.properties</code> file.
     *
     * @throws ServletException if a servlet-specific error occurs
     */
    @Override
    public void init() throws ServletException {
        super.init();

        AppConfigLoader.loadPropertiesToContext(getServletContext());
    }
}
