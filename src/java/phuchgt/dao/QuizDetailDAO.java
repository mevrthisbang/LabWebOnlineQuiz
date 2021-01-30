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
public class QuizDetailDAO implements Serializable{
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
    public boolean insertStudentQuiz(QuizDetailDTO quizDetail, HashMap<QuestionDTO, List<AnswerDTO>> studentQuestionQuiz) throws Exception{
        boolean check=false;
        try {
           conn=MyConnection.getMyConnection();
           String sqlInsertDetail="Insert Into STUDENTQUIZDETAIL(id, student, startedAt)\n"
                   + "Values(?,?,?)";
           String sqlInsertStuQuestionQuiz="Insert Into STUDENTQUIZQUESTION(id, quizTake, question, questionContent)\n"
                   + "Values(?,?,?,?)";
           String sqlInsertStuQuestionAnswer="Insert Into STUDENTQUIZANSWER(id, question, answerContent, isCorrectAnswer)\n"
                   + "Values(?,?,?,?)";
           preStmDetail=conn.prepareStatement(sqlInsertDetail);
           preStmStuQuestion=conn.prepareStatement(sqlInsertStuQuestionQuiz);
           preStmStuAnswer=conn.prepareStatement(sqlInsertStuQuestionAnswer);
           conn.setAutoCommit(false);
           preStmDetail.setString(1, quizDetail.getId());
           preStmDetail.setString(2, quizDetail.getStudentID());
           preStmDetail.setTimestamp(3, new Timestamp(quizDetail.getStartedAt().getTime()));
           preStmDetail.executeUpdate();
           int count=1;
           for(QuestionDTO question: studentQuestionQuiz.keySet()){
               preStmStuQuestion.setString(1, quizDetail.getId()+"_"+count);
               preStmStuQuestion.setString(2, quizDetail.getId());
               preStmStuQuestion.setString(3, question.getId());
               preStmStuQuestion.setString(4, question.getQuestionContent());
               preStmStuQuestion.executeUpdate();
               int countAnswer=1;
               for(AnswerDTO answer: studentQuestionQuiz.get(question)){
                   preStmStuAnswer.setString(1, quizDetail.getId()+"_"+count+"_"+countAnswer);
                   preStmStuAnswer.setString(2, quizDetail.getId()+"_"+count);
                   preStmStuAnswer.setString(3, answer.getAnswerContent());
                   preStmStuAnswer.setBoolean(4, answer.isIsCorrectAnswer());
                   preStmStuAnswer.executeUpdate();
                   countAnswer++;
               }
               count++;
           }
           conn.commit();
           conn.setAutoCommit(true);
           check=true;
        } finally{
            closeConnection();
        }
        return check;
    }
    public boolean updateQuizDetailAndInsertStuAnswer(QuizDetailDTO quizDetail, HashMap<String, AnswerDTO> studentAnswer) throws Exception{
        boolean check=true;
        try {
           conn=MyConnection.getMyConnection();
           String sqlUpdateDetail="Update STUDENTQUIZDETAIL\n"
                   + "Set quiz=?, score=?, numberOfCorrect=?, finishedAt=?\n"
                   + "Where id=?";
           String sqlInsertStuAnswer="Insert STUDENTANSWER(id, questionID, answerID, isCorrect)\n"
                   + "Values(?,?,?,?)";
           preStmDetail=conn.prepareStatement(sqlUpdateDetail);
           preStmStuAnswer=conn.prepareStatement(sqlInsertStuAnswer);
           conn.setAutoCommit(false);
           preStmDetail.setString(1, quizDetail.getSubjectID());
           preStmDetail.setFloat(2, quizDetail.getScore());
           preStmDetail.setInt(3, quizDetail.getNumberOfCorrect());
           preStmDetail.setTimestamp(4, new Timestamp(quizDetail.getFinishedAt().getTime()));
           preStmDetail.setString(5, quizDetail.getId());
           preStmDetail.executeUpdate();
           int count=1;
           for(String question: studentAnswer.keySet()){
               preStmStuAnswer.setString(1, question+"_"+count);
               preStmStuAnswer.setString(2, question);
               preStmStuAnswer.setString(3, studentAnswer.get(question).getId());
               preStmStuAnswer.setBoolean(4, studentAnswer.get(question).isIsCorrectAnswer());
               preStmStuAnswer.executeUpdate();
               count++;
           }
           conn.commit();
           conn.setAutoCommit(true);
           check=true;
        } finally{
            closeConnection();
        }
        return check;
    }
}
