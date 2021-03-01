package controllers.employees;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Employee;

/**
 * Servlet implementation class EmployeesNewServlet
 */
@WebServlet("/employees/new")
public class EmployeesNewServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public EmployeesNewServlet() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //CSRF対策 トークン設定
        request.setAttribute("_token", request.getSession().getId());

        //更新時等に内容を残すためにフォームに現在のタスク内容を表示するように設定
        //newのJSP表示時に入っていないとエラーになるので初期値として設定
        request.setAttribute("employee", new Employee());

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/employees/new.jsp");
        rd.forward(request, response);
    }

}
