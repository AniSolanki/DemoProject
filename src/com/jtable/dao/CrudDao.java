package com.jtable.dao;

import com.jtable.model.Country;
import com.jtable.model.State;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import com.jtable.model.User;
import com.jtable.model.UserIP;
import com.jtable.model.UserType;
import com.jtable.utility.DBUtility;

public class CrudDao {

    private Connection connection;

    public CrudDao() {
        connection = DBUtility.getConnection();
    }

    public void addUser(User user) {
        try {

            PreparedStatement preparedStatement = connection
                    .prepareStatement("insert into tblUser(userid,firstname,lastname,email,isActive,about,education,gender,usertypeid,req) values (?,?, ?, ?,?,?,?,?,?,NOW() )");
            // Parameters start with 1
            preparedStatement.setInt(1, user.getUserid());
            preparedStatement.setString(2, user.getFirstName());
            preparedStatement.setString(3, user.getLastName());
            preparedStatement.setString(4, user.getEmail());
            preparedStatement.setString(5, user.getIsActive());
            preparedStatement.setString(6, user.getAbout());
            preparedStatement.setString(7, user.getEducation());
            preparedStatement.setString(8, user.getGender());
            preparedStatement.setString(9, user.getUsertypeid());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void addIp(UserIP userip) {
        try {

            PreparedStatement preparedStatement = connection
                    .prepareStatement("insert into tbluserip(userid,ip,req) values (?,?,NOW())");
            // Parameters start with 1
            preparedStatement.setInt(1, userip.getUserid());
            preparedStatement.setString(2, userip.getIp());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteUser(int userId) {
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("delete from tblUser where userid=?");
            // Parameters start with 1
            preparedStatement.setInt(1, userId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void deleteIp(int id) {
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("delete from tbluserip where id=?");
            // Parameters start with 1
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateUser(User user) throws ParseException {
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("update tblUser set "
                    + "firstname=?,"
                    + "lastname=?,"
                    + "email=?,"
                    + "isActive=?,"
                    + "about=?,"
                    + "education=?,"
                    + "gender=?,"
                    + "usertypeid=?,"
                    + "req=NOW()"
                    + "where userid=?");
            // Parameters start with 1			
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getIsActive());
            preparedStatement.setString(5, user.getAbout());
            preparedStatement.setString(6, user.getEducation());
            preparedStatement.setString(7, user.getGender());
            preparedStatement.setString(8, user.getUsertypeid());
            preparedStatement.setInt(9, user.getUserid());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void updateIp(UserIP userip) throws ParseException {
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("update tbluserip set "
                    + "userid=?,"
                    + "ip=?,"
                    + "req=NOW()"
                    + "where id=?");
            // Parameters start with 1			
            preparedStatement.setInt(1, userip.getUserid());
            preparedStatement.setString(2, userip.getIp());
            preparedStatement.setInt(3, userip.getId());
            preparedStatement.executeUpdate();
            System.out.println(""+userip.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers(String firstName,String jtStartIndex,String jtPageSize,String jtSorting) {
        List<User> users = new ArrayList<User>();
        firstName=firstName.length()>0?firstName.toLowerCase():"";
         String query="select * from tblUser WHERE LOWER(firstName) LIKE '%"+firstName+"%' ORDER BY "+jtSorting+" LIMIT "+jtStartIndex+","+jtPageSize+"";
        try {
            Statement statement = connection.createStatement();
           
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                User user = new User();
                user.setUserid(rs.getInt("userid"));
                user.setFirstName(rs.getString("firstname"));
                user.setLastName(rs.getString("lastname"));
                user.setEmail(rs.getString("email"));
                user.setIsActive(rs.getString("isActive"));
                user.setAbout(rs.getString("about"));
                user.setEducation(rs.getString("education"));
                user.setGender(rs.getString("gender"));
                user.setUsertypeid(rs.getString("usertypeid"));
                user.setReq("" + rs.getDate("req"));
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(query);
        return users;
    }
    public List<UserIP> getUserIPs(int userid,String jtStartIndex,String jtPageSize,String jtSorting) {
        List<UserIP> usersip = new ArrayList<UserIP>();
         String query="select * from tbluserip WHERE userid="+userid+" ORDER BY "+jtSorting+" LIMIT "+jtStartIndex+","+jtPageSize+"";
        try {
            Statement statement = connection.createStatement();
           
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                UserIP userIP = new UserIP();
                userIP.setId(rs.getInt("id"));
                userIP.setUserid(rs.getInt("userid"));
                userIP.setIp(rs.getString("ip"));
                userIP.setReq("" + rs.getDate("req"));
                usersip.add(userIP);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(query);
        return usersip;
    }

    

    public int countAllUsers(String firstName) {
        firstName=firstName.length()>0?firstName.toLowerCase():"";
        int count = 0;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select count(*) from tblUser WHERE LOWER(firstName) like '%"+firstName+"%'");
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("count" + count);
        return count;

    }
    public int countAllUserIPs(int userid) {
        int count = 0;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select count(*) from tbluserip WHERE userid="+userid);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("count" + count);
        return count;

    }

    public List<UserType> getAllUsersType() {
        List<UserType> userType=new ArrayList<UserType>();
        String query="SELECT * from tblusertype";
        try {
            Statement statement = connection.createStatement();
           
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                UserType usertype = new UserType();
                usertype.setValue(rs.getInt("id"));
                usertype.setDisplayText(rs.getString("usertype"));
                userType.add(usertype);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(query);
        return userType;
    }

    public List<Country> getAllCountry(String usertypeid) {
        List<Country> country=new ArrayList<Country>();
        String query="SELECT * from country WHERE userid="+usertypeid;
        try {
            Statement statement = connection.createStatement();
           
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                Country country1 = new Country();
                country1.setValue(rs.getInt("id"));
                country1.setDisplayText(rs.getString("country"));
                country.add(country1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(query);
        return country;
    }

    public List<State> getAllState(String countryId) {
        List<State> state=new ArrayList<State>();
        String query="SELECT * from state WHERE cid="+countryId;
        try {
            Statement statement = connection.createStatement();
           
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                State state1 = new State();
                state1.setValue(rs.getInt("id"));
                state1.setDisplayText(rs.getString("state"));
                state.add(state1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(query);
        return state;
    }
}
