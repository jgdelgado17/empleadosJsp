package controller;

import common.BuildUrl;
import config.application.AppConfigLoader;
import dao.EmployedDao;
import model.Employed;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Objects;

@WebServlet("/EmployedController")
public class EmployedController extends HttpServlet {

    private final EmployedDao employedDao = new EmployedDao();
    private final String PAGE_FORM = AppConfigLoader.getProperty("view.form.employees");
    private final String PAGE_LIST = AppConfigLoader.getProperty("view.list.employees");
    private final String PAGE_DETAILS = AppConfigLoader.getProperty("view.details.employees");
    private final String PAGE = AppConfigLoader.getProperty("request.param.page");
    private final String LIST = AppConfigLoader.getProperty("action.list");
    private final int PAGE_SIZE = Integer.parseInt(Objects.requireNonNull(AppConfigLoader.getProperty("view.list.pageSize")));
    private final String ORDER_BY = AppConfigLoader.getProperty("request.param.orderBy");
    private String fieldSort = AppConfigLoader.getProperty("request.param.sort.id");
    private String orderDirection = AppConfigLoader.getProperty("action.sort.asc");
    private int currentPage = 1;

    /**
     * Shows the form for creating a new employee
     *
     * @param request  the request
     * @param response the response
     * @throws IOException      if there's an error with the input/output
     * @throws ServletException if there's an error with the servlet
     */
    private void createEmployee(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        request.setAttribute("employee", new Employed());
        request.getRequestDispatcher(PAGE_FORM).forward(request, response);

    }

    /**
     * Saves a new employee in the database
     *
     * @param request  the request
     * @param response the response
     * @throws IOException      if there's an error with the input/output
     * @throws ServletException if there's an error with the servlet
     *
     * <p>
     * This method creates a new <code>Employed</code> object from the request
     * parameters, saves it in the database using the <code>employedDao</code>
     * and redirects to the list page if the save is successful or forwards back
     * to the form page if there's an error.
     */
    private void saveEmployee(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        try {
            Employed employee = Employed.builder()
                    .firstName(request.getParameter("firstName"))
                    .lastName(request.getParameter("lastName"))
                    .entryDate(Date.valueOf(request.getParameter("entryDate")))
                    .salary(Double.parseDouble(request.getParameter("salary")))
                    .build();
            int result = employedDao.saveEmployed(employee);

            if (result > 0) {
                request.getSession().setAttribute("success", "Employee created");
                response.sendRedirect(BuildUrl.employees(request, LIST, fieldSort, orderDirection, currentPage));
            } else {
                request.getSession().setAttribute("error", "Employee not created");
                request.setAttribute("employee", employee);
                request.getRequestDispatcher(PAGE_FORM).forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.getSession().setAttribute("error", "Error creating employee");
            request.getRequestDispatcher(PAGE_FORM).forward(request, response);
        }
    }

    /**
     * Shows the list of all employees in the database, paginated by page.
     * <p>
     * This method retrieves the list of employees from the database using the
     * <code>employedDao</code>, sets the request attributes and forwards the
     * request to the list page.
     *
     * @param request  the request
     * @param response the response
     * @throws IOException      if there's an error with the input/output
     * @throws ServletException if there's an error with the servlet
     */
    private void listAllEmployees(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        String pageStr = request.getParameter(PAGE);
        currentPage = (pageStr != null) ? Integer.parseInt(pageStr) : 1;
        String fieldOrder = request.getParameter(ORDER_BY);
        fieldSort = (fieldOrder != null) ? fieldOrder : fieldSort;
        String order = request.getParameter("orderDirection");
        orderDirection = (order != null) ? order : orderDirection;

        ArrayList<Employed> employees = employedDao.findAllEmployees(currentPage, PAGE_SIZE, fieldSort, orderDirection);

        int totalEmployees = employedDao.countEmployees();
        int totalPages = (int) Math.ceil(totalEmployees / (double) PAGE_SIZE);

        request.setAttribute("orderDirection", orderDirection);
        request.setAttribute("fieldSort", fieldSort);
        request.setAttribute("employees", employees);
        request.setAttribute("currentPage", currentPage);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("totalEmployees", totalEmployees);

        request.getRequestDispatcher(PAGE_LIST).forward(request, response);
    }

    /**
     * Searches employees in the database by first name or last name.
     *
     * @param request  the request
     * @param response the response
     * @throws IOException      if there's an error with the input/output
     * @throws ServletException if there's an error with the servlet
     *
     * <p>
     * This method retrieves the list of employees from the database using the
     * <code>employedDao</code>, sets the request attributes and forwards the
     * request to the list page if the search query is not empty. If the search
     * query is empty, it redirects to the list page.
     */
    private void searchEmployeeByFirstNameOrLastName(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String search = request.getParameter("searchByName");
        request.setAttribute("employees", employedDao.searchEmployedByFirstNameOrLastName(search));
        request.setAttribute("searchQuery", search);
        request.setAttribute("fieldSort", fieldSort);
        request.setAttribute("currentPage", currentPage);
        request.setAttribute("orderDirection", orderDirection);
        if (search.isEmpty()) {
            response.sendRedirect(BuildUrl.employees(request, LIST, fieldSort, orderDirection, currentPage));
        }else{
            request.getRequestDispatcher(PAGE_LIST).forward(request, response);
        }
    }

    /**
     * Shows the details of an employee.
     *
     * @param request  the request
     * @param response the response
     * @throws IOException      if there's an error with the input/output
     * @throws ServletException if there's an error with the servlet
     *
     * <p>
     * This method finds an employee in the database by id using the
     * <code>employedDao</code>, sets the request attributes and forwards the
     * request to the details page if the employee is found. If the employee is
     * not found, it sets an error message in the session and redirects to the
     * list page.
     */
    private void detailsEmployee(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Employed employed = employedDao.findEmployedById(id);
        if (employed != null) {
            request.setAttribute("employee", employed);
            request.getRequestDispatcher(PAGE_DETAILS).forward(request, response);
        }else{
            request.getSession().setAttribute("error", "Employee with id " + id + " not found");
            response.sendRedirect(BuildUrl.employees(request, LIST, fieldSort, orderDirection, currentPage));
        }
    }

    /**
     * Shows the form for editing an existing employee.
     *
     * @param request  the request
     * @param response the response
     * @throws IOException      if there's an error with the input/output
     * @throws ServletException if there's an error with the servlet
     *
     * <p>
     * This method finds an employee in the database by id using the
     * <code>employedDao</code>, sets the request attributes and forwards the
     * request to the form page if the employee is found. If the employee is
     * not found, it sets an error message in the session and redirects to the
     * list page.
     */
    private void editEmployee(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Employed employed = employedDao.findEmployedById(id);
        if (employed != null) {
            request.setAttribute("employee", employed);
            request.getRequestDispatcher(PAGE_FORM).forward(request, response);
        }else{
            request.getSession().setAttribute("error", "Employee with id " + id + " not found");
            response.sendRedirect(BuildUrl.employees(request, LIST, fieldSort, orderDirection, currentPage));
        }
    }

    /**
     * Updates an employee in the database.
     *
     * @param request  the request
     * @param response the response
     * @throws IOException if there's an error with the input/output
     *
     * <p>
     * This method creates a new <code>Employed</code> object from the request
     * parameters, saves it in the database using the <code>employedDao</code>
     * and redirects to the list page if the update is successful or forwards back
     * to the form page if there's an error.
     */
    private void updateEmployee(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Employed employed = Employed.builder()
                .id(Integer.parseInt(request.getParameter("id")))
                .firstName(request.getParameter("firstName"))
                .lastName(request.getParameter("lastName"))
                .entryDate(Date.valueOf(request.getParameter("entryDate")))
                .salary(Double.parseDouble(request.getParameter("salary")))
                .build();
        int result = employedDao.updateEmployed(employed);
        if (result > 0) {
            request.getSession().setAttribute("success", "Employee with id " + employed.getId() + " updated");
            response.sendRedirect(BuildUrl.employees(request, LIST, fieldSort, orderDirection, currentPage));
        } else {
            request.getSession().setAttribute("error", "Employee with id " + employed.getId() + " was not updated");
            request.setAttribute("employee", employed);
            request.getRequestDispatcher(PAGE_FORM).forward(request, response);
        }
    }

    /**
     * Deletes an employee from the database.
     *
     * @param request  the request
     * @param response the response
     * @throws IOException if there's an error with the input/output
     *
     * <p>
     * This method deletes an employee from the database using the
     * <code>employedDao</code> and redirects to the list page if to delete
     * is successful or forwards back to the list page if there's an error.
     */
    private void deleteEmployee(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        int result = employedDao.deleteEmployedById(id);
        if (result > 0) {
            request.getSession().setAttribute("success", "Employee with id " + id + " deleted");
        } else {
            request.getSession().setAttribute("error", "Employee with id " + id + " not found");
        }
        response.sendRedirect(BuildUrl.employees(request, LIST, fieldSort, orderDirection, currentPage));
    }

    /**
     * Deletes multiple employees from the database.
     *
     * @param request  the request
     * @param response the response
     * @throws IOException if there's an error with the input/output
     *
     * <p>
     * This method deletes multiple employees from the database using the
     * <code>employedDao</code> and redirects to the list page if to delete
     * is successful or forwards back to the list page if there's an error.
     * If the request parameter "ids" is empty or null, it sets an error
     * message in the session and redirects to the list page.
     */
    private void deleteMultipleEmployees(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String ids = request.getParameter("ids");
        if (ids == null || ids.isEmpty()) {
            request.getSession().setAttribute("error", "Please select at least one employee.");
            response.sendRedirect(BuildUrl.employees(request, LIST, fieldSort, orderDirection, currentPage));
            return;
        }
        int result = employedDao.deleteMultipleEmployees(ids);
        if (result > 0) {
            request.getSession().setAttribute("success", "Employees deleted");
        } else {
            request.getSession().setAttribute("error", "Employees not found");
        }
        response.sendRedirect(BuildUrl.employees(request, LIST, fieldSort, orderDirection, currentPage));
    }

    /**
     * Processes the request and forwards or redirects to the appropriate page.
     * <p>
     * This method checks the value of the {@code action} parameter and calls the
     * appropriate method to handle the request. If the request is not recognized,
     * it throws an {@link AssertionError}.
     *
     * @param request  the request
     * @param response the response
     * @throws IOException      if there's an error with the input/output
     * @throws ServletException if there's an error with the servlet
     */
    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        response.setContentType("text/html;charset=UTF-8");

        String action = request.getParameter("action");
        switch (action) {
            case "create":
                createEmployee(request, response);
                break;
            case "save":
                saveEmployee(request, response);
                break;
            case "list":
                listAllEmployees(request, response);
                break;
            case "details":
                detailsEmployee(request, response);
                break;
            case "search":
                searchEmployeeByFirstNameOrLastName(request, response);
                break;
            case "edit":
                editEmployee(request, response);
                break;
            case "update":
                updateEmployee(request, response);
                break;
            case "delete":
                deleteEmployee(request, response);
                break;
            case "deleteMultiple":
                deleteMultipleEmployees(request, response);
                break;
            default:
                throw new AssertionError();
        }

    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request  the request
     * @param response the response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException       if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request  the request
     * @param response the response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException       if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>PUT</code> method.
     *
     * @param request  the request
     * @param response the response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException       if an I/O error occurs
     */
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>DELETE</code> method.
     *
     * @param request  the request
     * @param response the response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException       if an I/O error occurs
     */
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}