/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phuchgt.dao;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.xml.bind.DatatypeConverter;
import phuchgt.db.MyConnection;
import phuchgt.dto.AccountDTO;

/**
 *
 * @author mevrthisbang
 */
public class AccountDAO implements Serializable{
    private Connection conn;
    private PreparedStatement preStm;
    private ResultSet rs;
    private void closeConnection() throws Exception{
        if(rs!=null){
            rs.close();
        }
        if(preStm!=null){
            preStm.close();
        }
        if(conn!=null){
            conn.close();
        }
    }
    public static String toHexString(String input) throws Exception 
    {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hash=md.digest(input.getBytes(StandardCharsets.UTF_8)); 
        return DatatypeConverter.printHexBinary(hash).toLowerCase();
    } 
    public AccountDTO checkLogin(String email, String password) throws Exception{
        AccountDTO result=null;
        try {
            conn=MyConnection.getMyConnection();
            String sql="Select fullname, role\n"
                    + "From ACCOUNT\n"
                    + "Where email=? AND password=?";
            preStm=conn.prepareStatement(sql);
            preStm.setString(1, email);
            preStm.setString(2, toHexString(password));
            rs=preStm.executeQuery();
            if(rs.next()){
                String fullname=rs.getString("fullname");
                String role=rs.getString("role");
                result=new AccountDTO(email, fullname, role);
            }
        } finally{
            closeConnection();
        }
        return result;
    }
    public boolean existedEmail(String email) throws Exception {
        boolean check = false;
        try {
            conn = MyConnection.getMyConnection();
            String sql = "Select email From Account\n"
                    + "Where email=?";
            preStm = conn.prepareStatement(sql);
            preStm.setString(1, email);
            rs = preStm.executeQuery();
            if (rs.next()) {
                check = true;
            }
        } finally {
            closeConnection();
        }
        return check;
    }
    public boolean insertNewAccount(AccountDTO account) throws Exception {
        boolean check = false;
        try {
            conn = MyConnection.getMyConnection();
            String sql = "Insert Into Account(email, password, fullname, role)\n"
                    + "Values(?,?,?,?)";
            preStm = conn.prepareStatement(sql);
            preStm.setString(1, account.getEmail());
            preStm.setString(2, toHexString(account.getPassword()));
            preStm.setString(3, account.getFullname());
            preStm.setString(4, account.getRole());
            check = preStm.executeUpdate() > 0;
        } finally {
            closeConnection();
        }
        return check;
    }

}
