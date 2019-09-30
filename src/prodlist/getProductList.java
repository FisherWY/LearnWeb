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
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Fisher
 * @Date: 2019/9/29 9:14 上午
 */

@WebServlet("/getProdlist")
public class getProductList extends HttpServlet {

    productDao db = new productDao();

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
        String errMsg = "";
        if (db.select()) {
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
                errMsg = e.getMessage();
            }

            req.setCharacterEncoding(config.ENCODE);
            req.setAttribute("productList", list);
            req.getRequestDispatcher("/prodlist/prod_list.jsp").forward(req,resp);

        } else {
            resp.setContentType(config.CONTENT_TYPE + "charset=" + config.ENCODE);
            PrintWriter writer = resp.getWriter();

            writer.write("<h1>数据库发生错误</h1>");
            writer.write(errMsg);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String req_method = req.getMethod();
        String req_url = req.getRequestURI();

        resp.setContentType(config.CONTENT_TYPE + "charset=" + config.ENCODE);
        PrintWriter writer = resp.getWriter();
        writer.write("<h1>Method: " + req_method + "</h1>");
        writer.write("<h1>URL: " +req_url + "</h1>");
        writer.write("<h1>访问get product list接口，来自post请求</h1>");
    }
}
