package com.pack;

import com.pack.animals.Species;
import com.pack.data.DAO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CagesFactory {

    List<Integer> numbersOfCages;
    DAO dao;
    CagesFactory(DAO dao){
        numbersOfCages = new ArrayList<>();
        this.dao = dao;
    }
    public CagesFactory add(int number, int area, Species species){
        Cage cage = new CageImpl(number, area, new ConditionImpl(species));
        numbersOfCages.add(number);
        try {
            dao.setCage(cage);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return this;
    }
    public List<Integer> getNumbersOfCages(){
        return numbersOfCages;
    }
}
