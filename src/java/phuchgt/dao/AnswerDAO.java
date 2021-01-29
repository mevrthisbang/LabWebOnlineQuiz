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
import phuchgt.dto.AnswerDTO;

/**
 *
 * @author mevrthisbang
 */
public class AnswerDAO implements Serializable{
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
    public List<AnswerDTO> listAnswerOfQuestion(String questionID) throws Exception{
        List<AnswerDTO> result=null;
        String id, answerContent;
        boolean isCorrectAnswer;
        AnswerDTO answer=null;
        try {
            conn=MyConnection.getMyConnection();
            String sql="Select id, answerContent, isCorrectAnswer\n"
                    + "From ANSWER\n"
                    + "Where question=?\n"
                    + "Order by isCorrectAnswer";
            preStm=conn.prepareStatement(sql);
            preStm.setString(1, questionID);
            rs=preStm.executeQuery();
            result=new ArrayList<>();
            while(rs.next()){
                id=rs.getString("id");
                answerContent=rs.getString("answerContent");
                isCorrectAnswer=rs.getBoolean("isCorrectAnswer");
                answer=new AnswerDTO(id, answerContent);
                answer.setIsCorrectAnswer(isCorrectAnswer);
                result.add(answer);
            }
        } finally{
            closeConnection();
        }
        return result;
    }
    public List<AnswerDTO> listAnswerOfStuQuestion(String questionID) throws Exception{
        List<AnswerDTO> result=null;
        String id, answerContent;
        AnswerDTO answer=null;
        try {
            conn=MyConnection.getMyConnection();
            String sql="Select id, answerContent\n"
                    + "From STUDENTQUIZANSWER\n"
                    + "Where question=?\n"
                    + "ORDER BY NEWID()";
            preStm=conn.prepareStatement(sql);
            preStm.setString(1, questionID);
            rs=preStm.executeQuery();
            result=new ArrayList<>();
            while(rs.next()){
                id=rs.getString("id");
                answerContent=rs.getString("answerContent");
                answer=new AnswerDTO(id, answerContent);
                result.add(answer);
            }
        } finally{
            closeConnection();
        }
        return result;
    }
    public boolean isCorrectAnswer(String answerID) throws Exception{
        boolean check=false;
        try {
            conn=MyConnection.getMyConnection();
            String sql="Select isCorrectAnswer\n"
                    + "From STUDENTQUIZANSWER\n"
                    + "Where id=?";
            preStm=conn.prepareStatement(sql);
            preStm.setString(1, answerID);
            rs=preStm.executeQuery();
            if(rs.next()){
                check=rs.getBoolean("isCorrectAnswer");
            }
        } finally{
            closeConnection();
        }
        return check;
    }
}
