package com.book.service;


import com.book.dao.OperatorDao;
import com.book.dao.WeixinDAO;
import com.book.domain.AccessToken;
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

    public boolean updateOprecord2(Oprecord oprecord){
        return operatorDao.updateOprecord2(oprecord)>0;
    }

    public Oprecord queryOprecordById(String userId){
        return operatorDao.queryOprecordById(userId);
    }
    //微信
    private WeixinDAO weixinDAO;

    @Autowired
    public void setWeixinDAO(WeixinDAO weixinDAO){
        this.weixinDAO=weixinDAO;
    }

    //获取已有的accesstoken
    public AccessToken getAccess(){
        return weixinDAO.getAccess();
    }

    //更新已有的accesstoken
    public void updateAccess(AccessToken accessToken){
        weixinDAO.updateAccess(accessToken);
    }
    //删除所有记录
    public void deleteAccess(){
        weixinDAO.deleteAccess();
    }
}













