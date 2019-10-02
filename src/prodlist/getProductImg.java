package prodlist;

import dao.productDao;
import preference.config;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: Fisher
 * @Date: 2019/9/30 4:08 下午
 */

@WebServlet("/getProdImg")
public class getProductImg extends HttpServlet {

    private productDao db = new productDao();

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
        String imgurl = req.getParameter("imgurl");
        req.getRequestDispatcher(imgurl).forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req,resp);
    }
}
