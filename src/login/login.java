package login;

import dao.CRUD;
import model.User;
import preference.config;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @Author: Fisher
 * @Date: 2019-09-23 20:02
 */

@WebServlet("/login")
public class login extends HttpServlet {

    private CRUD db = new CRUD();

    @Override
    public void init() throws ServletException {
        super.init();
        db.init();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String req_method = req.getMethod();
        String req_url = req.getRequestURI();

        resp.setContentType(config.CONTENT_TYPE + "charset=" + config.ENCODE);
        PrintWriter writer = resp.getWriter();
        writer.write("<h1>Method: " + req_method + "</h1>");
        writer.write("<h1>URL: " +req_url + "</h1>");
        writer.write("<h1>访问login接口，来自get请求</h1>");

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType(config.CONTENT_TYPE + "charset=" + config.ENCODE);
        PrintWriter writer = resp.getWriter();

        req.setCharacterEncoding(config.ENCODE);
        String username = req.getParameter("username");
        String psw = req.getParameter("password");
        String checked = req.getParameter("remname");
        User user = new User(username,psw,null,null,null);

        // 记住用户名
        if ("on".equals(checked)) {
            Cookie usernameCookie = new Cookie("username", username);
            usernameCookie.setMaxAge(10000);
            resp.addCookie(usernameCookie);
        }

        if (db.select(user.getUsername())) {
            ResultSet res = db.getResult();
            String auth = null;
            String nickname = null;
            String email = null;
            try {
                res.next();
                auth = res.getString(3);
                nickname = res.getString(4);
                email = res.getString(5);
            } catch (SQLException e) {
                writer.write("<h1>发生错误</h1>");
                writer.write(e.getMessage());
                e.printStackTrace();
            }
            if (psw.equals(auth)) {
                user.setNickname(nickname);
                user.setEmail(email);
                req.getSession().setAttribute("user", user);

                writer.write("<h1>登录成功，3s后跳转到主页</h1>");
                resp.setHeader("refresh","3;url=/LearnWeb/index/index.jsp");
            } else {
                writer.write("<h1>登录失败，密码错误</h1>");
                resp.setHeader("refresh","3;url=/LearnWeb/login/login.jsp");
            }

        }
    }
}
