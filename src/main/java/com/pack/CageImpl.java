package com.pack;

public class CageImpl implements Cage {

    private int number;
    private double area;
    Condition condition;
    boolean settled;


    public CageImpl(int number, double area, Condition condition) {
        this.number = number;
        this.area = area;
        this.condition = condition;
    }

    @Override
    public int getNumber() {
        return number;
    }

    @Override
    public double getArea() {
        return area;
    }

    @Override
    public Condition getCondition() {
        return condition;
    }

    @Override
    public boolean isVacantCage() {
        return !settled;
    }

    @Override
    public void setCage(boolean set) {
        settled = set;
    }


}
