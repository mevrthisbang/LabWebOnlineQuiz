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
public class AnswerDTO implements Serializable{
    private String id, answerContent, question;
    private boolean isCorrectAnswer;

    public AnswerDTO() {
    }

    public AnswerDTO(String id, String answerContent) {
        this.id = id;
        this.answerContent = answerContent;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAnswerContent() {
        return answerContent;
    }

    public void setAnswerContent(String answerContent) {
        this.answerContent = answerContent;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public boolean isIsCorrectAnswer() {
        return isCorrectAnswer;
    }

    public void setIsCorrectAnswer(boolean isCorrectAnswer) {
        this.isCorrectAnswer = isCorrectAnswer;
    }
    
}
