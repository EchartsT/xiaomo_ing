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
    private final static String GET_KeyWord_SQL="SELECT * FROM keyword where keywordName = \"?\"";

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
        jdbcTemplate.query(GET_KeyWord_SQL, new Object[]{searchWord} ,new RowCallbackHandler() {
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
}
