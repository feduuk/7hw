package com.pack.animals;

abstract public class AnimalImpl implements Animal {
    private String name;
    Species species;

    public AnimalImpl(String name, Species species) {
        this.name = name;
        this.species = species;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Species getSpecies() {
        return species;
    }
}
