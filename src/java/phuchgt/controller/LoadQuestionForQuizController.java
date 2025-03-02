/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phuchgt.controller;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
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
import phuchgt.dto.QuizAnswerObj;
import phuchgt.dto.QuizDetailDTO;

/**
 *
 * @author mevrthisbang
 */
public class LoadQuestionForQuizController extends HttpServlet {

    private static final String ERROR = "error.jsp";
    private static final String OKAY = "studentQuiz.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url=ERROR;
        try {
            HttpSession session = request.getSession();
            AccountDTO loginUser = (AccountDTO) session.getAttribute("USER");
            if (loginUser.getRole().equals("student")) {
                String subjectID = request.getParameter("subjectID");
                String quizID = request.getParameter("quizID");
                int quizTime = Integer.parseInt(request.getParameter("quizTime"));
                int numberOfQuestion = Integer.parseInt(request.getParameter("numberOfQuestion"));

                QuizAnswerObj studentAnswer = (QuizAnswerObj) session.getAttribute("STUDENTANSWER");

                if (studentAnswer == null) {
                    studentAnswer = new QuizAnswerObj(loginUser.getEmail());
                }
                QuizDetailDAO quizDetailDAO = new QuizDetailDAO();
                QuizDetailDTO quizDetail = quizDetailDAO.getQuizDetailById(quizID + "_" + loginUser.getEmail());
                Date timeEndQuiz = null;
                List<QuestionDTO> listQuestion = null;
                QuestionDAO questionDAO = new QuestionDAO();
                AnswerDAO answerDAO = new AnswerDAO();
                LinkedHashMap<QuestionDTO, List<AnswerDTO>> listQuestionWithAnswers = (LinkedHashMap<QuestionDTO, List<AnswerDTO>>) session.getAttribute("listQuestionQuiz");
                if (quizDetail == null) {

                    listQuestion = questionDAO.generateListQuestion(subjectID, numberOfQuestion);
                    listQuestionWithAnswers = new LinkedHashMap<>();
                    for (QuestionDTO questionDTO : listQuestion) {
                        listQuestionWithAnswers.put(questionDTO, answerDAO.listAnswerOfQuestion(questionDTO.getId()));
                    }
                    Date date = new Date();
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(date);
                    calendar.add(Calendar.MINUTE, quizTime);
                    calendar.add(Calendar.SECOND, 2);
                    timeEndQuiz = calendar.getTime();

                    quizDetail = new QuizDetailDTO();
                    quizDetail.setId(quizID + "_" + loginUser.getEmail());
                    quizDetail.setQuizID(quizID);
                    quizDetail.setStudentID(loginUser.getEmail());
                    quizDetail.setStartedAt(date);
                    quizDetail.setStatus("In Progress");
                    quizDetail.setEstimateFinishTime(timeEndQuiz);
                    if (quizDetailDAO.insertStudentQuiz(quizDetail, listQuestionWithAnswers)) {
                        listQuestionWithAnswers.clear();
                        listQuestion = questionDAO.getStuQuestionQuiz(quizDetail.getId());
                        for (QuestionDTO questionDTO : listQuestion) {
                            listQuestionWithAnswers.put(questionDTO, answerDAO.listAnswerOfStuQuestion(questionDTO.getId()));
                            studentAnswer.getStudentAnswer().put(questionDTO.getId(), new AnswerDTO());
                        }
                        quizDetailDAO.insertStuAnswer(studentAnswer.getStudentAnswer());
                    } else {
                        request.setAttribute("ERROR", "Insert quiz detail fail");
                    }
                } else {
                    if (listQuestionWithAnswers == null) {
                        listQuestionWithAnswers = new LinkedHashMap<>();
                        listQuestion = questionDAO.getStuQuestionQuiz(quizDetail.getId());
                        for (QuestionDTO questionDTO : listQuestion) {
                            listQuestionWithAnswers.put(questionDTO, answerDAO.listAnswerOfStuQuestion(questionDTO.getId()));
                            studentAnswer.getStudentAnswer().put(questionDTO.getId(), answerDAO.getStuAnswerByQuestionID(questionDTO.getId()));
                        }
                    }
                    timeEndQuiz = quizDetail.getEstimateFinishTime();
                }
                session.setAttribute("STUDENTANSWER", studentAnswer);
                session.setAttribute("STUDENTQUIZDETAIL", quizDetail);
                session.setAttribute("listQuestionQuiz", listQuestionWithAnswers);
                session.setAttribute("timeEndQuiz", timeEndQuiz);
                request.setAttribute("Question", (QuestionDTO) listQuestionWithAnswers.keySet().toArray()[0]);
                request.setAttribute("listAnswer", listQuestionWithAnswers.values().toArray()[0]);
                request.setAttribute("currentQuestion", 1);
                request.setAttribute("numberOfQuestion", listQuestionWithAnswers.size());
                url=OKAY;
            } else {
                request.setAttribute("ERROR", "You do not have permission to do this");
            }
        } catch (Exception e) {
            log("ERROR at LoadQuestionForQuizController: " + e.getMessage());
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
