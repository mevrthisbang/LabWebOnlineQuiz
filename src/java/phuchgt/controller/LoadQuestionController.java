/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phuchgt.controller;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import phuchgt.dao.AnswerDAO;
import phuchgt.dao.QuestionDAO;
import phuchgt.dto.AccountDTO;
import phuchgt.dto.AnswerDTO;
import phuchgt.dto.QuestionDTO;

/**
 *
 * @author mevrthisbang
 */
public class LoadQuestionController extends HttpServlet {
    private static final String ERROR="error.jsp";
    private static final String OKAY="admin.jsp";
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url=ERROR;
        try {
            HttpSession session = request.getSession();
            AccountDTO loginUser = (AccountDTO) session.getAttribute("USER");
            if (loginUser != null && loginUser.getRole().equals("admin")) {
                String content = request.getParameter("txtContent");
                String status = request.getParameter("cboStatus");
                String subject = request.getParameter("cboSubject");
                if (content == null) {
                    content = "";
                }
                if (status == null) {
                    status = "";
                }
                if (subject == null) {
                    subject = "";
                }
                QuestionDAO questionDAO = new QuestionDAO();
                int page = 1;
                int recordsPerPage = 3;
                if (request.getParameter("page") != null && !request.getParameter("page").isEmpty()) {
                    page = Integer.parseInt(request.getParameter("page"));
                }

                List<QuestionDTO> listQuestion = questionDAO.searchQuestion(content, status, subject, (page - 1) * recordsPerPage, recordsPerPage);
                LinkedHashMap<QuestionDTO, List<AnswerDTO>> listSearchQuestion = new LinkedHashMap<>();
                AnswerDAO answerDAO = new AnswerDAO();
                for (QuestionDTO questionDTO : listQuestion) {
                    listSearchQuestion.put(questionDTO, answerDAO.listAnswerOfQuestion(questionDTO.getId()));
                }
                int noOfRecords = questionDAO.numberOfRecordSearchQuestion(content, status, subject);
                int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
                request.setAttribute("noOfPages", noOfPages);
                request.setAttribute("currentPage", page);
                request.setAttribute("listQuestion", listSearchQuestion);
                url=OKAY;
            }else{
                request.setAttribute("ERROR", "You do not have permission to do this");
            }
        } catch (Exception e) {
            log("ERROR at LoadQuestionController: " + e.getMessage());
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
