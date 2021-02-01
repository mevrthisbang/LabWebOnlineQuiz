/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phuchgt.controller;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import phuchgt.dao.AnswerDAO;
import phuchgt.dao.QuestionDAO;
import phuchgt.dao.QuizDetailDAO;
import phuchgt.dao.SubjectDAO;
import phuchgt.dto.QuestionDTO;
import phuchgt.dto.QuizAnswerObj;
import phuchgt.dto.QuizDetailDTO;

/**
 *
 * @author mevrthisbang
 */
public class SubmitQuizController extends HttpServlet {

    private static final String ERROR = "error.jsp";
    private static final String SUCCESS = "quizResult.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR;
        try {
            HttpSession session = request.getSession();
            QuizAnswerObj studentAnswer = (QuizAnswerObj) session.getAttribute("STUDENTANSWER");
            QuizDetailDTO quizDetail = (QuizDetailDTO) session.getAttribute("STUDENTQUIZDETAIL");
            AnswerDAO answerDAO = new AnswerDAO();
            int numberOfCorrect = 0;
            if (studentAnswer == null) {
                studentAnswer = new QuizAnswerObj(quizDetail.getStudentID());
                QuestionDAO questionDAO = new QuestionDAO();
                List<QuestionDTO> listQuestion = questionDAO.getStuQuestionQuiz(quizDetail.getId());
                for (QuestionDTO questionDTO : listQuestion) {
                    studentAnswer.getStudentAnswer().put(questionDTO.getId(), answerDAO.getStuAnswerByQuestionID(questionDTO.getId()));
                }
            }
            float scorePerQuestion = (float) (10 * 1.0) / studentAnswer.getStudentAnswer().size();
            float score;
            QuizDetailDAO dao = new QuizDetailDAO();
            for (String question : studentAnswer.getStudentAnswer().keySet()) {
                boolean isCorrectAnswer = false;
                if (studentAnswer.getStudentAnswer().get(question).getId() != null) {
                    if (answerDAO.isCorrectAnswer(studentAnswer.getStudentAnswer().get(question).getId())) {
                        isCorrectAnswer = true;
                        numberOfCorrect++;
                    }
                }
                studentAnswer.getStudentAnswer().get(question).setIsCorrectAnswer(isCorrectAnswer);
                dao.updateStuAnswer(question, studentAnswer.getStudentAnswer().get(question));
            }
            score = scorePerQuestion * numberOfCorrect;
            Date date = new Date();
            quizDetail.setFinishedAt(date);
            quizDetail.setNumberOfCorrect(numberOfCorrect);
            quizDetail.setScore(score);

            if (dao.updateQuizDetail(quizDetail)) {
                request.setAttribute("score", score);
                request.setAttribute("numberOfCorrect", numberOfCorrect);
//                SubjectDAO subjectDAO = new SubjectDAO();
//                request.setAttribute("quizSubject", subjectDAO.getSubjectQuizByID(quizDetail.getSubjectID()));
                session.removeAttribute("STUDENTANSWER");
                session.removeAttribute("STUDENTQUIZDETAIL");
                session.removeAttribute("listQuestionQuiz");
                session.removeAttribute("timeEndQuiz");
                url = SUCCESS;
            } else {
                request.setAttribute("ERROR", "There's some errors during submit quiz");
            }
        } catch (Exception e) {
            log("ERROR at SubmitController: " + e.getMessage());
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
        }

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
