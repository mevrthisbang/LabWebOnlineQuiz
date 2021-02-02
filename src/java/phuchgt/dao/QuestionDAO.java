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
import phuchgt.dto.QuestionDTO;

/**
 *
 * @author mevrthisbang
 */
public class QuestionDAO implements Serializable {
    
    private Connection conn;
    private PreparedStatement preStmQuestion;
    private PreparedStatement preStmAnswer;
    private ResultSet rs;
    
    private void closeConnection() throws Exception {
        if (rs != null) {
            rs.close();
        }
        if (preStmAnswer != null) {
            preStmAnswer.close();
        }
        if (preStmQuestion != null) {
            preStmQuestion.close();
        }
        if (conn != null) {
            conn.close();
        }
    }
    
    public List<QuestionDTO> generateListQuestion(String subjectID, int numberOfQuestion) throws Exception {
        List<QuestionDTO> result = null;
        String id, questionContent;
        QuestionDTO question = null;
        try {
            conn = MyConnection.getMyConnection();
            String sql = "Select Top (?) questionContent, id From QUESTION\n"
                    + "Where subject=? AND status='Active'\n"
                    + "ORDER BY NEWID()";
            preStmQuestion = conn.prepareStatement(sql);
            preStmQuestion.setInt(1, numberOfQuestion);
            preStmQuestion.setString(2, subjectID);
            rs = preStmQuestion.executeQuery();
            result = new ArrayList<>();
            while (rs.next()) {
                id = rs.getString("id");
                questionContent = rs.getString("questionContent");
                question = new QuestionDTO(id, questionContent);
                result.add(question);
            }
        } finally {
            closeConnection();
        }
        return result;
    }
    
    public List<QuestionDTO> getStuQuestionQuiz(String quizDetailID) throws Exception {
        List<QuestionDTO> result = null;
        String id, questionContent;
        QuestionDTO question = null;
        try {
            conn = MyConnection.getMyConnection();
            String sql = "Select questionContent, id From STUDENTQUIZQUESTION\n"
                    + "Where quizTake=?";
            preStmQuestion = conn.prepareStatement(sql);
            preStmQuestion.setString(1, quizDetailID);
            rs = preStmQuestion.executeQuery();
            result = new ArrayList<>();
            while (rs.next()) {
                id = rs.getString("id");
                questionContent = rs.getString("questionContent");
                question = new QuestionDTO(id, questionContent);
                result.add(question);
            }
        } finally {
            closeConnection();
        }
        return result;
    }
    
    public List<QuestionDTO> searchQuestion(String content, String status, String subject, int offset, int pageSize) throws Exception {
        List<QuestionDTO> result = null;
        String id, questionContent;
        QuestionDTO question = null;
        try {
            conn = MyConnection.getMyConnection();
            String sql = "Select questionContent, id, subject, status, createDate\n"
                    + "From QUESTION\n"
                    + "Where questionContent LIKE ?\n"
                    + "INTERSECT\n"
                    + "Select questionContent, id, subject, status, createDate\n"
                    + "From QUESTION\n"
                    + "Where status LIKE ?\n"
                    + "INTERSECT \n"
                    + "Select questionContent, id, subject, status, createDate\n"
                    + "From QUESTION\n"
                    + "Where subject LIKE ?\n"
                    + "ORDER BY createDate\n"
                    + "OFFSET ? ROWS\n"
                    + "FETCH NEXT ? ROWS ONLY";
            preStmQuestion = conn.prepareStatement(sql);
            preStmQuestion.setString(1, "%" + content + "%");
            if (status.isEmpty()) {
                preStmQuestion.setString(2, "%" + status + "%");
            } else {
                preStmQuestion.setString(2, status);
            }
            if (subject.isEmpty()) {
                preStmQuestion.setString(3, "%" + subject + "%");
            } else {
                preStmQuestion.setString(3, subject);
            }
            preStmQuestion.setInt(4, offset);
            preStmQuestion.setInt(5, pageSize);
            rs = preStmQuestion.executeQuery();
            result = new ArrayList<>();
            while (rs.next()) {
                id = rs.getString("id");
                questionContent = rs.getString("questionContent");
                subject = rs.getString("subject");
                status = rs.getString("status");
                question = new QuestionDTO(id, questionContent);
                question.setSubject(subject);
                question.setStatus(status);
                result.add(question);
            }
        } finally {
            closeConnection();
        }
        return result;
    }
    
    public int numberOfRecordSearchQuestion(String content, String status, String subject) throws Exception {
        int result = 0;
        try {
            conn = MyConnection.getMyConnection();
            String sql = "Select Count(*) as NoOfRecords\n"
                    + "From (\n"
                    + "Select questionContent, id, subject, status\n"
                    + "From QUESTION\n"
                    + "Where questionContent LIKE ?\n"
                    + "INTERSECT\n"
                    + "Select questionContent, id, subject, status\n"
                    + "From QUESTION\n"
                    + "Where status LIKE ?\n"
                    + "INTERSECT\n"
                    + "Select questionContent, id, subject, status\n"
                    + "From QUESTION\n"
                    + "Where subject LIKE ?\n"
                    + ") I";
            preStmQuestion = conn.prepareStatement(sql);
            preStmQuestion.setString(1, "%" + content + "%");
            if (status.isEmpty()) {
                preStmQuestion.setString(2, "%" + status + "%");
            } else {
                preStmQuestion.setString(2, status);
            }
            if (subject.isEmpty()) {
                preStmQuestion.setString(3, "%" + subject + "%");
            } else {
                preStmQuestion.setString(3, subject);
            }
            rs = preStmQuestion.executeQuery();
            if (rs.next()) {
                result = rs.getInt("NoOfRecords");
            }
        } finally {
            closeConnection();
        }
        return result;
    }
    
    public boolean delete(String id) throws Exception {
        boolean check = false;
        try {
            conn = MyConnection.getMyConnection();
            String sql = "Update QUESTION\n"
                    + "Set status='Deactive'\n"
                    + "Where id=?";
            preStmQuestion = conn.prepareStatement(sql);
            preStmQuestion.setString(1, id);
            check = preStmQuestion.executeUpdate() > 0;
        } finally {
            closeConnection();
        }
        return check;
    }
    
    public String getLastQuestionID() throws Exception {
        String result = null;
        try {
            conn = MyConnection.getMyConnection();
            String sql = "Select id\n"
                    + "From QUESTION\n"
                    + "Where createDate=(Select MAX(createDate)\n"
                    + "From QUESTION)";
            preStmQuestion = conn.prepareStatement(sql);
            rs = preStmQuestion.executeQuery();
            if (rs.next()) {
                result = rs.getString("id");
            }
        } finally {
            closeConnection();
        }
        return result;
    }
    
    public boolean insert(QuestionDTO question, List<AnswerDTO> listAnswer) throws Exception {
        boolean check = false;
        try {
            conn = MyConnection.getMyConnection();
            String sqlInsertQuestion = "Insert Into QUESTION(id, questionContent, subject, status)\n"
                    + "Values(?,?,?, 'Active')";
            String sqlInsertAnswer = "Insert Into ANSWER(id, answerContent, isCorrectAnswer, question)\n"
                    + "Values(?,?,?,?)";
            preStmQuestion = conn.prepareStatement(sqlInsertQuestion);
            preStmAnswer = conn.prepareStatement(sqlInsertAnswer);
            conn.setAutoCommit(false);
            preStmQuestion.setString(1, question.getId());
            preStmQuestion.setString(2, question.getQuestionContent());
            preStmQuestion.setString(3, question.getSubject());
            preStmQuestion.executeUpdate();
            int count = 1;
            for (AnswerDTO answerDTO : listAnswer) {
                preStmAnswer.setString(1, question.getId()+"_"+count);
                preStmAnswer.setString(2, answerDTO.getAnswerContent());
                preStmAnswer.setBoolean(3, answerDTO.isIsCorrectAnswer());
                preStmAnswer.setString(4, question.getId());
                preStmAnswer.executeUpdate();
                count++;
            }
            conn.commit();
            conn.setAutoCommit(true);
            check = true;
        } finally {
            closeConnection();
        }
        return check;
    }
    public QuestionDTO getQuestionByID(String id) throws Exception{
        QuestionDTO result=null;
        String questionContent, subject, status;
        try {
            conn=MyConnection.getMyConnection();
            String sql="Select questionContent, subject, status\n"
                    + "From QUESTION\n"
                    + "Where id=?";
            preStmQuestion=conn.prepareStatement(sql);
            preStmQuestion.setString(1, id);
            rs=preStmQuestion.executeQuery();
            if(rs.next()){
                questionContent=rs.getString("questionContent");
                subject=rs.getString("subject");
                status=rs.getString("status");
                result=new QuestionDTO(id, questionContent);
                result.setSubject(subject);
                result.setStatus(status);
            }
        } finally{
            closeConnection();
        }
        return result;
    }
    public boolean update(QuestionDTO question, List<AnswerDTO> listAnswer) throws Exception {
        boolean check = false;
        try {
            conn = MyConnection.getMyConnection();
            String sqlUpdateQuestion = "Update QUESTION\n"
                    + "Set questionContent=?, subject=?, status=?\n"
                    + "Where id=?";
            String sqlUpdateAnswer = "Update ANSWER\n"
                    + "Set answerContent=?, isCorrectAnswer=?\n"
                    + "Where id=?";
            preStmQuestion = conn.prepareStatement(sqlUpdateQuestion);
            preStmAnswer = conn.prepareStatement(sqlUpdateAnswer);
            conn.setAutoCommit(false);
            preStmQuestion.setString(1, question.getQuestionContent());
            preStmQuestion.setString(2, question.getSubject());
            preStmQuestion.setString(3, question.getStatus());
            preStmQuestion.setString(4, question.getId());
            preStmQuestion.executeUpdate();
            for (AnswerDTO answerDTO : listAnswer) {
                preStmAnswer.setString(1, answerDTO.getAnswerContent());
                preStmAnswer.setBoolean(2, answerDTO.isIsCorrectAnswer());
                preStmAnswer.setString(3, answerDTO.getId());
                preStmAnswer.executeUpdate();
            }
            conn.commit();
            conn.setAutoCommit(true);
            check = true;
        } finally {
            closeConnection();
        }
        return check;
    }
}
