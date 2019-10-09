package prodinfo;

import dao.productDao;
import model.Product;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.ResultSet;

/**
 * @Author: Fisher
 * @Date: 2019/10/9 7:17 下午
 */

@WebServlet("/getProdinfo")
public class getProductInfo extends HttpServlet {
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
        String pid = req.getParameter("pid");
        Product product = new Product();
        if (db.getProductInfo(pid)) {
            try {
                ResultSet res = db.getResult();
                if (res.next()) {
                    product.setId(res.getString(1));
                    product.setName(res.getString(2));
                    product.setPrice(res.getDouble(3));
                    product.setCategory(res.getString(4));
                    product.setPnum(res.getInt(5));
                    product.setImgurl(res.getString(6));
                    product.setDescription(res.getString(7));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        req.setAttribute("prod", product);
        req.getRequestDispatcher("/prodinfo/prod_info.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req,resp);
    }
}
