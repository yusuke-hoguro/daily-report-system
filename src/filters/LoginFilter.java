package filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import models.Employee;

/**
 * Servlet Filter implementation class LoginFilter
 */
@WebFilter("/*")
public class LoginFilter implements Filter {

    /**
     * Default constructor.
     */
    public LoginFilter() {
    }

    /**
     * @see Filter#destroy()
     */
    public void destroy() {
    }

    /**
     * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
     */
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        //パスを取得 http://(サーバ名:ポート番号)/(コンテキストパス)/(Servletパス)
        String context_path = ((HttpServletRequest)request).getContextPath(); //WEBアプリケーションを識別するためのパス
        String servlet_path = ((HttpServletRequest)request).getServletPath(); //サーブレットのパス

        //cssフォルダ以外での処理（cssフォルダ内はログイン認証処理から除外する）
        if(!servlet_path.matches("/css.*")){

            //リクエストからセッションを取得
            HttpSession session = ((HttpServletRequest)request).getSession();

            //セッションスコープに保存されたログインユーザー情報を取得
            Employee e = (Employee)session.getAttribute("login_employee");

            //ログイン画面以外の場合の処理
            if(!servlet_path.equals("/login")){

                //ログアウトしている状態であればログイン画面にリダイレクト
                if(e == null){
                    ((HttpServletResponse)response).sendRedirect(context_path + "/login");
                    //リダイレクトしたので終了させる
                    return;
                }

                //従業員管理の機能は管理者のみが閲覧できるようにする
                if(servlet_path.matches("/employees.*") && e.getAdmin_flag() == 0){

                    //TopPageへリダイレクト
                    ((HttpServletResponse)response).sendRedirect(context_path + "/");
                    //リダイレクトしたので終了させる
                    return;
                }
            }
            //ログイン画面の場合の処理
            else{

                //ログインしているのにログイン画面を表示しようとした場合
                //トップページへリダイレクト
                if(e != null){
                    //TopPageへリダイレクト
                    ((HttpServletResponse)response).sendRedirect(context_path + "/");
                    //リダイレクトしたので終了させる
                    return;
                }
            }
        }

        chain.doFilter(request, response);
    }

    /**
     * @see Filter#init(FilterConfig)
     */
    public void init(FilterConfig fConfig) throws ServletException {
    }

}
