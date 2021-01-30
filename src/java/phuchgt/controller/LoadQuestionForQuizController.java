/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phuchgt.controller;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import phuchgt.dao.AnswerDAO;
import phuchgt.dao.QuestionDAO;
import phuchgt.dao.QuizDetailDAO;
import phuchgt.dto.AccountDTO;
import phuchgt.dto.AnswerDTO;
import phuchgt.dto.QuestionDTO;
import phuchgt.dto.QuizDetailDTO;

/**
 *
 * @author mevrthisbang
 */
public class LoadQuestionForQuizController extends HttpServlet {

    private static final String ERROR = "error.jsp";
    private static final String SUCCESS = "studentQuiz.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try {
            HttpSession session = request.getSession();
            HashMap<QuestionDTO, List<AnswerDTO>> listQuestionWithAnswers = (HashMap<QuestionDTO, List<AnswerDTO>>) session.getAttribute("listQuestionQuiz");
            if (listQuestionWithAnswers == null) {
                String subjectID = request.getParameter("subjectID");
                int quizTime = Integer.parseInt(request.getParameter("quizTime"));
                int numberOfQuestion = Integer.parseInt(request.getParameter("numberOfQuestion"));
                QuestionDAO questionDAO = new QuestionDAO();
                List<QuestionDTO> listQuestion = questionDAO.generateListQuestion(subjectID, numberOfQuestion);
                AnswerDAO answerDAO = new AnswerDAO();
                listQuestionWithAnswers = new HashMap<>();
                for (QuestionDTO questionDTO : listQuestion) {
                    listQuestionWithAnswers.put(questionDTO, answerDAO.listAnswerOfQuestion(questionDTO.getId()));
                }
                Date date = new Date();
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                calendar.add(Calendar.MINUTE, quizTime);
                calendar.add(Calendar.SECOND, 2);
                Date timeEndQuiz = calendar.getTime();
                AccountDTO loginUser = (AccountDTO) session.getAttribute("USER");
                QuizDetailDTO quizDetail = new QuizDetailDTO();
                quizDetail.setId(subjectID + "_" + loginUser.getEmail());
                quizDetail.setSubjectID(subjectID);
                quizDetail.setStudentID(loginUser.getEmail());
                quizDetail.setStartedAt(date);
                QuizDetailDAO quizDetailDAO = new QuizDetailDAO();
                if (quizDetailDAO.insertStudentQuiz(quizDetail, listQuestionWithAnswers)) {
                    listQuestionWithAnswers.clear();
                    listQuestion = questionDAO.getStuQuestionQuiz(quizDetail.getId());
                    for (QuestionDTO questionDTO : listQuestion) {
                        listQuestionWithAnswers.put(questionDTO, answerDAO.listAnswerOfStuQuestion(questionDTO.getId()));
                    }
                    session.setAttribute("STUDENTQUIZDETAIL", quizDetail);
                    session.setAttribute("listQuestionQuiz", listQuestionWithAnswers);
                    session.setAttribute("timeEndQuiz", timeEndQuiz);
                }
            }
            request.setAttribute("Question", (QuestionDTO) listQuestionWithAnswers.keySet().toArray()[0]);
            request.setAttribute("listAnswer", listQuestionWithAnswers.values().toArray()[0]);
            request.setAttribute("currentQuestion", 1);
            request.setAttribute("numberOfQuestion", listQuestionWithAnswers.size());
        } catch (Exception e) {
            log("ERROR at LoadQuestionForQuizController: " + e.getMessage());
        } finally {
            request.getRequestDispatcher("studentQuiz.jsp").forward(request, response);
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
