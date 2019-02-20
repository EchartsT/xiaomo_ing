package com.book.dao;

import com.book.domain.ActiveRank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

@Repository
public class ActiveDao {
    private JdbcTemplate jdbcTemplate;
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

    private final static String ActiveRank_LIST_SQL="SELECT * FROM activerank ORDER BY actionTime desc";
    private final static String ActiveRank_DELETE_SQL="delete from activerank where userId = ? ";
    private final static String GET_AC_SQL="SELECT * FROM activerank where userId = ? ";
    private final static String ActiveRank_LIST_ASC_SQL="SELECT * FROM activerank ORDER BY actionTime asc";

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public ArrayList<ActiveRank> acList(){
        final ArrayList<ActiveRank> list=new ArrayList<ActiveRank>();
        jdbcTemplate.query(ActiveRank_LIST_SQL, new RowCallbackHandler() {
            public void processRow(ResultSet resultSet) throws SQLException {
                resultSet.beforeFirst();
                while (resultSet.next()){
                    ActiveRank ac=new ActiveRank();
                    ac.setActiveId(resultSet.getInt("activeId"));
                    ac.setUserId(resultSet.getString("userId"));
                    ac.setActionTime(resultSet.getInt("actionTime"));
                    list.add(ac);
                }
            }
        });
        return list;
    }
    public ArrayList<ActiveRank> acList_asc(){
        final ArrayList<ActiveRank> list=new ArrayList<ActiveRank>();
        jdbcTemplate.query(ActiveRank_LIST_ASC_SQL, new RowCallbackHandler() {
            public void processRow(ResultSet resultSet) throws SQLException {
                resultSet.beforeFirst();
                while (resultSet.next()){
                    ActiveRank ac=new ActiveRank();
                    ac.setActiveId(resultSet.getInt("activeId"));
                    ac.setUserId(resultSet.getString("userId"));
                    ac.setActionTime(resultSet.getInt("actionTime"));
                    list.add(ac);
                }
            }
        });
        return list;
    }
    public int deleteacList(String operatorId){
        return jdbcTemplate.update(ActiveRank_DELETE_SQL,operatorId);
    }
    public ArrayList<ActiveRank> matchac(String searchWord){
        final ArrayList<ActiveRank> list=new ArrayList<ActiveRank>();
        jdbcTemplate.query(GET_AC_SQL, new Object[]{searchWord} ,new RowCallbackHandler() {
            public void processRow(ResultSet resultSet) throws SQLException {
                resultSet.beforeFirst();
                while (resultSet.next()){
                    ActiveRank ac=new ActiveRank();
                    ac.setActiveId(resultSet.getInt("activeId"));
                    ac.setUserId(resultSet.getString("userId"));
                    ac.setActionTime(resultSet.getInt("actionTime"));
                    list.add(ac);
                }
            }
        });
        return list;
    }
}
