package com.book.service;

import com.book.dao.ActiveDao;
import com.book.domain.ActiveRank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
@Service
public class ActiveService {
    private ActiveDao activeDao;

    @Autowired
    public void setacDao(ActiveDao activeDao){
        this.activeDao=activeDao;
    }
    public ArrayList<ActiveRank> acList(){
        return activeDao.acList();
    }
    public ArrayList<ActiveRank> acList_asc(){
        return activeDao.acList_asc();
    }
    public int deleteacList(String operatorId ){
        return activeDao.deleteacList(operatorId);
    }

    public ArrayList<ActiveRank> matchAC(String searchWord){
        return activeDao.matchac(searchWord);
    }

    public boolean addActiveItem(ActiveRank activeRank){
        return activeDao.addActiveItem(activeRank)>0;
    }
    public boolean updateActiveItem(ActiveRank activeRank){
        return activeDao.updateActiveItem(activeRank)>0;
    }
}
