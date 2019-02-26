package com.book.dao;

import com.book.domain.ActiveRank;
import com.book.domain.KeyWord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

@Repository
public class KeyWordDao {
    private JdbcTemplate jdbcTemplate;

    private final static String KeyWord_LIST_SQL="SELECT * FROM keyword ORDER BY keywordNum desc";
    private final static String KeyWord_DELETE_SQL="delete from keyword where keywordId = ? ";
    private final static String GET_KeyWord_SQL="SELECT * FROM keyword where keywordName =? or keywordId = ?";
    private final static String GET_RowNum_SQL="select count(*) from keyword where keywordName = ? ";
    private final static String ADD_KeyWord_SQL="INSERT INTO keyword (keywordName,keywordNum) VALUES(?,?)";
    private final static String UPDATE_KeyWord_SQL="UPDATE keyword SET keywordNum = ? WHERE keywordName = ?";

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public ArrayList<KeyWord> keywordList(){
        final ArrayList<KeyWord> list=new ArrayList<KeyWord>();
        jdbcTemplate.query(KeyWord_LIST_SQL, new RowCallbackHandler() {
            public void processRow(ResultSet resultSet) throws SQLException {
                resultSet.beforeFirst();
                while (resultSet.next()){
                    KeyWord kw=new KeyWord();
                    kw.setKeywordId(resultSet.getInt("keywordId"));
                    kw.setKeywordName(resultSet.getString("keywordName"));
                    kw.setKeywordNum(resultSet.getInt("keywordNum"));
                    list.add(kw);
                }
            }
        });
        return list;
    }

    public int deletekeyword(String operatorId){
        return jdbcTemplate.update(KeyWord_DELETE_SQL,operatorId);
    }


    public ArrayList<KeyWord> matchKeyword(String searchWord){
        final ArrayList<KeyWord> list=new ArrayList<KeyWord>();
        jdbcTemplate.query(GET_KeyWord_SQL, new Object[]{searchWord,searchWord} ,new RowCallbackHandler() {
            public void processRow(ResultSet resultSet) throws SQLException {
                resultSet.beforeFirst();
                while (resultSet.next()){
                    KeyWord kw=new KeyWord();
                    kw.setKeywordId(resultSet.getInt("keywordId"));
                    kw.setKeywordName(resultSet.getString("keywordName"));
                    kw.setKeywordNum(resultSet.getInt("keywordNum"));
                    list.add(kw);
                }
            }
        });
        return list;
    }

    public KeyWord matchKeyword_Single(String searchWord){
        KeyWord kw=new KeyWord();
        jdbcTemplate.query(GET_KeyWord_SQL, new Object[]{searchWord,searchWord} ,new RowCallbackHandler() {
            public void processRow(ResultSet resultSet) throws SQLException {
                kw.setKeywordId(resultSet.getInt("keywordId"));
                kw.setKeywordName(resultSet.getString("keywordName"));
                kw.setKeywordNum(resultSet.getInt("keywordNum"));
            }
        });
        return kw;
    }

    public int addKeyWord(KeyWord keyWord){
        String keywordName = keyWord.getKeywordName();
        int keywordNum = keyWord.getKeywordNum();
        int rowNum = jdbcTemplate.queryForObject(GET_RowNum_SQL, new Object[]{keywordName},Integer.class);
        if(rowNum == 0){
            return jdbcTemplate.update(ADD_KeyWord_SQL,new Object[]{keywordName,keywordNum});
        }else{
            return jdbcTemplate.update(UPDATE_KeyWord_SQL,new Object[]{keywordNum,keywordName});
        }
    }
}
