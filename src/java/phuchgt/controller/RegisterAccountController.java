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
import phuchgt.dao.AccountDAO;
import phuchgt.dto.AccountDTO;
import phuchgt.dto.AccountErrorObj;

/**
 *
 * @author mevrthisbang
 */
public class RegisterAccountController extends HttpServlet {

    private static final String ERROR = "error.jsp";
    private static final String SUCCESS = "loginForm.jsp";
    private static final String INVALID = "register.jsp";

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
        String url=ERROR;
        try {
            String fullname = request.getParameter("txtFullname");
            String email = request.getParameter("txtEmail");
            String password = request.getParameter("txtPassword");
            String confirm = request.getParameter("txtConfirm");
            boolean valid = true;
            AccountErrorObj errorObj = new AccountErrorObj();
            if (fullname.trim().isEmpty()) {
                errorObj.setFullNameError("Fullname is not supposed to be empty");
                valid = false;
            }
            if (email.trim().isEmpty()) {
                errorObj.setEmailError("Email is not supposed to be empty");
                valid = false;
            }
            if (!email.trim().isEmpty() && !email.trim().matches("\\w+@\\w+[.]\\w+([.]\\w+)?")) {
                errorObj.setEmailError("Email must be like abc@abc.abc");
                valid = false;
            }
            if (password.trim().isEmpty()) {
                errorObj.setPasswordError("Password is not supposed to be empty");
                valid = false;
            }
            if (!confirm.equals(password)) {
                errorObj.setConfirmError("Confirm must match new password");
                valid = false;
            }
            AccountDAO dao = new AccountDAO();
            if (dao.existedEmail(email)) {
                errorObj.setEmailError("Email is existed");
                valid = false;
            }
            if(valid){
                AccountDTO account=new AccountDTO(email, fullname, "student");
                account.setPassword(password);
                if(dao.insertNewAccount(account)){
                    url=SUCCESS;
                }else{
                    request.setAttribute("ERROR", "Register fail");
                }
            }else{
                url=INVALID;
                request.setAttribute("INVALID", errorObj);
            }
        } catch (Exception e) {
            log("ERROR at RegisterController: "+e.getMessage());
        }finally{
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
