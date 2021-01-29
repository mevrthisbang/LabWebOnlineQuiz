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
public class QuestionErrorObj implements Serializable{
    private String questionContentError, answer1Error, answer2Error, answer3Error, correctAnswerError;

    public QuestionErrorObj() {
    }

    public String getQuestionContentError() {
        return questionContentError;
    }

    public void setQuestionContentError(String questionContentError) {
        this.questionContentError = questionContentError;
    }

    public String getAnswer1Error() {
        return answer1Error;
    }

    public void setAnswer1Error(String answer1Error) {
        this.answer1Error = answer1Error;
    }

    public String getAnswer2Error() {
        return answer2Error;
    }

    public void setAnswer2Error(String answer2Error) {
        this.answer2Error = answer2Error;
    }

    public String getAnswer3Error() {
        return answer3Error;
    }

    public void setAnswer3Error(String answer3Error) {
        this.answer3Error = answer3Error;
    }

    public String getCorrectAnswerError() {
        return correctAnswerError;
    }

    public void setCorrectAnswerError(String correctAnswerError) {
        this.correctAnswerError = correctAnswerError;
    }
    
}
