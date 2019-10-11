package dao;

import com.sun.istack.internal.NotNull;
import preference.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @Author: Fisher
 * @Date: 2019/9/29 9:16 上午
 */

public class productDao {

    public String serverURL = config.DB_URL;
    public String database = config.DB_NAME;
    public String serverTimeZone = config.DB_TIMEZONE;

    private String username = config.DB_USERNAME;
    private String password = config.DB_PASSWORD;

    private Connection connection = null;
    private PreparedStatement statement = null;
    private ResultSet result = null;

    public boolean init() {
        try {
            // 加载驱动
            Class.forName(config.DB_DRIVER);
            // 建立连接
            connection = DriverManager.getConnection(serverURL+database+serverTimeZone, username, password);
            // 返回连接情况
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public void release() {
        try {
            if (connection != null) {
                connection.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (result != null) {
                result.close();
            }
            System.gc();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

    }

    public ResultSet getResult() {
        return result;
    }

    public boolean select() {
        try {
            String sql = "select * from products";

            statement = connection.prepareStatement(sql);

            result = statement.executeQuery();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean search(@NotNull String name, @NotNull String category, @NotNull double minPrice, @NotNull double maxPrice) {
        try {
            String sql = "select * from products where name like %?% and category like %?% and price >=? and price <=?";

            statement = connection.prepareStatement(sql);
            statement.setString(1, name);
            statement.setString(2, category);
            statement.setDouble(3, minPrice);
            statement.setDouble(4, maxPrice);

            result = statement.executeQuery();

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean getProductInfo(@NotNull String id) {
        try {
            String sql = "select * from products where id=?";

            statement = connection.prepareStatement(sql);
            statement.setString(1, id);

            result = statement.executeQuery();

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
