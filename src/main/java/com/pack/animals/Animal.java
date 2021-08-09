package com.pack.animals;

/**
 * class provide information about Animal
 * each animal should has own class
 * @getName is used as primary key of Animal
 */
public interface Animal {
    String getName();
    Species getSpecies();
}