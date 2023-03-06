package element;
import connect.Connexion;
import element.Generalisation;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class GestionStockage extends Generalisation{
    LocalDate Daty;
    String IDElement;
    float Entrer;
    float Sortie;
    float Prix;
    
    public GestionStockage() {
    }
    public GestionStockage(String daty, String iDElement, float entrer, float sortie,float prix) {
        setDaty(daty);
        IDElement = iDElement;
        Entrer = entrer;
        Sortie = sortie;
        Prix = prix;
    }
    public GestionStockage(LocalDate daty, String iDElement, float entrer, float sortie,float prix) {
        setDaty(daty);
        IDElement = iDElement;
        Entrer = entrer;
        Sortie = sortie;
        Prix = prix;
    }
    public float getPrix() {
        return Prix;
    }
    public void setPrix(float prix) {
        Prix = prix;
    }
    public LocalDate getDaty() {
        return Daty;
    }
    public void setDaty(LocalDate daty) {
        Daty = daty;
    }
    public void setDaty(String daty) {
        DateTimeFormatter formatter=DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(daty,formatter);
        setDaty(localDate);
    }
    public String getIDElement() {
        return IDElement;
    }
    public void setIDElement(String iDElement) {
        IDElement = iDElement;
    }
    public float getEntrer() {
        return Entrer;
    }
    public void setEntrer(float entrer) {
        Entrer = entrer;
    }
    public float getSortie() {
        return Sortie;
    }
    public void setSortie(float sortie) {
        Sortie = sortie;
    }

    public static void Insertion(String name,GestionStockage G,Connection connection) throws Exception{
        Insert(name,G,connection);
    }

}