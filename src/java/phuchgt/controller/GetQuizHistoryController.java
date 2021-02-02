/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phuchgt.controller;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import phuchgt.dao.QuizDAO;
import phuchgt.dao.QuizDetailDAO;
import phuchgt.dto.AccountDTO;
import phuchgt.dto.QuizDetailDTO;

/**
 *
 * @author mevrthisbang
 */
public class GetQuizHistoryController extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try {
            HttpSession session=request.getSession();
            AccountDTO loginUser=(AccountDTO) session.getAttribute("USER");
            String name=request.getParameter("txtName");
            if(name==null){
                name="";
            }
            QuizDetailDAO dao=new QuizDetailDAO();
            List<QuizDetailDTO> listQuizHistory=dao.getListQuizHistory(name, loginUser.getEmail());
            for (QuizDetailDTO quizDetailDTO : listQuizHistory) {
                QuizDAO quizDAO=new QuizDAO();
                request.setAttribute(quizDetailDTO.getQuizID(), quizDAO.getQuizDetailByID(quizDetailDTO.getQuizID()));
            }
            request.setAttribute("listQuizHistory", listQuizHistory);
        } catch (Exception e) {
            log("ERROR at GetQuizHistoryController: "+e.getMessage());
        }finally{
            request.getRequestDispatcher("quizHistory.jsp").forward(request, response);
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
