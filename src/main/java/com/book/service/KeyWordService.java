package com.book.service;

import com.book.dao.KeyWordDao;
import com.book.domain.KeyWord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class KeyWordService {
    private KeyWordDao keyWordDao;

    @Autowired
    private void setKeyWordDao(KeyWordDao keyWordDao){this.keyWordDao=keyWordDao;}
    public ArrayList<KeyWord> keywordList(){
        return keyWordDao.keywordList();
    }
    public int deletekeyword (String keywordId ){
        return keyWordDao.deletekeyword(keywordId);
    }

    public ArrayList<KeyWord> matchKeyword(String searchWord){

        return keyWordDao.matchKeyword(searchWord);
    }

    public KeyWord matchKeyword_Single(String searchWord){

        return keyWordDao.matchKeyword_Single(searchWord);
    }
    public boolean addKeyWord(KeyWord keyWord){
        return keyWordDao.addKeyWord(keyWord)>0;
    }
}
