package controller;

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

@WebServlet("/EmployedController")
public class EmployedController extends HttpServlet {

    private EmployedDao employedDao = new EmployedDao();
    private final String pgList = "/views/employees/listEmployees.jsp";
    private final String formEmployee = "/views/employees/formEmployee.jsp";
    private final String pgDetails = "/views/employees/detailsEmployee.jsp";

    private void create(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        request.setAttribute("employee", new Employed());
        request.getRequestDispatcher(formEmployee).forward(request, response);

    }

    private void save(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        Employed employee = Employed.builder()
                .firstName(request.getParameter("firstName"))
                .lastName(request.getParameter("lastName"))
                .entryDate(Date.valueOf(request.getParameter("entryDate")))
                .salary(Double.parseDouble(request.getParameter("salary")))
                .build();
        int result = employedDao.save(employee);

        if (result > 0) {
            request.getSession().setAttribute("success", "Employee created");
            response.sendRedirect(request.getContextPath() + "/EmployedController?action=list");
        } else {
            request.getSession().setAttribute("error", "Employee not created");
            request.setAttribute("employee", employee);
            request.getRequestDispatcher(formEmployee).forward(request, response);
        }
    }

    private void list(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        response.setContentType("text/html;charset=UTF-8");

        request.setAttribute("employees", employedDao.findAll());
        request.getRequestDispatcher(pgList).forward(request, response);

    }

    private void details(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Employed employed = employedDao.findById(id);
        if (employed != null) {
            request.setAttribute("employee", employed);
            request.getRequestDispatcher(pgDetails).forward(request, response);
        }else{
            request.getSession().setAttribute("error", "Employee with id " + id + " not found");
            response.sendRedirect(request.getContextPath() + "/EmployedController?action=list");
        }
    }

    private void search(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String search = request.getParameter("searchByName");
        request.setAttribute("employees", employedDao.searchByFirstNameAndLastName(search));
        request.setAttribute("searchQuery", search);  // Agregar el valor de búsqueda
        if (search.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/EmployedController?action=list");
        }else{
            request.getRequestDispatcher(pgList).forward(request, response);
        }
    }

    private void edit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Employed employed = employedDao.findById(id);
        if (employed != null) {
            request.setAttribute("employee", employed);
            request.getRequestDispatcher(formEmployee).forward(request, response);
        }else{
            request.getSession().setAttribute("error", "Employee with id " + id + " not found");
            response.sendRedirect(request.getContextPath() + "/EmployedController?action=list");
        }
    }

    private void update(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Employed employed = Employed.builder()
                .id(Integer.parseInt(request.getParameter("id")))
                .firstName(request.getParameter("firstName"))
                .lastName(request.getParameter("lastName"))
                .entryDate(Date.valueOf(request.getParameter("entryDate")))
                .salary(Double.parseDouble(request.getParameter("salary")))
                .build();
        int result = employedDao.update(employed);
        if (result > 0) {
            request.getSession().setAttribute("success", "Employee with id " + employed.getId() + " updated");
        } else {
            request.getSession().setAttribute("error", "Employee with id " + employed.getId() + " not found");
        }
        response.sendRedirect(request.getContextPath() + "/EmployedController?action=list");
    }

    private void delete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        int result = employedDao.deleteById(id);
        if (result > 0) {
            request.getSession().setAttribute("success", "Employee with id " + id + " deleted");
        } else {
            request.getSession().setAttribute("error", "Employee with id " + id + " not found");
        }
        response.sendRedirect(request.getContextPath() + "/EmployedController?action=list");
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        response.setContentType("text/html;charset=UTF-8");

        String action = request.getParameter("action");
        switch (action) {
            case "create":
                create(request, response);
                break;
            case "save":
                save(request, response);
                break;
            case "list":
                list(request, response);
                break;
            case "details":
                details(request, response);
                break;
            case "search":
                search(request, response);
                break;
            case "edit":
                edit(request, response);
                break;
            case "update":
                update(request, response);
                break;
            case "delete":
                delete(request, response);
                break;
            default:
                throw new AssertionError();
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}