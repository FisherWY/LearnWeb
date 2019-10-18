package dao;

import com.sun.istack.internal.NotNull;
import model.Order;
import model.OrderInfo;
import model.OrderItem;
import model.Product;
import preference.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: Fisher
 * @Date: 2019/10/18 9:26 上午
 */

public class orderDao {

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

    public void addOrder(Order order, List<OrderItem> list) throws Exception {
        try {
            String sql = "insert into orders(id,money,receiverinfo,paystate,user_id) values (?,?,?,?,?)";

            statement = connection.prepareStatement(sql);
            statement.setString(1, order.getId());
            statement.setDouble(2, order.getMoney());
            statement.setString(3, order.getReceiverinfo());
            statement.setInt(4, order.getPaystate());
            statement.setInt(5, order.getUser_id());
            statement.executeQuery();

            for (OrderItem orderItem : list) {
                int buyNum = orderItem.getBuynum();
                String pid = orderItem.getProduct_id();
                Product product = getProductInfo(pid);
                if (buyNum > product.getPnum()) {
                    throw new Exception("库存不足，添加订单失败");
                }
                addOrderItem(orderItem);
                updatePnum(pid, product.getPnum() - buyNum);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Product getProductInfo(@NotNull String id) {
        Product product = new Product();
        try {
            String sql = "select * from products where id=?";

            statement = connection.prepareStatement(sql);
            statement.setString(1, id);

            result = statement.executeQuery();

            result.next();

            product.setId(result.getString(1));
            product.setName(result.getString(2));
            product.setPrice(result.getDouble(3));
            product.setCategory(result.getString(4));
            product.setPnum(result.getInt(5));
            product.setImgurl(result.getString(6));
            product.setDescription(result.getString(7));

            return product;
        } catch (Exception e) {
            e.printStackTrace();
            return product;
        }
    }

    public void addOrderItem(OrderItem item) {
        try {
            String sql = "insert into orderitem(order_id,product_id,buynum) values (?,?,?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, item.getOrder_id());
            statement.setString(2, item.getProduct_id());
            statement.setInt(3, item.getBuynum());
            statement.executeUpdate();
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updatePnum(String pid, int pnum) {
        try {
            String sql = "update products set pnum = ? where id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, pnum);
            statement.setString(2, pid);
            statement.executeUpdate();
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<OrderInfo> findOrderInfoByUserId(int userId) {
        List<OrderInfo> orderInfoList = new ArrayList<OrderInfo>();
        List<Order> orderList = findOrderByUserId(userId);
        for (Order order : orderList) {
            String orderId = order.getId();
            List<OrderItem> orderItems = findOrderItemByOrderId(orderId);
            Map<Product, Integer> map = new HashMap<Product, Integer>();
            for (OrderItem orderItem : orderItems) {
                Product product = finProductById(orderItem.getProduct_id());
                map.put(product, orderItem.getBuynum());
            }
            OrderInfo orderInfo = new OrderInfo();
            orderInfo.setOrder(order);
            orderInfo.setMap(map);
            orderInfoList.add(orderInfo);
        }
        return orderInfoList;
    }

    public List<Order> findOrderByUserId(int userId) {
        List<Order> orderList = new ArrayList<Order>();
        try {
            String sql = "select * from orders where user_id=?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, userId);
            ResultSet res = statement.executeQuery();
            while (res.next()) {
                Order order = new Order();
                order.setId(res.getString("id"));
                order.setMoney(res.getDouble("money"));
                order.setReceiverinfo(res.getString("receiverinfo"));
                order.setPaystate(res.getInt("paystate"));
                order.setUser_id(res.getInt("user_id"));
                order.setOrdertime(res.getTimestamp("ordertime"));
                orderList.add(order);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return orderList;
    }

    public List<OrderItem> findOrderItemByOrderId(String orderId) {
        List<OrderItem> orderItems = new ArrayList<>();
        try {
            String sql = "select * from orderItem where order_id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, orderId);
            ResultSet res = statement.executeQuery();
            while (res.next()) {
                OrderItem item = new OrderItem();
                item.setOrder_id(orderId);
                item.setProduct_id(res.getString(2));
                item.setBuynum(res.getInt(3));
                orderItems.add(item);
            }
            return orderItems;
        } catch (Exception e) {
            e.printStackTrace();
            return orderItems;
        }
    }

    public Product finProductById(String id) {
        Product product = new Product();
        try {
            String sql = "select * from products where id=?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, id);
            ResultSet res = statement.executeQuery();
            if (res.next()) {
                product.setId(id);
                product.setName(res.getString("name"));
                product.setCategory(res.getString("category"));
                product.setDescription(res.getString("description"));
                product.setImgurl(res.getString("imgurl"));
                product.setPnum(res.getInt("pnum"));
                product.setPrice(res.getDouble("price"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return product;
    }
}
