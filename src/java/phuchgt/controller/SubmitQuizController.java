/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phuchgt.controller;

import java.io.IOException;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import phuchgt.dao.AnswerDAO;
import phuchgt.dao.QuizDAO;
import phuchgt.dao.QuizDetailDAO;
import phuchgt.dto.QuizAnswerObj;
import phuchgt.dto.QuizDetailDTO;

/**
 *
 * @author mevrthisbang
 */
public class SubmitQuizController extends HttpServlet {
    private static final String ERROR="error.jsp";
    private static final String SUCCESS="quizResult.jsp";
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
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
        request.setAttribute("ERROR", "You do not have permission to do this");
        request.getRequestDispatcher("error.jsp").forward(request, response);
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
        String url=ERROR;
        try {
            HttpSession session = request.getSession();
            QuizAnswerObj studentAnswer = (QuizAnswerObj) session.getAttribute("STUDENTANSWER");
            QuizDetailDTO quizDetail = (QuizDetailDTO) session.getAttribute("STUDENTQUIZDETAIL");
            float scorePerQuestion = (float) (10 * 1.0) / studentAnswer.getStudentAnswer().size();
            float score;
            AnswerDAO answerDAO = new AnswerDAO();
            int numberOfCorrect = 0;
            for (String question : studentAnswer.getStudentAnswer().keySet()) {
                boolean isCorrectAnswer = false;
                if (studentAnswer.getStudentAnswer().get(question).getId() != null) {
                    if (answerDAO.isCorrectAnswer(studentAnswer.getStudentAnswer().get(question).getId())) {
                        isCorrectAnswer = true;
                        numberOfCorrect++;
                    }
                }
                studentAnswer.getStudentAnswer().get(question).setIsCorrectAnswer(isCorrectAnswer);
            }
            score = scorePerQuestion * numberOfCorrect;
            Date date = new Date();
            quizDetail.setFinishedAt(date);
            quizDetail.setNumberOfCorrect(numberOfCorrect);
            quizDetail.setScore(score);
            QuizDetailDAO dao = new QuizDetailDAO();
            if (dao.updateQuizDetailAndInsertStuAnswer(quizDetail, studentAnswer.getStudentAnswer())) {
                request.setAttribute("score", score);
                request.setAttribute("numberOfCorrect", numberOfCorrect);
                QuizDAO quizDAO=new QuizDAO();
                request.setAttribute("QUIZ", quizDAO.getQuizByID(quizDetail.getQuizID()));
                session.removeAttribute("STUDENTANSWER");
                session.removeAttribute("STUDENTQUIZDETAIL");
                session.removeAttribute("listQuestionQuiz");
                url=SUCCESS;
            } else {
                request.setAttribute("ERROR", "There's some errors during submit quiz");
            }
        } catch (Exception e) {
            log("ERROR at SubmitController: " + e.getMessage());
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
        }
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
