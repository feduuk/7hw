package com.pack.data;


import com.pack.Cage;
import com.pack.CageImpl;
import com.pack.Condition;
import com.pack.ConditionImpl;
import com.pack.animals.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DAO {

    private String url = "jdbc:postgresql://localhost:5432/zoo";
    private String user = "postgres";
    private String password = "06032000";
    Connection con;

    public DAO() throws SQLException {
        this.con = DriverManager.getConnection(url, user, password);
        Statement statement = con.createStatement();
        String s1 = "DROP TABLE IF EXISTS logs;";
        statement.executeUpdate(s1);
        String s2 = "DROP TABLE IF EXISTS animals;";
        statement.executeUpdate(s2);
        String s3 = "DROP TABLE IF EXISTS cages;";
        statement.executeUpdate(s3);
        String str1 = """
                CREATE TABLE cages(
                number INT PRIMARY KEY,
                area DOUBLE PRECISION,
                condition VARCHAR(50),
                vacant BOOLEAN);""";
        statement.executeUpdate(str1);
        String str2 = """
                CREATE TABLE animals(
                name VARCHAR(50) PRIMARY KEY,
                species VARCHAR(50),
                cage INT,
                FOREIGN KEY(cage)
                	  REFERENCES cages(number)
                	        ON DELETE NO ACTION);""";
        statement.executeUpdate(str2);
        String str3 = """
                CREATE TABLE logs(
                name VARCHAR(50),
                enterDate TIMESTAMP,
                exitDate TIMESTAMP,
                PRIMARY KEY(enterDate));""";
        statement.executeUpdate(str3);
    }



    public String getLogs() throws SQLException {
        String sql = "SELECT * FROM logs;";
        Statement statement = con.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        StringBuilder sb = new StringBuilder();
        while(rs.next()){
            sb.append("name = " + rs.getString(1));
            sb.append(", enterDate = " + rs.getTimestamp(2));
            sb.append(", exitDate = " + rs.getTimestamp(3));
            sb.append("\n");
        }
        return sb.toString();

    }

    public Cage getCage(int number) throws SQLException, ClassNotFoundException {
        Cage cage;
        String sql = "SELECT area, condition, vacant FROM cages WHERE number = ?";
        PreparedStatement preparedStatement = con.prepareStatement(sql);
        preparedStatement.setInt(1, number);
        ResultSet rs = preparedStatement.executeQuery();
        rs.next();
        Condition condition = null;
        String str = rs.getString(2);
        String[] arr = str.split(" ");
        List<Species> species = new ArrayList<>();
        for(String c : arr){
            species.add(Species.valueOf(c));
        }
        condition = new ConditionImpl(species);
        cage = new CageImpl(number, rs.getDouble(1), condition);
        cage.setCage(!rs.getBoolean(3));
        return cage;
    }
    public Animal getAnimal(String name) throws SQLException, ClassNotFoundException {
        Animal animal = null;
        String sql = "SELECT species FROM animals WHERE name = ?";
        PreparedStatement preparedStatement = con.prepareStatement(sql);
        preparedStatement.setString(1, name);
        ResultSet rs = preparedStatement.executeQuery();
        rs.next();
        Species species = Species.valueOf(rs.getString(1));
        if(species == Species.LION) animal = new Lion(name);
        else if(species == Species.GIRAFFE) animal = new Giraffe(name);
        else if(species == Species.SQUIRREL) animal = new Squirrel(name);
        else if(species == Species.PENGUIN) animal = new Penguin(name);
        return animal;
    }

    public void setCage(Cage cage) throws SQLException {
        String sql = "INSERT INTO cages VALUES (?,?,?,?);";
        StringBuilder sb = new StringBuilder();
        for(Species species : cage.getCondition().isAvailableFor()){
            sb.append(species.toString());
            sb.append(" ");
        }
        PreparedStatement preparedStatement = con.prepareStatement(sql);
        preparedStatement.setInt(1, cage.getNumber());
        preparedStatement.setDouble(2, cage.getArea());
        preparedStatement.setString(3, sb.toString());
        preparedStatement.setBoolean(4, cage.isVacantCage());
        preparedStatement.executeUpdate();
    }
    public void setAnimal(Animal animal, int number) throws SQLException {
        String sql = "INSERT INTO animals VALUES (?,?,?);";
        PreparedStatement preparedStatement = con.prepareStatement(sql);
        preparedStatement.setString(1, animal.getName());
        preparedStatement.setString(2, animal.getSpecies().toString());
        preparedStatement.setInt(3, number);
        preparedStatement.executeUpdate();
    }
    public void setLogWithoutExitDate(Animal animal, java.util.Date enterDate) throws SQLException {
        String sql = "INSERT INTO logs(name, enterDate) VALUES (?,?);";
        PreparedStatement preparedStatement = con.prepareStatement(sql);
        preparedStatement.setString(1, animal.getName());
        preparedStatement.setTimestamp(2, new java.sql.Timestamp(enterDate.getTime()));
        preparedStatement.executeUpdate();
    }



    public void updateCageMakeVacant(int number) throws SQLException, ClassNotFoundException {
        Cage cage = getCage(number);
        String sql = "UPDATE cages SET vacant = ? WHERE number = ?;";
        PreparedStatement preparedStatement = con.prepareStatement(sql);
        preparedStatement.setBoolean(1, true);
        preparedStatement.setInt(2, cage.getNumber());
        preparedStatement.executeUpdate();
    }
    public void updateCageMakeOccupied(int number) throws SQLException, ClassNotFoundException {
        Cage cage = getCage(number);
        String sql = "UPDATE cages SET vacant = ? WHERE number = ?;";
        PreparedStatement preparedStatement = con.prepareStatement(sql);
        preparedStatement.setBoolean(1, false);
        preparedStatement.setInt(2, cage.getNumber());
        preparedStatement.executeUpdate();
    }
//    public void updateAnimalExitFromCage(Animal animal) throws SQLException {
//        String sql = "UPDATE animals SET cage = null WHERE name = ?;";
//        PreparedStatement preparedStatement = con.prepareStatement(sql);
//        preparedStatement.setString(1, animal.getName());
//        preparedStatement.executeUpdate();
//    }
public void updateAnimalDelete(Animal animal) throws SQLException {
    String sql = "DELETE FROM animals WHERE name = ?;";
    PreparedStatement preparedStatement = con.prepareStatement(sql);
    preparedStatement.setString(1, animal.getName());
    preparedStatement.executeUpdate();
}
    public void updateLogAddExitDate(Animal animal, java.util.Date exitDate) throws SQLException {
        String sql = "UPDATE logs SET exitDate = ? WHERE name = ?;";
        PreparedStatement preparedStatement = con.prepareStatement(sql);
        preparedStatement.setTimestamp(1, new java.sql.Timestamp(exitDate.getTime()));
        preparedStatement.setString(2, animal.getName());
        preparedStatement.executeUpdate();
    }




    public void clean() throws SQLException {
        Statement statement = con.createStatement();
        String str1 = "DROP TABLE IF EXISTS logs;";
        statement.executeUpdate(str1);
        String str2 = "DROP TABLE IF EXISTS animals;";
        statement.executeUpdate(str1);
        String str3 = "DROP TABLE IF EXISTS cages;";
        statement.executeUpdate(str1);
        con.close();
    }


}
