package com.book.service;

import com.book.dao.ManagerDao;
import com.book.domain.Manager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ManagerService {
    ManagerDao managerDao;

    @Autowired
    public void setManagerDao(ManagerDao managerDao){this.managerDao=managerDao;}

    public ArrayList<Manager> managerList(){
        return managerDao.managerList();
    }
    public int deletemaList(String operatorId ){
        return managerDao.deletemaList(operatorId);
    }

    public ArrayList<Manager> matchMa(String searchWord){
        return managerDao.matchMA(searchWord);
    }

    public Manager searchMa(String managerId){
        return managerDao.getMatch(managerId);
    }

    public boolean editMa(Manager manager){        return managerDao.editManager(manager)>0;}

    public boolean addMa(Manager manager){        return managerDao.addManager(manager)>0;}

}
