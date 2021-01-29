/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phuchgt.dto;

import java.io.Serializable;
import java.util.HashMap;

/**
 *
 * @author mevrthisbang
 */
public class QuizAnswerObj implements Serializable {

    private HashMap<String, AnswerDTO> studentAnswer;
    private String student;
    
    public QuizAnswerObj(String student) {
        studentAnswer = new HashMap<>();
        this.student = student;
    }
    
    public HashMap<String, AnswerDTO> getStudentAnswer() {
        return studentAnswer;
    }
    
    public String getStudent() {
        return student;
    }
    
    public void setStudent(String student) {
        this.student = student;
    }

    public void addAnswer(String questionID, String answerID) throws Exception {
        AnswerDTO answer = new AnswerDTO();
        answer.setId(answerID);
        studentAnswer.put(questionID, answer);
    }

    public void updateAnswer(String questionID, String answerID) throws Exception {
        AnswerDTO answer = studentAnswer.get(questionID);
        answer.setId(answerID);
        studentAnswer.put(questionID, answer);
    }
    
}
