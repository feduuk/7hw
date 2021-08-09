package com.pack;

import com.pack.animals.Species;
import com.pack.data.DAO;

import java.sql.SQLException;

public class FedyaSharov {

    public static void main(String[] args)  {

        DAO dao = null;
        try {
            dao = new DAO();
            CagesFactory cages = new CagesFactory(dao);
            cages.add(1, 100, Species.LION)
                .add(2, 100, Species.GIRAFFE)
                .add(3, 100, Species.SQUIRREL)
                .add(4, 100, Species.PENGUIN);
            Zoo zoo = new ZooImpl(dao, cages.getNumbersOfCages());
            new Parser().parse(zoo);
            dao.clean();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
