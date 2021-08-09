package com.pack;

import com.pack.animals.Animal;
import com.pack.data.DAO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class ZooImpl implements Zoo{

    final private List<Integer> numbersOfCages;
    final private List<String> namesOfAnimals;
    final private HashMap<String, Integer> animalsInCagesMap;
    DAO dao;


    public ZooImpl(DAO dao, List<Integer> numbersOfCages) {
        this.numbersOfCages = numbersOfCages;
        this.namesOfAnimals = new ArrayList<>();
        this.animalsInCagesMap = new HashMap<>();
        this.dao = dao;
    }

    @Override
    public void checkInAnimal(Animal animal) throws IllegalArgumentException, SQLException, ClassNotFoundException {
        for(String nameOfAnimal : namesOfAnimals){
            if(nameOfAnimal.equals(animal.getName()))
            {
                throw new IllegalArgumentException("Such name already in use!!!");
            }
        }

        for(int number : numbersOfCages){
            Cage cage = dao.getCage(number);
            for(int j = 0; j < cage.getCondition().isAvailableFor().size(); j++){

                if(cage.getCondition().isAvailableFor().get(j) == animal.getSpecies()){
                    if(cage.isVacantCage()){
                        cage.setCage(true);
                        dao.updateCageMakeOccupied(cage.getNumber());
                        namesOfAnimals.add(animal.getName());
                        animalsInCagesMap.put(animal.getName(), cage.getNumber());
                        dao.setAnimal(animal, cage.getNumber());
                        Date date = new Date();
                        dao.setLogWithoutExitDate(animal, date);
                        return;
                    }else{
                        throw new IllegalArgumentException("Cage for an animal of that species is occupied!!!");
                    }
                }
            }
        }
        throw new IllegalArgumentException("There is no cage for such species!!!");
    }

    @Override
    public void checkOutAnimal(Animal animal) throws IllegalArgumentException, SQLException, ClassNotFoundException {
        String name = animal.getName();
        if(animalsInCagesMap.containsKey(name)){
            int number = animalsInCagesMap.get(name);
            dao.updateCageMakeVacant(number);
            animalsInCagesMap.remove(name);
            dao.updateAnimalDelete(animal);
            namesOfAnimals.remove(name);
            Date date = new Date();
            dao.updateLogAddExitDate(animal, date);
        }else {
            throw new IllegalArgumentException("There's no such animal!!!");
        }
    }


    public String getHistory() throws SQLException {
        return dao.getLogs();
    }

    @Override
    public List<Integer> getNumbersOfCagesCages() {
        return numbersOfCages;
    }

    @Override
    public List<String> getNamesOfAnimals() {
        return namesOfAnimals;
    }

    @Override
    public HashMap<String, Integer> getAnimalsInCagesMap() {
        return animalsInCagesMap;
    }

    @Override
    public DAO getDao() {
        return dao;
    }
}
