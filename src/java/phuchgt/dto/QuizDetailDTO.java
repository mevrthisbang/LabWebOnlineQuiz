/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phuchgt.dto;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author mevrthisbang
 */
public class QuizDetailDTO implements Serializable{
    private String id, subjectID, studentID, status;
    private Date startedAt, finishedAt, estimateFinishTime;
    private float score;
    private int numberOfCorrect;

    public QuizDetailDTO() {
    }

    public QuizDetailDTO(String id, String subjectID, String studentID, Date startedAt, Date finishedAt, float score, int numberOfCorrect) {
        this.id = id;
        this.subjectID = subjectID;
        this.studentID = studentID;
        this.startedAt = startedAt;
        this.finishedAt = finishedAt;
        this.score = score;
        this.numberOfCorrect = numberOfCorrect;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSubjectID() {
        return subjectID;
    }

    public void setSubjectID(String subjectID) {
        this.subjectID = subjectID;
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public Date getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(Date startedAt) {
        this.startedAt = startedAt;
    }

    public Date getFinishedAt() {
        return finishedAt;
    }

    public void setFinishedAt(Date finishedAt) {
        this.finishedAt = finishedAt;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public int getNumberOfCorrect() {
        return numberOfCorrect;
    }

    public void setNumberOfCorrect(int numberOfCorrect) {
        this.numberOfCorrect = numberOfCorrect;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getEstimateFinishTime() {
        return estimateFinishTime;
    }

    public void setEstimateFinishTime(Date estimateFinishTime) {
        this.estimateFinishTime = estimateFinishTime;
    }
    
}
