package com.pack;

import com.pack.animals.Animal;
import com.pack.data.DAO;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

/**
 * entity keep state of Zoo and provide service desk functionality
 * @checkInAnimal method add Animal to Zoo
 * should throw exception when Zoo can't provide place
 * @checkOutAnimal method remove Animal from Zoo
 * @getHistory - method show log of check in and check out
 */
public interface Zoo {

    void checkInAnimal(Animal animal) throws IllegalArgumentException, SQLException, ClassNotFoundException;
    void checkOutAnimal(Animal animal) throws IllegalArgumentException, SQLException, ClassNotFoundException;
    String getHistory() throws SQLException;
    List<Integer> getNumbersOfCagesCages();
    List<String> getNamesOfAnimals();
    HashMap<String, Integer> getAnimalsInCagesMap();
    DAO getDao();
}