package prodlist;

import dao.productDao;
import model.Product;
import preference.config;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Fisher
 * @Date: 2019/9/30 4:08 下午
 */

@WebServlet("/searchProd")
public class searchProduct extends HttpServlet {

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
        String name = req.getParameter("name");
        String category = req.getParameter("category");
        Double minPrice = Double.valueOf(req.getParameter("minprice"));
        Double maxPrice = Double.valueOf(req.getParameter("maxprice"));
        if (db.search(name,category,minPrice,maxPrice)) {
            ResultSet res = db.getResult();
            List<Product> list = new ArrayList<>();

            try {
                while (res.next()) {
                    Product product = new Product();
                    product.setId(res.getString(1));
                    product.setName(res.getString(2));
                    product.setPrice(res.getDouble(3));
                    product.setCategory(res.getString(4));
                    product.setPnum(res.getInt(5));
                    product.setImgurl(res.getString(6));
                    product.setDescription(res.getString(7));
                    list.add(product);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            req.setCharacterEncoding(config.ENCODE);
            req.setAttribute("productList", list);
            req.getRequestDispatcher("/prodlist/prod_list.jsp").forward(req,resp);
        } else {
            PrintWriter writer = resp.getWriter();
            writer.write("<h1>查询失败</h1>");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req,resp);
    }
}
