package element;

import java.io.PrintWriter;
import java.sql.Connection;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import connect.Connexion;
import java.io.IOException;


public class Controller extends HttpServlet {
        public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            PrintWriter out= response.getWriter();
            out.println("non");
            try {
                Connection connection= Connexion.IConnex();
                String produit = request.getParameter("produit");
                float entre = Float.parseFloat( request.getParameter("entrer")) ;
                float sortie = Float.parseFloat(request.getParameter("sortie")) ;
                String daty = request.getParameter("date");
                float prix = Float.parseFloat(request.getParameter("prix")) ;

                response.setContentType("text/plain");
               
                GestionStockage G = new GestionStockage(daty, produit, entre,sortie,prix);
               
                out.println(G.getEntrer());
                out.println(G.getSortie());
                out.println(G.getPrix());
                out.println(G.getDaty());
                out.println(G.getIDElement());

                GestionStockage.Insert("GestionStockage",G,connection);
                response.sendRedirect("Home.jsp");
                connection.close();
            } catch (Exception e) {
                out.print(e.getMessage());
            }
        
    }
}

