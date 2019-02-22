package com.book.dao;

import com.book.domain.Book;
import com.book.domain.Manager;
import com.book.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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

    public static void extract(User user, ResultSet resultSet) throws SQLException{
        user.setUserId(resultSet.getString("userId"));
        user.setUserOpenId(resultSet.getString("userOpenId"));
        user.setUserName(resultSet.getString("userName"));
        user.setIsSubscribe(resultSet.getBoolean("isSubscribe"));
        user.setChatData(resultSet.getAsciiStream("chatData"));
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




}
