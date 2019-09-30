package dao;

import com.sun.istack.internal.NotNull;
import model.RegistUser;

import java.sql.*;

/**
 * @Author Fisher
 * @Date 2019/9/6 10:15
 **/


public class userDao {

    public String serverURL = "jdbc:mysql://192.168.199.241:3306/";
    public String database = "easymall";
    public String serverTimeZone = "?serverTimezone=UTC";

    private String username = "fisher";
    private String password = "12345678";

    private Connection connection = null;
    private PreparedStatement statement = null;
    private ResultSet result = null;

    public boolean init() {
        try {
            // 加载驱动
            Class.forName("com.mysql.cj.jdbc.Driver");
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

    public boolean select(@NotNull String User_name) {
        try {
            String sql = "select * from user where username = ?";

            statement = connection.prepareStatement(sql);
            statement.setString(1, User_name);

            result = statement.executeQuery();

            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean add(@NotNull RegistUser user) {
        try {
            String sql = "insert into user(username,password,nickname,email) values(?,?,?,?)";

            statement = connection.prepareStatement(sql);
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPsw());
            statement.setString(3, user.getNickname());
            statement.setString(4, user.getEmail());

            statement.executeUpdate();

            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(@NotNull String User_name) {
        try {
            String sql = "delete from user where username=?";

            statement = connection.prepareStatement(sql);
            statement.setString(1, User_name);

            statement.executeUpdate();

            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean update(@NotNull String User_name, @NotNull String password) {
        try {
            String sql = "update user set password=? where username=?";

            statement = connection.prepareStatement(sql);
            statement.setString(1, password);
            statement.setString(2, User_name);

            statement.executeUpdate();

            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public ResultSet getResult() {
        return result;
    }

    public static void main(String[] args) {
        userDao lesson1 = new userDao();

        if (lesson1.init()) {
            System.out.println("--------Select-------");
            lesson1.select("admin");
            ResultSet resultSet = lesson1.getResult();
            try {
                while (resultSet.next()) {
                    System.out.println("UUID: " + resultSet.getString(1));
                    System.out.println("User Name: " + resultSet.getString(2));
                    System.out.println("Nickname: " + resultSet.getString(3));
                    System.out.println("Email: " + resultSet.getString(4));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

//            System.out.println("---------ADD---------");
//            if (lesson1.add("沃克","Spurs", "Walker", "123@qq.com")) {
//                System.out.println("Add success!");
//            } else {
//                System.out.println("Add fail!");
//            }

            System.out.println("----------Update---------");
            if (lesson1.update("沃克", "San Antonio Spurs")) {
                System.out.println("Update success!");
            } else {
                System.out.println("Update fail!");
            }

//            System.out.println("----------Delete----------");
//            if (lesson1.delete("沃克")) {
//                System.out.println("Delete success!");
//            } else {
//                System.out.println("Delete fail!");
//            }

        }

        lesson1.release();
    }

}