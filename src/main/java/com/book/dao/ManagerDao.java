package com.book.dao;

import com.book.domain.Manager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

@Repository
public class ManagerDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    private static final String MATCH_ADMIN_SQL="SELECT COUNT(*) FROM manager where managerId = ? and managerPwd = ? ";
    private static final String SEARCH_ADMIN_SQL="SELECT * FROM manager where managerId = ?  ";
    private static final String RE_PASSWORD_SQL="UPDATE manager set managerPwd = ? where managerId = ? ";
    private static final String GET_PASSWD_SQL="SELECT managerPwd from manager where managerId = ?";
    private static final String Manager_LIST_SQL="SElECT * FROM manager";
    private final static String Manager_DELETE_SQL="DELETE from manager where managerId= ? ";
    private final static String GET_MA_SQL="SELECT * FROM manager where managerName = ? ";
    private final static String EDIT_MANAGER_SQL="update manager set  managerName = ? ,managerPwd = ?,managerStatus=? where managerId=?";

    public int getMatchCount(String managerId,String password){
        return jdbcTemplate.queryForObject(MATCH_ADMIN_SQL,new Object[]{managerId,password},Integer.class);
    }
    //修改时查找其他信息
    public ArrayList<Manager> getMatch(String managerId){
        //return jdbcTemplate.queryForObject(SEARCH_ADMIN_SQL,new Object[]{managerId},Manager.class);
        final ArrayList<Manager> list=new ArrayList<Manager>();
        jdbcTemplate.query(SEARCH_ADMIN_SQL, new Object[]{managerId},new RowCallbackHandler() {
            public void processRow(ResultSet resultSet) throws SQLException {
                resultSet.beforeFirst();
                while (resultSet.next()){
                    Manager ma=new Manager();
                    ma.setManagerId(resultSet.getString("managerId"));
                    ma.setManagerName(resultSet.getString("managerName"));
                    ma.setManagerPwd(resultSet.getString("managerPwd"));
                    ma.setManagerStatus(resultSet.getString("managerStatus"));
                    list.add(ma);
                }
            }
        });
        return list;

    }

    public int rePassword(int managerId,String newPasswd){
        return jdbcTemplate.update(RE_PASSWORD_SQL,new Object[]{newPasswd,managerId});
    }
    public String getPasswd(int managerId){
        return jdbcTemplate.queryForObject(GET_PASSWD_SQL,new Object[]{managerId},String.class);
    }
    public ArrayList<Manager> managerList(){
        final ArrayList<Manager> list=new ArrayList<Manager>();
        jdbcTemplate.query(Manager_LIST_SQL, new RowCallbackHandler() {
            public void processRow(ResultSet resultSet) throws SQLException {
                resultSet.beforeFirst();
                while (resultSet.next()){
                    Manager ma=new Manager();
                    ma.setManagerId(resultSet.getString("managerId"));
                    ma.setManagerName(resultSet.getString("managerName"));
                    ma.setManagerPwd(resultSet.getString("managerPwd"));
                    ma.setManagerStatus(resultSet.getString("managerStatus"));
                    list.add(ma);
                }
            }
        });
        return list;
    }
    public int deletemaList(String managerId){
        return jdbcTemplate.update(Manager_DELETE_SQL,managerId);
    }
    public ArrayList<Manager> matchMA(String searchWord){
        final ArrayList<Manager> list=new ArrayList<Manager>();
        jdbcTemplate.query(GET_MA_SQL, new Object[]{searchWord} ,new RowCallbackHandler() {
            public void processRow(ResultSet resultSet) throws SQLException {
                resultSet.beforeFirst();
                while (resultSet.next()){
                    Manager ma=new Manager();
                    ma.setManagerId(resultSet.getString("managerId"));
                    ma.setManagerName(resultSet.getString("managerName"));
                    ma.setManagerPwd(resultSet.getString("managerPwd"));
                    ma.setManagerStatus(resultSet.getString("managerStatus"));
                    list.add(ma);
                }
            }
        });
        return list;
    }


    public int editManager(Manager manager){
        String managerId = manager.getManagerId();
        String managerName = manager.getManagerName();
        String managerPwd = manager.getManagerPwd();
        String managerStatus = manager.getManagerStatus();
        return jdbcTemplate.update(EDIT_MANAGER_SQL,new Object[]{managerName,managerPwd,managerStatus,managerId});
    }

}
