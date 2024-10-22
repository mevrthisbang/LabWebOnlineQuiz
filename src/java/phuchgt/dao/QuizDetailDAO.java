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
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import phuchgt.db.MyConnection;
import phuchgt.dto.AnswerDTO;
import phuchgt.dto.QuestionDTO;
import phuchgt.dto.QuizDetailDTO;

/**
 *
 * @author mevrthisbang
 */
public class QuizDetailDAO implements Serializable {

    private Connection conn;
    private PreparedStatement preStmDetail;
    private PreparedStatement preStmStuQuestion;
    private PreparedStatement preStmStuAnswer;
    private ResultSet rs;

    private void closeConnection() throws Exception {
        if (rs != null) {
            rs.close();
        }
        if (preStmStuAnswer != null) {
            preStmStuAnswer.close();
        }
        if (preStmStuQuestion != null) {
            preStmStuQuestion.close();
        }
        if (preStmDetail != null) {
            preStmDetail.close();
        }
        if (conn != null) {
            conn.close();
        }
    }

    public boolean insertStudentQuiz(QuizDetailDTO quizDetail, HashMap<QuestionDTO, List<AnswerDTO>> studentQuestionQuiz) throws Exception {
        boolean check = false;
        try {
            conn = MyConnection.getMyConnection();
            String sqlInsertDetail = "Insert Into STUDENTQUIZDETAIL(id, student, startedAt, estimateFinishTime, quiz, status)\n"
                    + "Values(?,?,?,?,?,?)";
            String sqlInsertStuQuestionQuiz = "Insert Into STUDENTQUIZQUESTION(id, quizTake, question, questionContent)\n"
                    + "Values(?,?,?,?)";
            String sqlInsertStuQuestionAnswer = "Insert Into STUDENTQUIZANSWER(id, question, answerContent, isCorrectAnswer)\n"
                    + "Values(?,?,?,?)";
            preStmDetail = conn.prepareStatement(sqlInsertDetail);
            preStmStuQuestion = conn.prepareStatement(sqlInsertStuQuestionQuiz);
            preStmStuAnswer = conn.prepareStatement(sqlInsertStuQuestionAnswer);
            conn.setAutoCommit(false);
            preStmDetail.setString(1, quizDetail.getId());
            preStmDetail.setString(2, quizDetail.getStudentID());
            preStmDetail.setTimestamp(3, new Timestamp(quizDetail.getStartedAt().getTime()));
            preStmDetail.setTimestamp(4, new Timestamp(quizDetail.getEstimateFinishTime().getTime()));
            preStmDetail.setString(5, quizDetail.getQuizID());
            preStmDetail.setString(6, quizDetail.getStatus());
            preStmDetail.executeUpdate();
            int count = 1;
            for (QuestionDTO question : studentQuestionQuiz.keySet()) {
                preStmStuQuestion.setString(1, quizDetail.getId() + "_" + count);
                preStmStuQuestion.setString(2, quizDetail.getId());
                preStmStuQuestion.setString(3, question.getId());
                preStmStuQuestion.setString(4, question.getQuestionContent());
                preStmStuQuestion.executeUpdate();
                int countAnswer = 1;
                for (AnswerDTO answer : studentQuestionQuiz.get(question)) {
                    preStmStuAnswer.setString(1, quizDetail.getId() + "_" + count + "_" + countAnswer);
                    preStmStuAnswer.setString(2, quizDetail.getId() + "_" + count);
                    preStmStuAnswer.setString(3, answer.getAnswerContent());
                    preStmStuAnswer.setBoolean(4, answer.isIsCorrectAnswer());
                    preStmStuAnswer.executeUpdate();
                    countAnswer++;
                }
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

    public boolean insertStuAnswer(HashMap<String, AnswerDTO> studentAnswer) throws Exception {
        boolean check = true;
        try {
            conn = MyConnection.getMyConnection();
            String sqlInsertStuAnswer = "Insert STUDENTANSWER(questionID, answerID, isCorrect)\n"
                    + "Values(?,?,?)";
            preStmStuAnswer = conn.prepareStatement(sqlInsertStuAnswer);
            for (String question : studentAnswer.keySet()) {
                preStmStuAnswer.setString(1, question);
                preStmStuAnswer.setString(2, studentAnswer.get(question).getId());
                preStmStuAnswer.setBoolean(3, studentAnswer.get(question).isIsCorrectAnswer());
                preStmStuAnswer.executeUpdate();
            }
            check = true;
        } finally {
            closeConnection();
        }
        return check;
    }

    public boolean updateStuAnswer(String question, AnswerDTO answer) throws Exception {
        boolean check = true;
        try {
            conn = MyConnection.getMyConnection();
            String sqlUpdateStuAnswer = "Update STUDENTANSWER\n"
                    + "Set answerID=?, isCorrect=?\n"
                    + "Where questionID=?";
            preStmStuAnswer = conn.prepareStatement(sqlUpdateStuAnswer);
            preStmStuAnswer.setString(1, answer.getId());
            preStmStuAnswer.setBoolean(2, answer.isIsCorrectAnswer());
            preStmStuAnswer.setString(3, question);
            check = preStmStuAnswer.executeUpdate() > 0;
        } finally {
            closeConnection();
        }
        return check;
    }

    public boolean updateQuizDetail(QuizDetailDTO quizDetail) throws Exception {
        boolean check = false;
        try {
            conn = MyConnection.getMyConnection();
            String sqlUpdateDetail = "Update STUDENTQUIZDETAIL\n"
                    + "Set score=?, numberOfCorrect=?, finishedAt=?, status='Completed'\n"
                    + "Where id=?";
            preStmDetail = conn.prepareStatement(sqlUpdateDetail);
            preStmDetail.setFloat(1, quizDetail.getScore());
            preStmDetail.setInt(2, quizDetail.getNumberOfCorrect());
            preStmDetail.setTimestamp(3, new Timestamp(quizDetail.getFinishedAt().getTime()));
            preStmDetail.setString(4, quizDetail.getId());
            check = preStmDetail.executeUpdate() > 0;
        } finally {
            closeConnection();
        }
        return check;
    }

    public List<QuizDetailDTO> getListQuizHistory(String name, String loginUser) throws Exception {
        List<QuizDetailDTO> result = null;
        QuizDetailDTO dto = null;
        String quizID;
        float score;
        int numberOfCorrect;
        try {
            conn = MyConnection.getMyConnection();
            String sql = "Select quiz, score, numberOfCorrect\n"
                    + "From STUDENTQUIZDETAIL\n"
                    + "Where student=? AND finishedAt<=GETDATE() AND quiz IN(Select id\n"
                    + "From QUIZ\n"
                    + "Where subject IN(Select id\n"
                    + "From SUBJECT\n"
                    + "Where name LIKE ?))";
            preStmDetail = conn.prepareStatement(sql);
            preStmDetail.setString(1, loginUser);
            preStmDetail.setString(2, "%" + name + "%");
            rs = preStmDetail.executeQuery();
            result = new ArrayList<>();
            while (rs.next()) {
                quizID = rs.getString("quiz");
                score = rs.getFloat("score");
                numberOfCorrect = rs.getInt("numberOfCorrect");
                dto = new QuizDetailDTO();
                dto.setQuizID(quizID);
                dto.setScore(score);
                dto.setNumberOfCorrect(numberOfCorrect);
                result.add(dto);
            }
        } finally {
            closeConnection();
        }
        return result;
    }

    public QuizDetailDTO getQuizDetailById(String id) throws Exception {
        QuizDetailDTO result = null;
        try {
            conn = MyConnection.getMyConnection();
            String sql = "Select estimateFinishTime, status, quiz, score, numberOfCorrect, startedAt, student\n"
                    + "From STUDENTQUIZDETAIL\n"
                    + "Where id=?";
            preStmDetail = conn.prepareStatement(sql);
            preStmDetail.setString(1, id);
            rs = preStmDetail.executeQuery();
            if (rs.next()) {
                Date estimateFinishTime = new Date(rs.getTimestamp("estimateFinishTime").getTime());
                String status = rs.getString("status");
                String quiz = rs.getString("quiz");
                String student = rs.getString("student");
                int score = rs.getInt("score");
                int numberOfCorrect = rs.getInt("numberOfCorrect");
                Date startedAt = new Date(rs.getTimestamp("startedAt").getTime());
                result = new QuizDetailDTO(id, quiz, student, score, numberOfCorrect);
                result.setEstimateFinishTime(estimateFinishTime);
                result.setStatus(status);
                result.setStartedAt(startedAt);
            }
        } finally {
            closeConnection();
        }
        return result;
    }
    public List<QuizDetailDTO> getIncompleteQuizDetailByStudent(String studentID) throws Exception {
        List<QuizDetailDTO> result = null;
        QuizDetailDTO dto=null;
        try {
            conn = MyConnection.getMyConnection();
            String sql = "Select id, quiz, score, numberOfCorrect, student\n"
                    + "From STUDENTQUIZDETAIL\n"
                    + "Where student=? AND status='In Progress' AND estimateFinishTime<=GETDATE()";
            preStmDetail = conn.prepareStatement(sql);
            preStmDetail.setString(1, studentID);
            rs = preStmDetail.executeQuery();
            result=new ArrayList<>();
            while (rs.next()) {
                String id=rs.getString("id");
                String quiz = rs.getString("quiz");
                String student=rs.getString("student");
                int score=rs.getInt("score");
                int numberOfCorrect=rs.getInt("numberOfCorrect");
                dto = new QuizDetailDTO(id, quiz, student, score, numberOfCorrect);
                result.add(dto);
            }
        } finally {
            closeConnection();
        }
        return result;
    }
}
