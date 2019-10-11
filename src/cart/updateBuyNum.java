package cart;

import dao.productDao;
import model.Product;
import preference.config;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.ResultSet;
import java.util.Map;

/**
 * @Author: Fisher
 * @Date: 2019/10/11 9:34 上午
 */

@WebServlet("/updateBuyNum")
public class updateBuyNum extends HttpServlet {

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
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding(config.ENCODE);
        String pid = req.getParameter("pid");
        Integer buyNum = Integer.parseInt(req.getParameter("buyNum"));
        Map<Product, Integer> cartMap = (Map<Product, Integer>) req.getSession().getAttribute("cartmap");

        if (db.getProductInfo(pid)) {
            try {
                ResultSet res = db.getResult();
                if (res.next()) {
                    Product product = new Product();
                    product.setId(res.getString(1));
                    product.setName(res.getString(2));
                    product.setPrice(res.getDouble(3));
                    product.setCategory(res.getString(4));
                    product.setPnum(res.getInt(5));
                    product.setImgurl(res.getString(6));
                    product.setDescription(res.getString(7));

                    if (buyNum < 0) {
                        cartMap.remove(product);
                    } else {
                        cartMap.put(product, buyNum);
                    }

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
