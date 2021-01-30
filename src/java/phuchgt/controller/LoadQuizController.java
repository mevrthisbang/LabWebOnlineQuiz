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
import phuchgt.dao.QuizDetailDAO;
import phuchgt.dao.SubjectDAO;
import phuchgt.dto.AccountDTO;
import phuchgt.dto.QuizDetailDTO;
import phuchgt.dto.SubjectDTO;

/**
 *
 * @author mevrthisbang
 */
public class LoadQuizController extends HttpServlet {
    private static final String NORMAL="quizSubject";
    private static final String AUTOSUBMIT="SubmitQuizController";
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url=NORMAL;
        try {
            HttpSession session = request.getSession();
            AccountDTO loginUser = (AccountDTO) session.getAttribute("USER");
            String subjectID = request.getParameter("subjectID");
            SubjectDAO dao = new SubjectDAO();
            SubjectDTO quizSubject = dao.getSubjectQuizByID(subjectID);
            QuizDetailDAO quizDetailDAO = new QuizDetailDAO();
            QuizDetailDTO quizDetail = quizDetailDAO.getQuizDetailById(subjectID + "_" + loginUser.getEmail());
            if (quizDetail != null) {
                if (quizDetail.getEstimateFinishTime().after(new Date())) {
                    if (quizDetail.getStatus() != null && quizDetail.getStatus().equals("Completed")) {
                        request.setAttribute("Status", quizDetail.getStatus());
                    } else {
                        quizDetail.setId(subjectID + "_" + loginUser.getEmail());
                        session.setAttribute("STUDENTQUIZDETAIL", quizDetail);
                    }
                }else{
                    url=AUTOSUBMIT;
                }
            }
            request.setAttribute("quizSubject", quizSubject);
            
        } catch (Exception e) {
            log("ERROR at LoadQuizController: " + e.getMessage());
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
