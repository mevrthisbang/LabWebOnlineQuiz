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
    private String id, name, subjectID;

    public QuizDTO() {
    }

    public QuizDTO(String id, String name, String subjectID) {
        this.id = id;
        this.name = name;
        this.subjectID = subjectID;
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

    public String getSubjectID() {
        return subjectID;
    }

    
    public void setSubjectID(String subjectID) {
        this.subjectID = subjectID;
    }
    
}
