package com.book.dao;

import com.book.domain.AccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

@Repository
public class WeixinDAO {
    private JdbcTemplate jdbcTemplate;
    SimpleDateFormat df = new SimpleDateFormat("yyyy-M-d HH:mm:ss");
    private final static String ACCESS_LIST_SQL="SELECT * FROM accesstoken";
    private final static String UPDATE_ACCESS_SQL="INSERT  into accesstoken (access_token,access_time) values (?,?)";
    private final static String DELETE_ACCESS_SQL = "DELETE from accesstoken";



    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public AccessToken getAccess(){
        AccessToken accessToken = new AccessToken();
        jdbcTemplate.query(ACCESS_LIST_SQL,new RowCallbackHandler() {
            public void processRow(ResultSet resultSet) throws SQLException {
                accessToken.setAccess_token(resultSet.getString("access_token"));
                accessToken.setAccess_time(resultSet.getTimestamp("access_time").toString());
            }
        });
        return accessToken;
    }

    public int updateAccess(AccessToken accessToken){
        String access_token = accessToken.getAccess_token();
        Timestamp access_time=java.sql.Timestamp.valueOf(accessToken.getAccess_time());
        return jdbcTemplate.update(UPDATE_ACCESS_SQL,new Object[]{access_token,access_time});
    }

    public void deleteAccess(){
        jdbcTemplate.update(DELETE_ACCESS_SQL);
    }


}
