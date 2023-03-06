package element;
import connect.Connexion;
import java.sql.*;

public class Elements extends Generalisation{
    String IDElement;
    String Name;
    double MinQuantity;
    double PricePerQuantity;
    String Unite;
    String Primary;

    public String getPrimary() {
        return Primary;
    }
    public void setPrimary(String primary) {
        Primary = primary;
    }
    public String getIDElement() {
        return IDElement;
    }
    public void setIDElement(String iDElement) {
        IDElement = iDElement;
    }
    public String getName() {
        return Name;
    }
    public void setName(String name) {
        Name = name;
    }
    public double getMinQuantity() {
        return MinQuantity;
    }
    public void setMinQuantity(double minQuantity) {
        MinQuantity = minQuantity;
    }
    public double getPricePerQuantity() {
        return PricePerQuantity;
    }
    public void setPricePerQuantity(double pricePerQuantity) {
        PricePerQuantity = pricePerQuantity;
    }
    public String getUnite() {
        return Unite;
    }
    public void setUnite(String unite) {
        Unite = unite;
    }

    public static Elements[] ListElements(Connection connection){
        Elements[] E=null;
        try {
        Object[] O =new Elements().Select("SELECT * FROM Elements",connection);
        E=new Elements[O.length];
        for(int i=0;i<E.length;i++){E[i]=(Elements) O[i];} 
        } catch (Exception e) {
           
        }      
        return E;
    }

    
    public static Elements toElements(Elements[] LE,String E){
        Elements A=null;
        for (int i = 0; i < LE.length; i++) {
            if (LE[i].getIDElement().equalsIgnoreCase(E)) {
                A=LE[i];
            }
        }
        return A;
    }
    
    public static void DisplayResult(Elements[] listE,Composant[] listC){
        for (int index = 0; index < listE.length; index++) {  
            String ID="C"+(1+index);
            double prix= Composant.CalculCostPrice(listE,listC,ID);
            Elements E=Elements.toElements(listE,ID);
            
            if (prix>0) {
                System.out.println("prix de revient d'une "+E.getName() + " = " + prix );
            }

            Composant.decomposer(listE,listC,ID).forEach(item ->{
                System.out.println(item);
            });
        }
    }
}