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

@WebServlet("/EmployedController")
public class EmployedController extends HttpServlet {

    private EmployedDao employedDao = new EmployedDao();
    private final String pgList = "/views/employees/listEmployees.jsp";
    private final String formEmployee = "/views/employees/formEmployee.jsp";
    private final String pgDetails = "/views/employees/detailsEmployee.jsp";

    protected void create(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        request.setAttribute("employee", new Employed());
        request.getRequestDispatcher(formEmployee).forward(request, response);

    }

    protected void save(HttpServletRequest request, HttpServletResponse response)
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
            response.sendRedirect(request.getContextPath() + "/EmployedController?action=list");
        } else {
            request.setAttribute("employee", employee);
            request.getRequestDispatcher(formEmployee).forward(request, response);
        }
    }

    protected void list(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        response.setContentType("text/html;charset=UTF-8");

        request.setAttribute("employees", employedDao.findAll());
        request.getRequestDispatcher(pgList).forward(request, response);

    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
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
                int idDetails = Integer.parseInt(request.getParameter("id"));
                request.setAttribute("employee", employedDao.findById(idDetails));
                request.getRequestDispatcher(pgDetails).forward(request, response);
                break;
            case "edit":
                int idUpdate = Integer.parseInt(request.getParameter("id"));
                request.setAttribute("employee", employedDao.findById(idUpdate));
                request.getRequestDispatcher(formEmployee).forward(request, response);
                break;
            case "update":
                Employed employed = new Employed(
                        Integer.parseInt(request.getParameter("id")),
                        request.getParameter("firstName"),
                        request.getParameter("lastName"),
                        Date.valueOf(request.getParameter("entryDate")),
                        Double.parseDouble(request.getParameter("salary"))
                );
                employedDao.update(employed);
                response.sendRedirect(request.getContextPath() + "/EmployedController?action=list");
                break;
            case "delete":
                int id = Integer.parseInt(request.getParameter("id"));
                employedDao.deleteById(id);
                response.sendRedirect(request.getContextPath() + "/EmployedController?action=list");
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