/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

import Clases.Conexion;
import Clases.Persona;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Diego
 */
@WebServlet(urlPatterns = {"/Comentar2"})
public class Comentar2 extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            String comentario = request.getParameter("coment_prod_usu_n");
            String like = request.getParameter("like_neg");
            String idn = request.getParameter("idn");
            /*Print de prueba
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Comentar</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Comentario: " + comentario + " Like: "+like+"</h1>");
            out.println("</body>");
            out.println("</html>");
            */
            
            if(like!=null){
                like="true";
            }else{
                like="false";
            }
            
            HttpSession misession= (HttpSession) request.getSession();
            
            Persona per= (Persona) misession.getAttribute("persona");
            String correoa = per.getPer_correo();
            
            
            
            Conexion con = new Conexion();
            Connection c;
            con.setCon();
            c=con.getCon();
            int idu = con.Pid(correoa);
            LocalDateTime hoy = LocalDateTime.now();
                
                    String year = Integer.toString(hoy.getYear());

                    String mes = Integer.toString(hoy.getMonthValue());

                    String dia = Integer.toString(hoy.getDayOfMonth());

                    if(hoy.getDayOfMonth()<10){
                        dia = "0"+dia;
                    }

                    if(hoy.getMonthValue()<10){
                        mes="0"+mes;
                    }

                         String fecha = year +"-"+ mes +"-"+ dia;
            String mensaje = con.Comentario(idn, idu, comentario, like, fecha);
            
            try {
                c.close();
            } catch (SQLException ex) {
                Logger.getLogger(EditarCuenta.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            request.setAttribute("mensaje", mensaje);
            request.setAttribute("neid", idn);       
                   RequestDispatcher rd = request.getRequestDispatcher("NegocioCV.jsp");
                   rd.forward(request, response);
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
