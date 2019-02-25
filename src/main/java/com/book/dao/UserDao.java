package com.book.dao;

import com.book.domain.Book;
import com.book.domain.Manager;
import com.book.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

@Repository
public class UserDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final String User_LIST_SQL="SELECT * FROM user ";
    private final static String User_DELETE_SQL="DELETE from User where userId= ? ";
    private final static String QUERY_User_SQL="SELECT * FROM user WHERE userName like ?   ";
    private final static String QUERY_User_SQL2="SELECT * FROM user WHERE userId = ?   ";

    private final static String User_Add_SQL="INSERT INTO user (userId,userName,isSubscribe,fileName) VALUES ( ?, ?  ,?,?)";
    private final static String GET_RowNum_SQL="select count(*) from user where userId = ? ";
    private final static String ADD_User_SQL="INSERT INTO user (userId,userName,isSubscribe) VALUES(?,?,?)";
    private final static String UPDATE_User_SQL="UPDATE user SET isSubscribe = ? WHERE userId = ?";
    private final static String UPDATE_UserChatData_SQL="UPDATE user SET chatData = ? WHERE userId = ?";


    public int addUser(User user){
        String userId = user.getUserId();
        String userName = user.getUserName();
        boolean isSubscribe = user.getIsSubscribe();
        String fileName = user.getFileName();
        return  jdbcTemplate.update(User_Add_SQL,new Object[]{userId,userName,isSubscribe,fileName});
    }

    public static void extract(User user, ResultSet resultSet) throws SQLException{
        user.setUserId(resultSet.getString("userId"));
        user.setUserName(resultSet.getString("userName"));
        user.setIsSubscribe(resultSet.getBoolean("isSubscribe"));
        user.setFileName(resultSet.getString("fileName"));

    }

    public ArrayList<User> userList(){
        final ArrayList<User> list=new ArrayList<User>();
        jdbcTemplate.query(User_LIST_SQL, new RowCallbackHandler() {
            public void processRow(ResultSet resultSet) throws SQLException {
                resultSet.beforeFirst();
                while (resultSet.next()){
                    User user=new User();
                    extract(user,resultSet);
                    list.add(user);
                }
            }
        });
        return list;
    }

    public User queryUserById(String userId){
        User user = new User();
        jdbcTemplate.query(QUERY_User_SQL2, new Object[]{userId}, new RowCallbackHandler() {
            public void processRow(ResultSet resultSet) throws SQLException {
                resultSet.beforeFirst();
                while (resultSet.next()){
                    extract(user,resultSet);
                }
            }
        });
        return user;
    }


    public int deleteUserList(String userId){
        return jdbcTemplate.update(User_DELETE_SQL,userId);
    }

    public ArrayList<User> queryUser(String userName){
        String swcx="%"+userName+"%";
        final ArrayList<User> users=new ArrayList<User>();
        jdbcTemplate.query(QUERY_User_SQL, new Object[]{swcx}, new RowCallbackHandler() {
            public void processRow(ResultSet resultSet) throws SQLException {
                resultSet.beforeFirst();
                while (resultSet.next()){
                    User user=new User();
                    extract(user,resultSet);
                    users.add(user);
                }
            }
        });
        return users;
    }

    public int addUser2(User user){
        String userId = user.getUserId();
        String userName = user.getUserName();
        boolean isSubscribe = user.getIsSubscribe();

        int rowNum = jdbcTemplate.queryForObject(GET_RowNum_SQL, new Object[]{userId},Integer.class);
        if(rowNum == 0){
            return jdbcTemplate.update(ADD_User_SQL,new Object[]{userId,userName,String.valueOf(isSubscribe)});
        }
        else{
            return jdbcTemplate.update(UPDATE_User_SQL,new Object[]{String.valueOf(isSubscribe),userId});
        }
    }

    public int updateUser(User user){
        String userId = user.getUserId();
        boolean isSubscribe = user.getIsSubscribe();

        return jdbcTemplate.update(UPDATE_User_SQL,new Object[]{String.valueOf(isSubscribe),userId});
    }
    public int updateUser_chatdata(User user){
        String userId = user.getUserId();
        String chatData = user.getChatData();

        return jdbcTemplate.update(UPDATE_UserChatData_SQL,new Object[]{chatData,userId});
    }

}