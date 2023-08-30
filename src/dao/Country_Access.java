package dao;

import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Country;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Country_Access extends Country
{
    public Country_Access(int Country_ID, String Country_Name)
    {
        super(Country_ID, Country_Name);
    }

    public static ObservableList<Country_Access> getCountries() throws SQLException
    {
        ObservableList<Country_Access> ObservableList_Countries = FXCollections.observableArrayList();
        String sql = "SELECT Country_ID, Country FROM countries";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);          //might need to use setpreparedStatement()
        ResultSet rs = ps.executeQuery();

        //System.out.println("Countries access, in getCountries()");

        while (rs.next())
        {
            int Country_ID = rs.getInt("Country_ID");
            String Country_Name = rs.getString("Country");
            Country_Access country = new Country_Access(Country_ID, Country_Name);
            ObservableList_Countries.add(country);


            //System.out.println(country);
        }
        return ObservableList_Countries;
    }
}
