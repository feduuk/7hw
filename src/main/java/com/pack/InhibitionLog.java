package com.pack;

import com.pack.animals.Species;

import java.util.Date;

public class InhibitionLog {
    private Date checkInDate;
    private Date checkOutDate;
    private Species animalSpecies;
    private String animalName;

    public InhibitionLog(final Date checkInDate,
                         final Species animalSpecies,
                         final String animalName) {
        this.checkInDate = checkInDate;
        this.animalSpecies = animalSpecies;
        this.animalName = animalName;
    }

    public Date getCheckInDate() {
        return checkInDate;
    }

    public Date getCheckOutDate() {
        return checkOutDate;
    }

    public Species getAnimalSpecies() {
        return animalSpecies;
    }

    public String getAnimalName() {
        return animalName;
    }

    public void setCheckOutDate(Date checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    @Override
    public String toString() {
        return "{" +
                "checkInDate=" + checkInDate +
                ", checkOutDate=" + checkOutDate +
                ", animalSpecies=" + animalSpecies +
                ", animalName='" + animalName + "'" +
                '}';
    }
}