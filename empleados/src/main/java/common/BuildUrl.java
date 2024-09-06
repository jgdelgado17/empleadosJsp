package common;

import config.application.AppConfigLoader;

import javax.servlet.http.HttpServletRequest;

public class BuildUrl {

    private static final String ACTION = AppConfigLoader.getProperty("request.param.action");
    private static final String PAGE = AppConfigLoader.getProperty("request.param.page");
    private static final String ORDER_BY = AppConfigLoader.getProperty("request.param.sortBy");

    public static String employees(HttpServletRequest request, String action, String sortBy, String orderDirection, int page) {
        return request.getContextPath() + "/EmployedController?"
                + ACTION + "=" + action
                + "&" + ORDER_BY + "=" + sortBy
                + "&orderDirection=" + orderDirection
                + "&" + PAGE + "=" + page;
    }
}
