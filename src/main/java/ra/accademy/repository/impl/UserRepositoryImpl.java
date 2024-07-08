package ra.accademy.repository.impl;

import org.springframework.stereotype.Repository;
import ra.accademy.model.User;
import ra.accademy.repository.IUserRepository;
import ra.accademy.util.ConnectionDB;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
@Repository
public class UserRepositoryImpl implements IUserRepository {
    @Override
    public List<User> findAll() {
        List<User>users = new ArrayList<User>();
        Connection conn = null;
        CallableStatement callSt = null;
        try{
            // mở kết nối
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("{call proc_get_all_users()}");
             ResultSet rs = callSt.executeQuery();
             while (rs.next()){
                 User user = User.builder()
                         .id(rs.getInt("User_Id"))
                         .username(rs.getString("User_Name"))
                         .fullName(rs.getString("User_FullName"))
                         .address(rs.getString("User_Address"))
                         .email(rs.getString("User_Email"))
                         .phone(rs.getString("User_Phone"))
                         .role(rs.getBoolean("User_Permission"))
                         .active(rs.getBoolean("User_Status"))
                         .build();
                 users.add(user);
             }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
                ConnectionDB.closeConnection(conn,callSt);
        }
        return users;
    }

    @Override
    public void create(User user) {
        Connection conn = null;
        CallableStatement callSt = null;
        try{
            // mở kết nối
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("{call proc_create_users(?,?,?,?,?,?)}");
            // truyền đối số
            callSt.setString(1, user.getUsername());
            callSt.setString(2, user.getPassword());
            callSt.setString(3, user.getFullName());
            callSt.setString(4, user.getAddress());
            callSt.setString(5, user.getEmail());
            callSt.setString(6, user.getPhone());

            callSt.executeUpdate();

        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            ConnectionDB.closeConnection(conn,callSt);
        }
    }

    @Override
    public User findByUserName(String username) {
        Connection conn = null;
        CallableStatement callSt = null;
        try{
            // mở kết nối
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("select * from Web_User where  user_name=? ");
            callSt.setString(1, username);
            ResultSet rs = callSt.executeQuery();
            if (rs.next()){
               return User.builder()
                        .id(rs.getInt("User_Id"))
                       .password(rs.getString("User_Password"))
                        .username(rs.getString("User_Name"))
                        .fullName(rs.getString("User_FullName"))
                        .address(rs.getString("User_Address"))
                        .email(rs.getString("User_Email"))
                        .phone(rs.getString("User_Phone"))
                        .role(rs.getBoolean("User_Permission"))
                        .active(rs.getBoolean("User_Status"))
                        .build();
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            ConnectionDB.closeConnection(conn,callSt);
        }
        return null;
    }
}
