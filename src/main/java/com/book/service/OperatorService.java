package com.book.service;


import com.book.dao.OperatorDao;
import com.book.domain.Oprecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.spel.ast.Operator;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class OperatorService {
    private OperatorDao operatorDao;

    @Autowired
    public void setOpDao(OperatorDao operatorDao){
        this.operatorDao=operatorDao;
    }
    public ArrayList<Oprecord> opList(){
        return operatorDao.opList();
    }
    public int deleteopList(String operatorId ){
        return operatorDao.deleteopList(operatorId);
    }

    public ArrayList<Oprecord> matchOP(String searchWord){
        return operatorDao.matchOP(searchWord);
    }

    public boolean addOprecord(Oprecord oprecord){
        return operatorDao.addOprecord(oprecord)>0;
    }
    public boolean updateOprecord(Oprecord oprecord){
        return operatorDao.updateOprecord(oprecord)>0;
    }
}
