package regist;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: Fisher
 * @Date: 2019-09-19 11:21
 */

@WebServlet("/getVerifyCode")
public class getVerifyCode extends HttpServlet {

    @Override
    public void init() throws ServletException {
        super.init();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        VerifyCode verifyCode = new VerifyCode();
        resp.setContentType("img/jpg");
        ServletOutputStream outputStream = resp.getOutputStream();
        verifyCode.drawImage(outputStream);
        req.getSession().setAttribute("verifyCode", verifyCode.getCode());
        outputStream.flush();
    }
}
