package edu.wctc.ssm.bookwebapp.controller;

import java.io.IOException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import edu.wctc.ssm.bookwebapp.model.Author;
import edu.wctc.ssm.bookwebapp.model.AuthorService;
import java.sql.SQLException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;

/**
 * This servlet takes care of the interaction between the View(interface) and
 * Model (data and calculations).
 *
 * @author Scott
 */
@WebServlet(name = "AuthorController", urlPatterns = {"/AuthorController"})
public class AuthorController extends HttpServlet {

    // db config init params from web.xml
    private String driverClass;
    private String url;
    private String username;
    private String password;
    private String table;
    private String colone;
    private String coltwo;
    private String primarykey;
    
    private static final String EDIT_DELETE_ACTION = "editDelete";

    @Inject
    AuthorService aus;
    
    private static final String EDIT_PAGE = "Edit.jsp";
    private static final String DEST_PAGE = "Authors.jsp";
    private static final String ACTION_PARAM = "action";

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     * @throws java.lang.ClassNotFoundException
     * @throws java.sql.SQLException
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        //try {
            configDbConnection();
            //String action = request.getParameter("image");
            String deleteId = request.getParameter("deleteid");
            String createName = request.getParameter("createname");
            String createDate = request.getParameter("createdate");
            String updateId = request.getParameter("updateid");
            String updateName = request.getParameter("updatename");
            String updateDate = request.getParameter("updatedate");
            String id = request.getParameter("authorId");
            String name = request.getParameter("authorName");
            String date = request.getParameter("authorDate");
            String editId = request.getParameter("editId");
            //String imageAction = request.getParameter(IMAGE_PARAM);
            String action = request.getParameter(ACTION_PARAM);
             
//            switch (action) {
//            case EDIT_DELETE_ACTION:
//                if (editId != null && "".equals(editId)) {
//                    Author author =aus.getAuthorById(editId);
//                    request.setAttribute("author", author);
//                }
//                break;
//        }

            //delete
            if (deleteId != null && !"".equals(deleteId)) {
                aus.deleteAuthorById(deleteId);
            }
            //create
            if (createName != null && !"".equals(createName) && createDate != null && !"".equals(createDate)) {
                aus.createOneAuthor(createName, createDate);
            } else if (createName != null && !"".equals(createName) && createDate == null || "".equals(createDate)) {
                aus.createOneAuthor(createName, new Date());
            }
            //update
            if (updateId != null && !"".equals(updateId) && updateName != null && !"".equals(updateName) && createDate != null && !"".equals(updateDate)) {
                aus.updateAuthor(updateId, updateName, updateDate);
            } else if (updateId != null && !"".equals(updateId) && updateName != null && !"".equals(updateName) && createDate == null || "".equals(updateDate)) {
                aus.updateAuthor(updateId, updateName, new Date());
            }
            //display table
            List<Author> authors = aus.getAuthorList();
            request.setAttribute("authors", authors);

//        } catch (ClassNotFoundException | SQLException ex) {
//            Logger.getLogger(AuthorController.class.getName()).log(Level.SEVERE, null, ex);
//        } finally {

        RequestDispatcher view = request.getRequestDispatcher(DEST_PAGE);
        view.forward(request, response);
                }
//    }

    //web.xml config
    private void configDbConnection() {
        aus.getDao().initDao(driverClass, url, username, password, table, colone, coltwo, primarykey);
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
        try {
            processRequest(request, response);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(AuthorController.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        try {
            processRequest(request, response);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(AuthorController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Book Web App made for Dist. Java";
    }// </editor-fold>
    //Override for web.xml

    @Override
    public void init() throws ServletException {
        driverClass = getServletContext().getInitParameter("db.driver.class");
        url = getServletContext().getInitParameter("db.url");
        username = getServletContext().getInitParameter("db.username");
        password = getServletContext().getInitParameter("db.password");
        table = getServletContext().getInitParameter("db.table");
        colone = getServletContext().getInitParameter("db.colone");
        coltwo = getServletContext().getInitParameter("db.coltwo");
        primarykey = getServletContext().getInitParameter("db.primarykey");

    }

}
