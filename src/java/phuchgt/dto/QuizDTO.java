/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phuchgt.dto;

import java.io.Serializable;

/**
 *
 * @author mevrthisbang
 */
public class QuizDTO implements Serializable{
    private String id, name, description, subjectID;
    private int numberOfQuestion, quizTime;

    public QuizDTO() {
    }

    public QuizDTO(String id, String name, String description, String subjectID, int numberOfQuestion, int quizTime) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.subjectID = subjectID;
        this.numberOfQuestion = numberOfQuestion;
        this.quizTime = quizTime;
    }
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSubjectID() {
        return subjectID;
    }

    public void setSubjectID(String subjectID) {
        this.subjectID = subjectID;
    }

    public int getNumberOfQuestion() {
        return numberOfQuestion;
    }

    public void setNumberOfQuestion(int numberOfQuestion) {
        this.numberOfQuestion = numberOfQuestion;
    }

    public int getQuizTime() {
        return quizTime;
    }

    public void setQuizTime(int quizTime) {
        this.quizTime = quizTime;
    }
    
}
