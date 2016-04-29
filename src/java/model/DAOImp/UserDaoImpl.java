package model.DAOImp;

import java.sql.Statement;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.DAO.UserDao;
import model.dto.Credit;
import model.dto.Order;
import model.dto.User;
import util.JdbcConnection;

/**
 *
 * @author AgriMarket team
 */
public class UserDaoImpl implements UserDao {

    Connection con;

    public UserDaoImpl() {
        try {
            con = JdbcConnection.getConnection();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    @Override
    public List<User> getAllUsers() {
        List<User> allUser = new ArrayList();
        try {
            Statement statment = con.createStatement();
            ResultSet result = statment.executeQuery("select * from agri_project.user");
            while (result.next()) {
                User user = new User();
                user.setUserName(result.getString("user_name"));
                user.setDOB(result.getDate("DOB").toLocalDate());
                user.setCreditNumber(result.getString("credit_number"));
                user.setEmail(result.getString("email"));
                user.setImage(result.getBytes("image"));
                user.setInterests(getAllInterests(result.getString("email")));
                user.setJob(result.getString("job"));
                user.setPassword(result.getString("password"));
                OrderDaoImp orders = new OrderDaoImp();
                user.setOrders(orders.getAllOrders(result.getString("email")));
                user.setCart(orders.getCart(result.getString("email")));
                CreditDaoImpl creditDao = new CreditDaoImpl();
                user.setBalance(creditDao.getBalance(result.getString("credit_number")));
                allUser.add(user);
            }
            return allUser;
        } catch (SQLException ex) {
            Logger.getLogger(UserDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     *
     * @param email user email
     * @param password user Password
     * @return user function for retrieve user
     */
    @Override
    public User signIn(String email, String password) {
        User user = null;
        ResultSet res = null;

        try {
            PreparedStatement pst = con.prepareStatement("select * from user where email =? and password = ?");
            pst.setString(1, email);
            pst.setString(2, password);
            res = pst.executeQuery();
            System.out.println("sign in");
            if (res.next()) {
                System.out.println("found");
                user = new User();
                user.setEmail(res.getString("email"));
                user.setUserName(res.getString("user_name"));
                user.setPassword(res.getString("password"));
                user.setJob(res.getString("job"));
                user.setAddress(res.getString("address"));
                user.setImage(res.getBytes("image"));
                user.setDOB(res.getDate("DOB").toLocalDate());
                user.setCreditNumber(res.getString("credit_number"));
                user.setInterests(getAllInterests(email));
                OrderDaoImp order = new OrderDaoImp();
                user.setOrders(order.getAllOrders(email));
                user.setCart(order.getCart(res.getString("email")));
                CreditDaoImpl creditDao = new CreditDaoImpl();

                user.setBalance(creditDao.getBalance(res.getString("credit_number")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;

    }

    public ArrayList<User> getAllUsersLazy() {
        ArrayList<User> allUser = new ArrayList();
        try {
            Statement statment = con.createStatement();
            ResultSet result = statment.executeQuery("select * from user");
            while (result.next()) {
                User user = new User();
                user.setUserName(result.getString("user_name"));
                user.setDOB(result.getDate("DOB").toLocalDate());
                user.setCreditNumber(result.getString("credit_number"));
                user.setEmail(result.getString("email"));
                user.setImage(result.getBytes("image"));
                allUser.add(user);
            }
            return allUser;
        } catch (SQLException ex) {
            Logger.getLogger(UserDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public ArrayList<String> getAllInterests(String userEmail) {
        ArrayList<String> userInterests = new ArrayList();
        try {

            Statement interestsStatements = con.createStatement();
            ResultSet interestsResult = interestsStatements.executeQuery("select * from agri_project.interests where email= '" + userEmail + "'");
            while (interestsResult.next()) {
                userInterests.add(interestsResult.getString("name"));
            }
            return userInterests;
        } catch (SQLException ex) {
            Logger.getLogger(UserDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return userInterests;
    }

    @Override
    public void updateUser(User user) {

        try {
            PreparedStatement pst = con.prepareStatement("update user set email=? , user_name=? , password=? , job=? , address=? , image=? , DOB=? , credit_number=?  where email =?");
            pst.setString(1, user.getEmail());
            pst.setString(2, user.getUserName());
            pst.setString(3, user.getPassword());
            pst.setString(4, user.getJob());
            pst.setString(5, user.getAddress());
            pst.setBytes(6, user.getImage());
            pst.setDate(7, Date.valueOf(user.getDOB()));
            pst.setString(8, user.getCreditNumber());
            pst.setString(9, user.getEmail());
            pst.executeUpdate();

            pst = con.prepareStatement("delete from interests where email =?");
            pst.setString(1, user.getEmail());
            pst.executeUpdate();

            ArrayList<String> lst = user.getInterests();
            for (int i = 0; i < lst.size(); i++) {
                String get = lst.get(i);

                pst = con.prepareStatement("insert into  interests (email,name) values (?,?)");
                pst.setString(1, user.getEmail());
                pst.setString(2, get);
                pst.executeUpdate();

            }

            pst.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void updateBalance(String user, int balance) {

        try {
            PreparedStatement pst = con.prepareStatement("update credit set balance=? where number=?");
            pst.setString(2, user);
            pst.setInt(1, balance);
            pst.executeUpdate();
            pst.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean signUp(User user) {

        try {

            PreparedStatement pst = con.prepareStatement("insert into user values(?,?,?,?,?,?,?,?)");
            pst.setString(1, user.getEmail());
            pst.setString(2, user.getUserName());
            pst.setString(3, user.getPassword());
            pst.setString(4, user.getJob());
            pst.setString(5, user.getAddress());
            pst.setBytes(6, user.getImage());
            pst.setDate(7, Date.valueOf(user.getDOB()));
            pst.setString(8, user.getCreditNumber());
            pst.executeUpdate();

            ArrayList<String> lst = user.getInterests();
            for (int i = 0; i < lst.size(); i++) {
                String get = lst.get(i);

                pst = con.prepareStatement("insert into  interests (email,name) values (?,?)");
                pst.setString(1, user.getEmail());
                pst.setString(2, get);
                pst.executeUpdate();
                Order cart = new Order();
                cart.setDate(LocalDate.now());
                cart.setUser_email(user.getEmail());
                cart.setStatus("cart");
                OrderDaoImp orderDao = new OrderDaoImp();
                if (orderDao.addOrder(cart)) {
                    cart = orderDao.getCart(user.getEmail());
                    user.setCart(cart);
                    user.getOrders().add(cart);
                }
            }

            CreditDaoImpl creditDao = new CreditDaoImpl();

            user.setBalance(creditDao.getBalance(user.getCreditNumber()));

            pst.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public User getUser(String email
    ) {
        try {
            Statement statment = con.createStatement();
            ResultSet result = statment.executeQuery("select * from user where email='" + email + "';");
            result.next();
            User user = new User();
            user.setUserName(result.getString("user_name"));
            user.setDOB(result.getDate("DOB").toLocalDate());
            user.setCreditNumber(result.getString("credit_number"));
            user.setEmail(result.getString("email"));
            user.setImage(result.getBytes("image"));
            user.setAddress(result.getString("address"));
            user.setInterests(getAllInterests(result.getString("email")));
            user.setJob(result.getString("job"));
            user.setPassword(result.getString("password"));
            OrderDaoImp orders = new OrderDaoImp();
            user.setOrders(orders.getAllOrders(result.getString("email")));

            user.setCart(orders.getCart(result.getString("email")));
            CreditDaoImpl creditDao = new CreditDaoImpl();

            user.setBalance(creditDao.getBalance(result.getString("credit_number")));

            return user;
        } catch (SQLException ex) {
            Logger.getLogger(UserDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    @Override
    public boolean isUserExist(String email, String Password
    ) {
        ResultSet res = null;
        try {
            PreparedStatement pst = con.prepareStatement("select * from user where email =? and password = ?");
            pst.setString(1, email);
            pst.setString(2, Password);
            res = pst.executeQuery();
            if (res.next()) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }

    public boolean isUserExist(String email) {
        ResultSet res = null;
        try {
            PreparedStatement pst = con.prepareStatement("select * from user where email =?");
            pst.setString(1, email);

            res = pst.executeQuery();
            if (res.next()) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }

    @Override
    public boolean isCreditNumberAssigned(Credit Credit) {
        ResultSet res = null;
        try {
            PreparedStatement pst = con.prepareStatement("select * from user where credit_number=? ;");
            pst.setString(1, Credit.getNumber());
            res = pst.executeQuery();
            if (res.next()) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }

}
