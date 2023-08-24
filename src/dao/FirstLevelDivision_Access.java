package dao;

import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.First_Level_Division;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FirstLevelDivision_Access extends First_Level_Division
{
    public FirstLevelDivision_Access(int division_ID, String division_name, int country_ID)
    {
        super(division_ID, division_name, country_ID);
    }

    public static ObservableList<FirstLevelDivision_Access> getFirst_Level_Division() throws SQLException
    {
        //System.out.println("Entered getFLD");
        ObservableList<FirstLevelDivision_Access> ObservableList_First_Level_Divisions = FXCollections.observableArrayList();
        String sql = "SELECT * from first_level_divisions";
        //System.out.println("Just finished SQL pt 1");
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);          //might need to use setpreparedStatement()
        //System.out.println("Just finished SQL pt 2");
        ResultSet rs = ps.executeQuery();
        //System.out.println("Just finished SQL pt 3");

        while(rs.next())
        {
            //System.out.println("entered while");
            int division_ID = rs.getInt("Division_ID");
            String division_name = rs.getString("Division");
            int country_ID = rs.getInt("Country_ID");
            //System.out.println("got data in while");
            FirstLevelDivision_Access FirstLevelDivision = new FirstLevelDivision_Access(division_ID, division_name, country_ID);
            ObservableList_First_Level_Divisions.add(FirstLevelDivision);
            //System.out.println("finished while");
        }
        //System.out.println("About to return FLD Obslist");
        return ObservableList_First_Level_Divisions;
    }

}
