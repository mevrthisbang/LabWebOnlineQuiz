/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phuchgt.dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import phuchgt.db.MyConnection;
import phuchgt.dto.SubjectDTO;

/**
 *
 * @author mevrthisbang
 */
public class SubjectDAO implements Serializable {

    private Connection conn;
    private PreparedStatement preStm;
    private ResultSet rs;

    private void closeConnection() throws Exception {
        if (rs != null) {
            rs.close();
        }
        if (preStm != null) {
            preStm.close();
        }
        if (conn != null) {
            conn.close();
        }
    }

    public List<SubjectDTO> getListSubjects() throws Exception {
        List<SubjectDTO> result = null;
        try {
            conn = MyConnection.getMyConnection();
            String sql = "Select id, name\n"
                    + "From SUBJECT";
            preStm = conn.prepareStatement(sql);
            rs = preStm.executeQuery();
            result = new ArrayList<>();
            while (rs.next()) {
                String id = rs.getString("id");
                String name = rs.getString("name");
                SubjectDTO subject = new SubjectDTO(id, name);
                result.add(subject);
            }
        } finally {
            closeConnection();
        }
        return result;
    }

    public int getSubjectNumberOfQuestionByID(String id) throws Exception {
        int result=0;
        try {
            conn=MyConnection.getMyConnection();
            String sql="Select numberOfQuestion\n"
                    + "From SUBJECT\n"
                    + "Where id=?";
            preStm=conn.prepareStatement(sql);
            preStm.setString(1, id);
            rs=preStm.executeQuery();
            if(rs.next()){
                result=rs.getInt("numberOfQuestion");
            }
        } finally {
            closeConnection();
        }
        return result;
    }
    public int getSubjectQuizTimeByID(String id) throws Exception {
        int result=0;
        try {
            conn=MyConnection.getMyConnection();
            String sql="Select quizTime\n"
                    + "From SUBJECT\n"
                    + "Where id=?";
            preStm=conn.prepareStatement(sql);
            preStm.setString(1, id);
            rs=preStm.executeQuery();
            if(rs.next()){
                result=rs.getInt("quizTime");
            }
        } finally {
            closeConnection();
        }
        return result;
    }
}
