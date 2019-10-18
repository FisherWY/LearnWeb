package order;
import dao.orderDao;
import model.Order;
import model.OrderItem;
import model.Product;
import model.User;
import preference.config;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @Author: Fisher
 * @Date: 2019/10/18 9:00 上午
 */

@WebServlet("/addOrder")
public class addOrder extends HttpServlet {

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
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 设置请求和返回的编码
        req.setCharacterEncoding(config.ENCODE);
        resp.setContentType(config.CONTENT_TYPE + "charset=" + config.ENCODE);

        // 获取用户信息
        User user = (User) req.getSession().getAttribute("user");
        if (user == null) {
            resp.getWriter().write("<a href='/LearnWeb/login/login.jsp>请先登录!</a>'");
        } else {
            // 添加新订单
            Order order = new Order();
            order.setId(UUID.randomUUID().toString());
            order.setPaystate(0);
            order.setReceiverinfo(req.getParameter("receiverinfo"));
            order.setUser_id(user.getId());

            // 添加新订单列表
            List<OrderItem> list = new ArrayList<>();
            double totalMoney = 0;
            Map<Product, Integer> map = (Map<Product, Integer>) req.getSession().getAttribute("cartmap");
            for (Map.Entry<Product, Integer> entry : map.entrySet()) {
                double price = entry.getKey().getPrice();
                int buyNum = entry.getValue();
                totalMoney += price * buyNum;

                OrderItem item = new OrderItem();
                item.setOrder_id(order.getId());
                item.setProduct_id(entry.getKey().getId());
                item.setBuynum(buyNum);
                list.add(item);
            }
            order.setMoney(totalMoney);
            try {
                db.addOrder(order, list);
            } catch (Exception e) {
                resp.getWriter().write("<h1>" + e.getMessage() + "</h1>");
                return;
            }

            map.clear();
            resp.sendRedirect("/LearnWeb/orderList");
        }
    }
}
