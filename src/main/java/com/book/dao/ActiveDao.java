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
    private final static String GET_RowNum_SQL="select count(*) from activerank where userId = ? ";
    private final static String ADD_ActiveItem_SQL="INSERT INTO activerank (userId,actionTime) VALUES(?,0)";
    private final static String UPDATE_ActiveItem_SQL="UPDATE activerank SET actionTime = actionTime + 1 WHERE userId = ? ";

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

    public int addActiveItem(ActiveRank activeRank){
        String userId=activeRank.getUserId();
        int activeId = activeRank.getActiveId();

        int rowNum = jdbcTemplate.queryForObject(GET_RowNum_SQL, new Object[]{userId},Integer.class);
        if(rowNum == 0){
            return jdbcTemplate.update(ADD_ActiveItem_SQL,new Object[]{userId});
        }return 0;
    }

    public int updateActiveItem(ActiveRank activeRank){
        String userId=activeRank.getUserId();

        return jdbcTemplate.update(UPDATE_ActiveItem_SQL,new Object[]{userId});
    }
}
