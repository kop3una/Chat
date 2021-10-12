package myApp.dateBase;
import myApp.config.Const;
import myApp.user.User;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;

import static myApp.config.Config.*;

public class DateBaseHandler{
    Connection dbConnection;

    public  Connection getDbConnection() throws SQLException {
        String connectionStr = "jdbc:mysql://" + dbHost + ":" + dbPort + "/" +dbName;
        dbConnection = DriverManager.getConnection(connectionStr,dbUser,dbPass);
        return dbConnection;
    }

    public void signUpUser (User user) throws SQLException, NoSuchAlgorithmException {
        MessageDigest md5= MessageDigest.getInstance("MD5");
        byte [] passwordMD5 = md5.digest(user.getPassword().trim().getBytes());
        StringBuilder pass = new StringBuilder();
        for (byte b : passwordMD5){
            pass.append(String.format("%02X",b));
        }
        System.out.println(pass.toString());

        String insert = "INSERT INTO "+ Const.USER_TABLE + "(" + Const.USER_NAME + "," + Const.USER_SURNAME + "," +
                Const.USER_LOGIN + "," + Const.USER_PASSWORD + "," + Const.USER_LOCATION + "," + Const.USER_GENDER + ")" +
                "VALUES (?,?,?,?,?,?)";

        PreparedStatement prSt = getDbConnection().prepareStatement(insert);
        prSt.setString(1,user.getName());
        prSt.setString(2,user.getSurname());
        prSt.setString(3,user.getLogin());
        prSt.setString(4, pass.toString());
        prSt.setString(5,user.getLocation());
        prSt.setString(6,user.getGender());

        prSt.executeUpdate();
    }

    public ResultSet getUser (User user) throws SQLException, NoSuchAlgorithmException {
        ResultSet resSet;
        MessageDigest md5= MessageDigest.getInstance("MD5");
        byte [] passwordMD5 = md5.digest(user.getPassword().trim().getBytes());
        StringBuilder pass = new StringBuilder();
        for (byte b : passwordMD5){
            pass.append(String.format("%02X",b));
        }

        String select = "SELECT * FROM " + Const.USER_TABLE + " WHERE " +
                Const.USER_LOGIN + "=? AND " + Const.USER_PASSWORD + "=?";

        PreparedStatement prSt = getDbConnection().prepareStatement(select);
        prSt.setString(1,user.getLogin());
        prSt.setString(2, pass.toString());

        resSet = prSt.executeQuery();
        return resSet;
    }

    public ResultSet chekLogin (User user) throws SQLException {
        ResultSet resSet;
        String select = "SELECT * FROM " + Const.USER_TABLE + " WHERE " +
                Const.USER_LOGIN + "=?";
        PreparedStatement prSt = getDbConnection().prepareStatement(select);
        prSt.setString(1,user.getLogin());;
        resSet = prSt.executeQuery();
        return resSet;
    }

    public ResultSet getUsers () throws SQLException {
        ResultSet resSet;
        String select = "SELECT * FROM " + Const.USER_TABLE;
        PreparedStatement prSt = getDbConnection().prepareStatement(select);
        resSet = prSt.executeQuery();
        return resSet;
    }
}
