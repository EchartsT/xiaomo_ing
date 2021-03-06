package com.book.dao;

import com.book.domain.AccessToken;
import com.book.domain.Lend;
import com.book.domain.Oprecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

@Repository
public class OperatorDao {
    private JdbcTemplate jdbcTemplate;
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

    private final static String Oprecord_LIST_SQL="SELECT * FROM oprecord";
    private final static String Oprecord_DELETE_SQL="delete from oprecord where operatorId = ? ";
    private final static String GET_OP_SQL="SELECT * FROM oprecord where userId = ? or operatorId = ? ";
    private final static String GET_RowNum_SQL="select count(*) from oprecord where userId = ? ";
    private final static String ADD_Oprecord_SQL="INSERT INTO oprecord (operatorId,userId,startTime,endTime,fileName,active) VALUES(?,?,?,?,?,'0')";
    private final static String ADD_Oprecord_AGAIN_SQL="INSERT INTO oprecord (operatorId,userId,startTime,endTime,fileName,active,messageType) VALUES(?,?,?,?,?,'0',?)";
    private final static String UPDATE_Oprecord_SQL="UPDATE oprecord SET endTime = ? WHERE userId = ?";
    private final static String UPDATE_Oprecord_SQL2="UPDATE oprecord SET lastQuestion = ?,active = '1',messagetype = ? WHERE userId = ?";

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
        jdbcTemplate.query(GET_OP_SQL, new Object[]{searchWord,searchWord} ,new RowCallbackHandler() {
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
        String operatorId = oprecord.getOperatorId();
        Timestamp startTime=java.sql.Timestamp.valueOf(oprecord.getStartTime());
        String fileName = oprecord.getFileName();

        int rowNum = jdbcTemplate.queryForObject(GET_RowNum_SQL, new Object[]{userId},Integer.class);
        if(rowNum == 0){
            return jdbcTemplate.update(ADD_Oprecord_SQL,new Object[]{operatorId,userId,startTime,startTime,fileName});
        }return 0;
    }

    public int addOprecord_again(Oprecord oprecord){
        String userId=oprecord.getUserId();
        String operatorId = oprecord.getOperatorId();
        Timestamp startTime=java.sql.Timestamp.valueOf(oprecord.getStartTime());
        String fileName = oprecord.getFileName();
        String messageType = oprecord.getMessagetype();

        int rowNum = jdbcTemplate.queryForObject(GET_RowNum_SQL, new Object[]{userId},Integer.class);
        if(rowNum == 0){
            return jdbcTemplate.update(ADD_Oprecord_AGAIN_SQL,new Object[]{operatorId,userId,startTime,startTime,fileName,messageType});
        }return 0;
    }

    public int updateOprecord(Oprecord oprecord){
        String userId=oprecord.getUserId();
        Timestamp endTime=java.sql.Timestamp.valueOf(oprecord.getEndTime());

        return jdbcTemplate.update(UPDATE_Oprecord_SQL,new Object[]{endTime,userId});
    }

    public int updateOprecord2(Oprecord oprecord){
        String userId=oprecord.getUserId();
        String lastQuestion = oprecord.getLastQuestion();
        String messagetype = oprecord.getMessagetype();

        return jdbcTemplate.update(UPDATE_Oprecord_SQL2,new Object[]{lastQuestion,messagetype,userId});
    }

    public Oprecord queryOprecordById(String userId){
        Oprecord op = new Oprecord();
        jdbcTemplate.query(GET_OP_SQL, new Object[]{userId,userId}, new RowCallbackHandler() {
            public void processRow(ResultSet resultSet) throws SQLException {
                    op.setOperatorId(resultSet.getString("operatorId"));
                    op.setUserId(resultSet.getString("userId"));
                    op.setStartTime(resultSet.getTimestamp("startTime").toString());
                    op.setEndTime(resultSet.getTimestamp("endTime").toString());
                    op.setFileName(resultSet.getString("fileName"));
                    op.setLastAnswer(resultSet.getString("lastAnswer"));
            }
        });
        return op;
    }
}
