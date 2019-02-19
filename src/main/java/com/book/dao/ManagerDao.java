package com.book.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ManagerDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    private static final String MATCH_ADMIN_SQL="SELECT COUNT(*) FROM manager where managerId = ? and managerPwd = ? ";
    private static final String RE_PASSWORD_SQL="UPDATE manager set managerPwd = ? where managerId = ? ";
    private static final String GET_PASSWD_SQL="SELECT managerPwd from manager where managerId = ?";

    public int getMatchCount(int managerId,String password){
        return jdbcTemplate.queryForObject(MATCH_ADMIN_SQL,new Object[]{managerId,password},Integer.class);
    }

    public int rePassword(int managerId,String newPasswd){
        return jdbcTemplate.update(RE_PASSWORD_SQL,new Object[]{newPasswd,managerId});
    }
    public String getPasswd(int managerId){
        return jdbcTemplate.queryForObject(GET_PASSWD_SQL,new Object[]{managerId},String.class);
    }

}
