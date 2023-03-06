package element;
import connect.Connexion;
import element.Generalisation;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class EtatStock extends Generalisation{
    String IDElement;
    float Entrer;
    float Sortie;
    float Reste;
    public EtatStock(String iDElement, float entrer, float sortie, float reste) {
       setIDElement(iDElement);
        setEntrer(entrer);
       setSortie(sortie);
        setReste(reste);;
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
        if(entrer<0){Entrer = entrer*-1;}
        Entrer = entrer;
    }
    public float getSortie() {
        return Sortie;
    }
    public void setSortie(float sortie) {
        if(sortie<0){Sortie = sortie*-1;}
        Sortie = sortie;
    }
    public float getReste() {
        return Reste;
    }
    public void setReste(float reste) {
        Reste = reste;
    }
    public EtatStock() {
    }

    public static EtatStock[] ListEtatStock(Connection connection){
        EtatStock[] E=null;
        try {
        Object[] O =new EtatStock().Select("SELECT * FROM EtatStock",connection);
        E=new EtatStock[O.length];
        for(int i=0;i<E.length;i++){E[i]=(EtatStock) O[i];} 
        } catch (Exception e) {
           
        }      
        return E;
    }

    public static EtatStock[] ListDate(Connection connection,String daty) throws Exception{
        EtatStock[] E=null;
        String requete =   "select IDElement,sum(Entre),sum(Sortie),(sum(Entre)) - (sum(Sortie))from GestionStockage where TO_CHAR(daty,'yyyy-mm-dd')<'" + daty + "' group by idelement";
        System.out.println(requete);
        Object[] O =new EtatStock().Select(requete,connection);
        E=new EtatStock[O.length];
        for(int i=0;i<E.length;i++){E[i]=(EtatStock) O[i];} 
        return E;
    }

    public static EtatStock[] ListOptimise(Connection connection) throws Exception{
        EtatStock[] E=null;
        String requete =   "select IDElement,sum(Entre),sum(Sortie),(sum(Entre)) - (sum(Sortie))from GestionStockage where daty>(select max(daty) from Inventaire) group by idelement";
        Object[] O =new EtatStock().Select(requete,connection);
        E=new EtatStock[O.length];
        for(int i=0;i<E.length;i++){E[i]=(EtatStock) O[i];} 
        return E;
    }

}
