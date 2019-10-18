package order;
import dao.orderDao;
import model.OrderInfo;
import model.User;
import preference.config;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @Author: Fisher
 * @Date: 2019/10/18 9:57 上午
 */

@WebServlet("/orderList")
public class orderList extends HttpServlet {

    private orderDao db = new orderDao();

    @Override
    public void init() throws ServletException {
        super.init();
        db.init();
    }

    @Override
    public void destroy() {
        super.destroy();
        db.release();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 解决请求乱码
        req.setCharacterEncoding(config.ENCODE);
        resp.setContentType(config.CONTENT_TYPE + "charset=" + config.ENCODE);

        User user = (User) req.getSession().getAttribute("user");
        if (user == null) {
            resp.getWriter().write("<a href='/LearnWeb/login/login.jsp'>请先登录</a>");
        } else {
            List<OrderInfo> orderInfoList = db.findOrderInfoByUserId(user.getId());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }


}
