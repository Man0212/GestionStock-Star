package element;

import java.io.*;
import java.sql.Connection;

import connect.Connexion;
import jakarta.servlet.*;
import jakarta.servlet.http.*;


public class ControllerReport extends HttpServlet {
        public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            PrintWriter out= response.getWriter();
            String daty=request.getParameter("daty");
            Connection connection=Connexion.IConnex();
            try {

                try {
                    Elements[] listeE= Elements.ListElements(connection); 
    
                    String[] list=new String[listeE.length];
                    for (int i = 0; i < listeE.length; i++) {
                        list[i] = request.getParameter(listeE[i].getIDElement());
                       Inventaire I = new Inventaire(daty,listeE[i].getIDElement(),Float.parseFloat(list[i]));
                       I.Insert("Inventaire",I,connection);
                
                    }
                    connection.commit();
                } catch (Exception e){
                    connection.rollback();
                    out.println(e.getMessage());
                }finally{
                    connection.close();
                }
                response.sendRedirect("Home.jsp");
            } catch (Exception e) {
                out.println(e.getMessage());
            }
    }
}

