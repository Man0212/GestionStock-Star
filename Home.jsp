
<%@ page import = "element.*" %>
<%@ page import = "element.Elements" %>
<%@ page import = "java.sql.Connection" %>
<%@ page import = "connect.Connexion" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="style.css">
    <title>Document</title>
</head>
<body>

    
    <%try {
        Connection connection=Connexion.IConnex();
        Elements[] listeE= Elements.ListElements(connection); 
        EtatStock[] listeES =EtatStock.ListEtatStock(connection);

        EtatStock[] listeOPT =EtatStock.ListOptimise(connection);
        Inventaire[] LI= Inventaire.ListInventaire(connection);
        
        String value=request.getParameter("daty");

        if(request.getParameter("daty")!=null){
            listeES =EtatStock.ListDate(connection,value);
        }

        if(listeES.length==0){
            listeES =EtatStock.ListEtatStock(connection);
        }

    %>



    <div class="container">
        <div class="insertion">
            <div class="espace"></div>
            <form action="Controller" method="get">
                date<input type="date" name="date"> 
                produit <select name="produit" id="">
                         <%  for (int i = 0; i < listeE.length; i++) { %>
                        <option value= <%= listeE[i].getIDElement()  %> > <%= listeE[i].getName() %></option>
                        <%  } %>
                    </select>
                Entre/Sortie 
                    <input type="number" name="entrer">
                    <input type="number" name="sortie">
                prix<input type="number" name="prix">
               <input type="submit" value="valider" class="btn">
            </form>
        </div>



        <div class="choix">
            <div class="espace"></div>
           <form action="Home.jsp" method="get">
                Date <input type="date" name="daty">
                <input type="submit" value="valider" class="btn">
            </form>
        </div>
    


    <div class="container1">
        
        <div class="OPT">
            <div  class="title">
                <center>Nouveau Report</center>
            </div>
            <br>
                <form action="ControllerReport" method="get">
                    Date<input type="date" name="daty">
                    <%  for (int i = 0; i < listeE.length; i++) { %>
                        <p><%= listeE[i].getName() %> <input class="inputOPT" type="number" name= <%= listeE[i].getIDElement() %>  > </p>
                    <%  } %>
                    <input type="submit" value="inventaire" class="optim">
                </form>
        </div>



        <div class="list">
                
                <div  class="title">
                   <center> Mouvement de stock optimise</center>
                </div>
                <br>
                <table class="tab" border="1">
                    <tr class="titre">
                        <td>ID</td>
                        <td>Nom du Composant</td>
                        <td>Date Dernier Report</td>
                        <td style="background-color:rgb(50, 197, 50) ;">Report</td>
                        <td>Entree</td>
                        <td>Sortie</td>
                        <td>reste</td>
                    </tr>
                <% for (int i = 0; i < listeOPT.length; i++) {%>
                    <tr>
                        <td><%= listeOPT[i].getIDElement() %></td>
                        <td><%= Elements.toElements(listeE,listeOPT[i].getIDElement()).getName() %></td>  
                        <td><%= Inventaire.toInventaire(LI,listeOPT[i].getIDElement()).getDaty() %></td>    
                        <td><%= Inventaire.toInventaire(LI,listeOPT[i].getIDElement()).getRest() %></td>               
                        <td><%= listeOPT[i].getEntrer() %></td>
                        <td><%= listeOPT[i].getSortie() %></td>
                        <td><%= (listeOPT[i].getReste()+Inventaire.toInventaire(LI,listeOPT[i].getIDElement()).getRest()) %></td>
                    </tr>
                <% } %>
            </table>
        </div>


        <div class="list">
            
            <div  class="title">
                <center>Mouvement de stock Date</center>
            </div>
            <br>
            <table class="tab" border="1">
                <tr class="titre">
                    <td>ID</td>
                    <td>Nom du Composant</td>
                    <td>Entree</td>
                    <td>Sortie</td>
                    <td>reste</td>
                </tr>
            <% for (int i = 0; i < listeES.length; i++) {%>
                <tr>
                    <td><%= listeES[i].getIDElement() %></td>
                    <td><%= Elements.toElements(listeE,listeES[i].getIDElement()).getName() %></td>                   
                    <td><%= listeES[i].getEntrer() %></td>
                    <td><%= listeES[i].getSortie() %></td>
                    <td><%= listeES[i].getReste() %></td>
                </tr>
            <% } %>
        </table>
        </div>
    </div>
    

    
        <%connection.close(); 
        } catch (Exception e) {
            out.println( e.getMessage() );
        } %>
     
    </div>
</div>

</body>
</html>