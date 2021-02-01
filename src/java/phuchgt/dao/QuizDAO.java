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
import phuchgt.dto.QuizDTO;

/**
 *
 * @author mevrthisbang
 */
public class QuizDAO implements Serializable{
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
    
    public List<QuizDTO> getListQuizBySubjectId(String subjectID) throws Exception{
        List<QuizDTO> result=null;
        String id, name;
        QuizDTO dto=null;
        try {
            conn=MyConnection.getMyConnection();
            String sql="Select id, quizName\n"
                    + "From QUIZ\n"
                    + "Where subject=?";
            preStm=conn.prepareStatement(sql);
            preStm.setString(1, subjectID);
            rs=preStm.executeQuery();
            result=new ArrayList<>();
            while(rs.next()){
                id=rs.getString("id");
                name=rs.getString("quizName");
                dto=new QuizDTO(id, name, subjectID);
                result.add(dto);
            }
        } finally{
            closeConnection();
        }
        return result;
    }
    public QuizDTO getQuizDetailByID(String id) throws Exception{
        QuizDTO result=null;
        String subjectID, name;
        try {
            conn=MyConnection.getMyConnection();
            String sql="Select subject, quizName\n"
                    + "From QUIZ\n"
                    + "Where id=?";
            preStm=conn.prepareStatement(sql);
            preStm.setString(1, id);
            rs=preStm.executeQuery();
            if(rs.next()){
                subjectID=rs.getString("subject");
                name=rs.getString("quizName");
                result=new QuizDTO(id, name, subjectID);
            }
        } finally{
            closeConnection();
        }
        return result;
    }
}
