package com.book.dao;

import com.book.domain.Lend;
import com.book.domain.Oprecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.spel.ast.Operator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
@Repository
public class OperatorDao {
    private JdbcTemplate jdbcTemplate;
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

    private final static String Oprecord_LIST_SQL="SELECT * FROM oprecord";
    private final static String Oprecord_DELETE_SQL="delete from oprecord where operatorId = ? ";
    private final static String GET_OP_SQL="SELECT * FROM oprecord where userId = ? ";
    private final static String ADD_Oprecord_SQL="INSERT INTO operecord VALUES(NULL,?,?,NULL,?,NULL)";
    private final static String UPDATE_Oprecord_SQL="UPDATE operecord SET endTime = ? WHERE userId = ?";

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public ArrayList<Oprecord> opList(){
        final ArrayList<Oprecord> list=new ArrayList<Oprecord>();
        jdbcTemplate.query(Oprecord_LIST_SQL, new RowCallbackHandler() {
            public void processRow(ResultSet resultSet) throws SQLException {
                resultSet.beforeFirst();
                while (resultSet.next()){
                    Oprecord op=new Oprecord();
                    op.setOperatorId(resultSet.getString("operatorId"));
                    op.setUserId(resultSet.getString("userId"));
                    op.setStartTime(resultSet.getTimestamp("startTime").toString());
                    op.setEndTime(resultSet.getTimestamp("endTime").toString());
                    op.setFileName(resultSet.getString("fileName"));
                    list.add(op);
                }
            }
        });
        return list;
    }
    public int deleteopList(String operatorId){
        return jdbcTemplate.update(Oprecord_DELETE_SQL,operatorId);
    }
    public ArrayList<Oprecord> matchOP(String searchWord){
        final ArrayList<Oprecord> list=new ArrayList<Oprecord>();
        jdbcTemplate.query(GET_OP_SQL, new Object[]{searchWord} ,new RowCallbackHandler() {
            public void processRow(ResultSet resultSet) throws SQLException {
                resultSet.beforeFirst();
                while (resultSet.next()){
                    Oprecord op=new Oprecord();
                    op.setOperatorId(resultSet.getString("operatorId"));
                    op.setUserId(resultSet.getString("userId"));
                    op.setStartTime(resultSet.getTimestamp("startTime").toString());
                    op.setEndTime(resultSet.getTimestamp("endTime").toString());
                    op.setFileName(resultSet.getString("fileName"));
                    list.add(op);
                }
            }
        });
        return list;
    }

    public int addOprecord(Oprecord oprecord){
        String userId=oprecord.getUserId();
        String startTime=oprecord.getStartTime();
        String fileName = oprecord.getFileName();

        return jdbcTemplate.update(ADD_Oprecord_SQL,new Object[]{userId,startTime,fileName});
    }

    public int updateOprecord(Oprecord oprecord){
        String userId=oprecord.getUserId();
        String endTime = oprecord.getEndTime();

        return jdbcTemplate.update(UPDATE_Oprecord_SQL,new Object[]{userId,endTime});
    }
}
