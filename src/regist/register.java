package regist;

import dao.CRUD;
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

    private String username;
    private String psw;
    private String psw1;
    private String nickname;
    private String email;
    private String verifycode;
    private String verifycode1;
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
        username = req.getParameter("username");
        psw = req.getParameter("password");
        psw1 = req.getParameter("password2");
        nickname = req.getParameter("nickname");
        email = req.getParameter("email");
        verifycode = req.getParameter("valistr").toLowerCase();
        verifycode1 = String.valueOf(req.getSession().getAttribute("verifyCode")).toLowerCase();

        // 回显页面
        resp.setCharacterEncoding(config.ENCODE);
        resp.setContentType(config.CONTENT_TYPE);
        PrintWriter writer = resp.getWriter();

        writer.write("<h1>注册信息如下</h1>");

        // 判断注册信息
        if (username==null || "".equals(username)) {
            writer.write("<h5>用户名不能为空</h5>");
            valid = false;
        } else {
            writer.write("<h5>用户名：" + username + "</h5>");
        }
        if (psw==null || psw1==null || "".equals(psw) || "".equals(psw1)) {
            writer.write("<h5>密码不能为空</h5>");
            valid = false;
        } else if (!psw.equals(psw1)) {
            writer.write("<h5>两次输入的密码不同</h5>");
        }
        if (nickname==null || "".equals(nickname)) {
            writer.write("<h5>Nickname不能为空</h5>");
            valid = false;
        } else {
            writer.write("<h5>你填写的Nickname：" + nickname);
        }
        if (email==null || "".equals(email)) {
            writer.write("<h5>email不能为空</h5>");
            valid = false;
        } else {
            writer.write("<h5>Email：" + email + "</h5>");
        }
        if (verifycode==null || "".equals(verifycode)) {
            writer.write("<h5>验证码不能为空</h5>");
            valid = false;
        } else if (!verifycode1.equals(verifycode)) {
            writer.write("<h5>验证码验证失败</h5>");
            valid = false;
        }

        if (valid) {
            if (db.select(username)) {
                ResultSet res = db.getResult();
                try {
                    if (res.next()) {
                        writer.write("<h1>该用户名已被注册，请重新输入用户名</h1>");
                        resp.setHeader("refresh", "3;url=/LearnWeb/regist/regist.jsp");
                    } else {
                        db.add(username,psw,nickname,email);
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
