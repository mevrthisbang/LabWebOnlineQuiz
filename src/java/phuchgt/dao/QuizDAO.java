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
public class QuizDAO implements Serializable {

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

    public List<QuizDTO> getQuizBySubject(String subjectID, String student) throws Exception {
        List<QuizDTO> result = null;
        String id, name, description;
        int numberOfQuestion, quizTime;
        QuizDTO quiz = null;
        try {
            conn = MyConnection.getMyConnection();
            String sql = "Select id, name, numberOfQuestion, quizTime, description\n"
                    + "From QUIZ\n"
                    + "Where subject=? AND id NOT IN(Select quiz\n"
                    + "From STUDENTQUIZDETAIL\n"
                    + "Where student=? )";
            preStm = conn.prepareStatement(sql);
            preStm.setString(1, subjectID);
            preStm.setString(2, student);
            rs = preStm.executeQuery();
            result = new ArrayList<>();
            while (rs.next()) {
                id = rs.getString("id");
                name = rs.getString("name");
                description = rs.getString("description");
                numberOfQuestion = rs.getInt("numberOfQuestion");
                quizTime = rs.getInt("quizTime");
                quiz = new QuizDTO(id, name, description, subjectID, numberOfQuestion, quizTime);
                result.add(quiz);
            }
        } finally {
            closeConnection();
        }
        return result;
    }
    public QuizDTO getQuizByID(String quizID) throws Exception {
        QuizDTO result = null;
        String name, description, subject;
        int numberOfQuestion, quizTime;
        try {
            conn = MyConnection.getMyConnection();
            String sql = "Select name, numberOfQuestion, quizTime, description, subject\n"
                    + "From QUIZ\n"
                    + "Where id=?";
            preStm = conn.prepareStatement(sql);
            preStm.setString(1, quizID);
            rs = preStm.executeQuery();
            if (rs.next()) {
                name = rs.getString("name");
                description = rs.getString("description");
                numberOfQuestion = rs.getInt("numberOfQuestion");
                quizTime = rs.getInt("quizTime");
                subject=rs.getString("subjectID");
                result = new QuizDTO(quizID, name, description, subject, numberOfQuestion, quizTime);
            }
        } finally {
            closeConnection();
        }
        return result;
    }
}
