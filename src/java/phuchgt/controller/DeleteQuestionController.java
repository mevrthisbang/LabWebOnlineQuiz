/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phuchgt.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import phuchgt.dao.QuestionDAO;
import phuchgt.dto.AccountDTO;

/**
 *
 * @author mevrthisbang
 */
public class DeleteQuestionController extends HttpServlet {

    private static final String ERROR = "error.jsp";
    private static final String SUCCESS = "LoadQuestionController";

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
            if (loginUser.getRole().equals("admin")) {
                String id = request.getParameter("id");
                QuestionDAO dao = new QuestionDAO();
                if (dao.delete(id)) {
                    url = SUCCESS;
                } else {
                    request.setAttribute("ERROR", "Delete failed");
                }
            } else {
                request.setAttribute("ERROR", "You do not have permission to do this");
            }
        } catch (Exception e) {
            log("ERROR at DeleteQuestion: " + e.getMessage());
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
