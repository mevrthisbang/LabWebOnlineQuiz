/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phuchgt.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import phuchgt.dao.QuizDetailDAO;
import phuchgt.dto.AccountDTO;
import phuchgt.dto.AnswerDTO;
import phuchgt.dto.QuestionDTO;
import phuchgt.dto.QuizAnswerObj;

/**
 *
 * @author mevrthisbang
 */
public class DispatchQuestionController extends HttpServlet {

    private static final String CONFIRM = "confirmSubmit.jsp";
    private static final String SUBMIT = "SubmitQuizController";
    private static final String DISPATCH = "studentQuiz.jsp";
    private static final String ERROR = "error.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

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
        response.setContentType("text/html;charset=UTF-8");
        request.setAttribute("ERROR", "You do not have permission to do this");
        request.getRequestDispatcher(ERROR).forward(request, response);
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
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR;
        HttpSession session = request.getSession();
        AccountDTO loginUser = (AccountDTO) session.getAttribute("USER");
        try {
            if (loginUser.getRole().equals("student")) {
                HashMap<QuestionDTO, List<AnswerDTO>> listQuestionWithAnswers = (HashMap<QuestionDTO, List<AnswerDTO>>) session.getAttribute("listQuestionQuiz");
                if (listQuestionWithAnswers != null) {
                    int currentQuestion = 0;
                    if (request.getParameter("currentQuestion")!=null&&!request.getParameter("currentQuestion").isEmpty()) {
                        currentQuestion = Integer.parseInt(request.getParameter("currentQuestion"));
                    }
                    String action = request.getParameter("action");
                    if (action != null) {
                        if (action.equals("Next")) {
                            ++currentQuestion;
                            url = DISPATCH;
                        } else if (action.equals("Previous")) {
                            --currentQuestion;
                            url = DISPATCH;
                        } else if (action.equals("Finish Attempt")) {
                            url = CONFIRM;
                        } else if (action.equals("submit")) {
                            url = SUBMIT;
                        } else {
                            request.setAttribute("ERROR", "Your action is invalid");
                        }
                    }
                    if (request.getParameter("dispatchRandom") != null && !request.getParameter("dispatchRandom").trim().isEmpty()) {
                        currentQuestion = Integer.parseInt(request.getParameter("dispatchRandom"));
                        url = DISPATCH;
                    }
                    QuizAnswerObj studentAnswer = (QuizAnswerObj) session.getAttribute("STUDENTANSWER");
                    if (studentAnswer == null) {
                        studentAnswer = new QuizAnswerObj(loginUser.getEmail());
                    }
                    String questionID = request.getParameter("questionID");
                    String answerID = request.getParameter("answerChoice");
                    QuizDetailDAO dao = new QuizDetailDAO();
                    if (studentAnswer.getStudentAnswer().containsKey(questionID)) {
                        studentAnswer.updateAnswer(questionID, answerID);
                        dao.updateStuAnswer(questionID, studentAnswer.getStudentAnswer().get(questionID));
                    }
                    session.setAttribute("STUDENTANSWER", studentAnswer);
                    request.setAttribute("Question", listQuestionWithAnswers.keySet().toArray()[currentQuestion - 1]);
                    request.setAttribute("currentQuestion", currentQuestion);
                    request.setAttribute("listAnswer", listQuestionWithAnswers.values().toArray()[currentQuestion - 1]);
                    request.setAttribute("numberOfQuestion", listQuestionWithAnswers.size());
                }
            } else {
                request.setAttribute("ERROR", "You do not have permission to do this");
            }
        } catch (Exception e) {
            log("ERROR at DispatchQuestionController: " + e.getMessage());
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
