package com.pack;

import com.pack.animals.Species;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ConditionImpl implements Condition {
    List<Species> list;
    public ConditionImpl(Species... list){
        this.list = new ArrayList<>(Arrays.asList(list));
    }
    public ConditionImpl(List<Species> list){
        this.list = list;
    }
    @Override
    public List<Species> isAvailableFor() {
        return this.list;
    }
}
