/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phuchgt.controller;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import phuchgt.dao.AnswerDAO;
import phuchgt.dao.QuestionDAO;
import phuchgt.dao.QuizDetailDAO;
import phuchgt.dao.SubjectDAO;
import phuchgt.dto.AccountDTO;
import phuchgt.dto.AnswerDTO;
import phuchgt.dto.QuestionDTO;
import phuchgt.dto.QuizDetailDTO;
import phuchgt.dto.SubjectDTO;

/**
 *
 * @author mevrthisbang
 */
public class LoadSubjectController extends HttpServlet {

    private static final String STUDENT = "student.jsp";
    private static final String ADMIN = "LoadQuestionController";
    private static final String ERROR = "loginForm.jsp";

    @Override
    public void init() throws ServletException {
        super.init(); //To change body of generated methods, choose Tools | Templates.
        ServletContext context = getServletContext();
        try {
            SubjectDAO dao = new SubjectDAO();
            List<SubjectDTO> listSubjects = dao.getListSubjects();
            context.setAttribute("listSubjects", listSubjects);
        } catch (Exception e) {
            log("ERROR at LoadSubjectController-init: " + e.getMessage());
        }
    }

    private void autoSubmitIncompleteQuiz(String student) throws Exception {
        QuizDetailDAO quizDetailDAO = new QuizDetailDAO();
        List<QuizDetailDTO> listInCompleteQuiz = quizDetailDAO.getIncompleteQuizDetailByStudent(student);
        for (int i = 0; i < listInCompleteQuiz.size(); i++) {
            QuestionDAO questionDAO = new QuestionDAO();
            List<QuestionDTO> listQuestion = questionDAO.getStuQuestionQuiz(listInCompleteQuiz.get(i).getId());
            float scorePerQuestion = (float) (10 * 1.0) / listQuestion.size();
            float score;
            int numberOfCorrect = 0;
            boolean isCorrectAnswer = false;
            for (QuestionDTO questionDTO : listQuestion) {
                AnswerDAO answerDAO = new AnswerDAO();
                AnswerDTO answer = answerDAO.getStuAnswerByQuestionID(questionDTO.getId());
                if (answer.getId() != null && answerDAO.isCorrectAnswer(answer.getId())) {
                    isCorrectAnswer = true;
                    numberOfCorrect++;
                }
                answer.setIsCorrectAnswer(isCorrectAnswer);
                quizDetailDAO.updateStuAnswer(questionDTO.getId(), answer);
            }
            score = scorePerQuestion * numberOfCorrect;
            Date date = new Date();
            listInCompleteQuiz.get(i).setFinishedAt(date);
            listInCompleteQuiz.get(i).setNumberOfCorrect(numberOfCorrect);
            listInCompleteQuiz.get(i).setScore(score);
            quizDetailDAO.updateQuizDetail(listInCompleteQuiz.get(i));
        }
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR;
        try {
            HttpSession session = request.getSession();
            AccountDTO loginUser = (AccountDTO) session.getAttribute("USER");
            if (loginUser.getRole().equals("student")) {
                autoSubmitIncompleteQuiz(loginUser.getEmail());
                url = STUDENT;
            } else if (loginUser.getRole().equals("admin")) {
                url = ADMIN;
            }
        } catch (Exception e) {
            log("ERROR at LoadSubjectController: " + e.getMessage());
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
