package regist;

import dao.CRUD;
import model.RegistUser;
import preference.config;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @Author: Fisher
 * @Date: 2019-09-19 09:37
 */

@WebServlet("/register")
public class register extends HttpServlet {

    private RegistUser user = new RegistUser();
    private boolean valid = true;

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

        resp.setContentType("text/html");
        PrintWriter writer = resp.getWriter();
        writer.write("<h1>Method: " + req_method + "</h1>");
        writer.write("<h1>Url: " + req_url + "</h1>");
        writer.write("<img src='/LearnWeb/getVerifyCode' width='120' height='30'>");

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // 重复提交监测
        String token1 = String.valueOf(req.getSession().getAttribute("token"));
        String token2 = req.getParameter("token");
        if (token1==null || token2==null || !token1.equals(token2)) {
            throw new RuntimeException("重复提交错误");
        } else {
            req.getSession().removeAttribute("token");
        }

        // 获取Post的参数
        req.setCharacterEncoding("UTF-8");
        user.setUsername(req.getParameter("username"));
        user.setPsw(req.getParameter("password"));
        user.setPsw1(req.getParameter("password2"));
        user.setNickname(req.getParameter("nickname"));
        user.setEmail(req.getParameter("email"));
        user.setVerifycode(req.getParameter("valistr").toLowerCase());
        user.setVerifycode1(String.valueOf(req.getSession().getAttribute("verifyCode")).toLowerCase());

        // 回显页面
        resp.setCharacterEncoding(config.ENCODE);
        resp.setContentType(config.CONTENT_TYPE);
        PrintWriter writer = resp.getWriter();

        writer.write("<h1>注册信息如下</h1>");

        // 判断注册信息
        if (user.getUsername()==null || "".equals(user.getUsername())) {
            writer.write("<h5>用户名不能为空</h5>");
            valid = false;
        } else {
            writer.write("<h5>用户名：" + user.getUsername() + "</h5>");
        }
        if (user.getPsw()==null || user.getPsw1()==null || "".equals(user.getPsw()) || "".equals(user.getPsw1())) {
            writer.write("<h5>密码不能为空</h5>");
            valid = false;
        } else if (!user.getPsw().equals(user.getPsw1())) {
            writer.write("<h5>两次输入的密码不同</h5>");
        }
        if (user.getNickname()==null || "".equals(user.getNickname())) {
            writer.write("<h5>Nickname不能为空</h5>");
            valid = false;
        } else {
            writer.write("<h5>你填写的Nickname：" + user.getNickname());
        }
        if (user.getEmail()==null || "".equals(user.getEmail())) {
            writer.write("<h5>email不能为空</h5>");
            valid = false;
        } else {
            writer.write("<h5>Email：" + user.getEmail() + "</h5>");
        }
        if (user.getVerifycode()==null || "".equals(user.getVerifycode())) {
            writer.write("<h5>验证码不能为空</h5>");
            valid = false;
        } else if (!user.getVerifycode1().equals(user.getVerifycode())) {
            writer.write("<h5>验证码验证失败</h5>");
            valid = false;
        }

        if (valid) {
            if (db.select(user.getUsername())) {
                ResultSet res = db.getResult();
                try {
                    if (res.next()) {
                        writer.write("<h1>该用户名已被注册，请重新输入用户名</h1>");
                        resp.setHeader("refresh", "3;url=/LearnWeb/regist/regist.jsp");
                    } else {
                        db.add(user);
                        resp.setHeader("refresh", "3;url=/LearnWeb/login/login.jsp");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }


    }

    @Override
    public void destroy() {
        super.destroy();
        db.release();
    }
}
