package element;
import java.sql.*;
import java.util.ArrayList;
import connect.Connexion;

public class Composant extends Generalisation{
    String IDComposant;
    String IDElement;
    double Quantity;

    public String getIDComposant() {
        return IDComposant;
    }

    public void setIDComposant(String iDComposant) {
        IDComposant = iDComposant;
    }

    public String getIDElement() {
        return IDElement;
    }

    public void setIDElement(String iDElement) {
        IDElement = iDElement;
    }

    public double getQuantity() {
        return Quantity;
    }

    public void setQuantity(double quantity) {
        Quantity = quantity;
    }

    public static Composant[] ListComposant(Connection connection) throws Exception{
        Object[] O =new Composant().Select("SELECT * FROM Composant order by idelement desc",connection);
        Composant[] E=new Composant[O.length];
        for(int i=0;i<E.length;i++){E[i]=(Composant) O[i];} 
        return E;
    }

    public static ArrayList<ArrayList> ListComposantPerProduit(Elements[] LE,Composant[] LC,String IDComposant){
        ArrayList<ArrayList> data=new ArrayList<ArrayList>();
        
        
        for(int i=0;i<LC.length;i++){  
            if(LC[i].getIDComposant().equalsIgnoreCase(IDComposant)){
                ArrayList list=new ArrayList<>();
                Elements E=Elements.toElements(LE, LC[i].getIDElement());
                list.add(LC[i].getIDElement());
                list.add(LC[i].getQuantity());

                double valiny=LC[i].getQuantity() * E.getPricePerQuantity();

                list.add(E.getName());
                list.add(valiny);
                data.add(list);
            }
        }   
        return data;
    }

    public static ArrayList<ArrayList> decomposer(Elements[] LE,Composant[] LC,String IDComposant){
        ArrayList<ArrayList> data=ListComposantPerProduit(LE,LC,IDComposant);

        for (int o = 0; o < 10; o++) {
        for (int i = 0; i < data.size(); i++) {
            Elements E=Elements.toElements(LE, (String) data.get(i).get(0));
            if (!E.getPrimary().equalsIgnoreCase("yes")) {
                ArrayList<ArrayList> Newdata = ListComposantPerProduit(LE,LC,E.getIDElement());
                for (int j = 0; j <Newdata.size(); j++) {
                    Newdata.get(j).set(1, (double)Newdata.get(j).get(1)*(double)data.get(i).get(1) );    
                    Newdata.get(j).set(3, (double)Newdata.get(j).get(3)*(double)data.get(i).get(1) );  
                    // if (!((String) Newdata.get(j).get(0)).equalsIgnoreCase("C7") && !((String) Newdata.get(j).get(0)).equalsIgnoreCase("C12")) {
                        data.add(Newdata.get(j));
                    // }
                }
                data.remove(i);
                i=0;
            }   
        }  
             
        }
        return data;
    }

    public static double CalculCostPrice(Elements[] LE,Composant[] LC,String IDComposant){
        ArrayList<ArrayList> LCPP = decomposer(LE,LC,IDComposant);
        double somme=0;
        for (int i = 0; i < LCPP.size(); i++) {
           Elements E= Elements.toElements(LE,(String) LCPP.get(i).get(0));
           double quantity = (double) LCPP.get(i).get(1);
           double valiny=(quantity / E.getMinQuantity()) * E.getPricePerQuantity();
           somme=somme+valiny;
        }
        return somme;
    }
}