/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phuchgt.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import phuchgt.dao.QuestionDAO;
import phuchgt.dto.AccountDTO;
import phuchgt.dto.AnswerDTO;
import phuchgt.dto.QuestionDTO;
import phuchgt.dto.QuestionErrorObj;

/**
 *
 * @author mevrthisbang
 */
public class CreateQuestionController extends HttpServlet {

    private static final String ERROR = "error.jsp";
    private static final String SUCCESS = "LoadQuestionController";
    private static final String INVALID = "createForm.jsp";

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
    private AnswerDTO setupAnswer(String answerContent, boolean isCorrectAnswer) {
        AnswerDTO answer = new AnswerDTO();
        answer.setAnswerContent(answerContent);
        answer.setIsCorrectAnswer(isCorrectAnswer);
        return answer;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR;
        HttpSession session = request.getSession();
        AccountDTO loginUser = (AccountDTO) session.getAttribute("USER");
        try {
            if (loginUser.getRole().equals("admin")) {
                String questionContent = request.getParameter("txtQuestionContent");
                String answer1 = request.getParameter("txtAnswer1");
                String answer2 = request.getParameter("txtAnswer2");
                String answer3 = request.getParameter("txtAnswer3");
                String correctAnswer = request.getParameter("txtCorrectAnswer");
                String subject = request.getParameter("cboSubjects");
                //valid
                boolean valid = true;
                QuestionErrorObj errorObj = new QuestionErrorObj();
                if (questionContent.isEmpty()) {
                    valid = false;
                    errorObj.setQuestionContentError("Question Content is not supposed to be empty");
                }
                if (answer1.isEmpty()) {
                    valid = false;
                    errorObj.setAnswer1Error("Not supposed to be empty");
                }
                if (answer2.isEmpty()) {
                    valid = false;
                    errorObj.setAnswer2Error("Not supposed to be empty");
                }
                if (answer3.isEmpty()) {
                    valid = false;
                    errorObj.setAnswer3Error("Not supposed to be empty");
                }
                if (correctAnswer.isEmpty()) {
                    valid = false;
                    errorObj.setCorrectAnswerError("Not supposed to be empty");
                }
                if (valid) {
                    QuestionDAO questionDAO = new QuestionDAO();
                    String lastQuestionID = questionDAO.getLastQuestionID();
                    String questionID;
                    if (lastQuestionID == null) {
                        questionID = "QT_1";
                    } else {
                        int count = Integer.parseInt(lastQuestionID.split("_")[1]);
                        questionID = "QT_" + (count + 1);
                    }
                    QuestionDTO question = new QuestionDTO(questionID, questionContent);
                    question.setSubject(subject);
                    List<AnswerDTO> listAnswer = new ArrayList<>();
                    listAnswer.add(setupAnswer(answer1, false));
                    listAnswer.add(setupAnswer(answer2, false));
                    listAnswer.add(setupAnswer(answer3, false));
                    listAnswer.add(setupAnswer(correctAnswer, true));
                    if (questionDAO.insert(question, listAnswer)) {
                        url = SUCCESS;
                    } else {
                        request.setAttribute("ERROR", "Insert failed");
                    }
                } else {
                    url = INVALID;
                    request.setAttribute("INVALID", errorObj);
                }
            }else{
                request.setAttribute("ERROR", "You do not have permission to do this");
            }
        } catch (Exception e) {
            log("ERROR at CreateQuestionController: " + e.getMessage());
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
