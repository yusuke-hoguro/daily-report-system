package controllers.employees;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Employee;
import models.validators.EmployeeValidator;
import utils.DBUtil;
import utils.EncryptUtil;

/**
 * Servlet implementation class EmployeesCreateServlet
 */
@WebServlet("/employees/create")
public class EmployeesCreateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public EmployeesCreateServlet() {
        super();
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //CSRF対策 トークンを取得
        String _token = (String)request.getParameter("_token");

        //トークンチェックOKなら処理を実行
        if(_token != null && _token.equals(request.getSession().getId())){

            //DBと通信するためのインスタンス作成
            EntityManager em = DBUtil.createEntityManager();

            //employeesテーブルのインスタンス作成
            Employee e = new Employee();

            //送信されてきたデータをインスタンスに設定する
            e.setCode(request.getParameter("code"));
            e.setName(request.getParameter("name"));
            //パスワードはSHA256 でハッシュ化
            e.setPassword(
                    EncryptUtil.getPasswordEncrypt(
                            request.getParameter("password"),
                            (String)this.getServletContext().getAttribute("pepper")
                            )
                    );
            e.setAdmin_flag(Integer.parseInt(request.getParameter("admin_flag")));

            Timestamp currentTime = new Timestamp(System.currentTimeMillis());
            e.setCreated_at(currentTime);
            e.setUpdated_at(currentTime);
            e.setDelete_flag(0);

            //入力項目のバリデーション(新規登録:社員番号重複チェックON パスワードチェックON）
            List<String> errors = EmployeeValidator.validate(e, true, true);

            //バリデーションでNGがあった場合はnew（入力されてあった値を再表示）に戻る
            if(errors.size() > 0){

                em.close();

                request.setAttribute("_token", request.getSession().getId());
                request.setAttribute("employee", e);
                request.setAttribute("errors", errors);

                RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/employees/new.jsp");
                rd.forward(request, response);

            }else{

                //データベースに書き込みを行う
                em.getTransaction().begin();
                em.persist(e);
                em.getTransaction().commit();
                request.getSession().setAttribute("flush", "登録が完了しました。");
                em.close();

                //一覧表示(index)にリダイレクト
                response.sendRedirect(request.getContextPath() + "/employees/index");
            }

        }
    }

}
