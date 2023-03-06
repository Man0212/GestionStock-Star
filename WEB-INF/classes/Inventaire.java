
package element;
import java.sql.Connection;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Inventaire extends Generalisation{
    LocalDate Daty;
    String IDElement;
    float Rest;
    public Inventaire() {
    }
    public Inventaire(String daty, String iDElement, float rest) {
        setDaty(daty);
        IDElement = iDElement;
        Rest = rest;
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
    public float getRest() {
        return Rest;
    }
    public void setRest(float rest) {
        Rest = rest;
    }
    
    public static Inventaire[] ListInventaire(Connection connection) throws Exception{
        Object[] O =new Inventaire().Select("SELECT * FROM Inventaire where daty=(select max(daty) from Inventaire)",connection);
        Inventaire[] E=new Inventaire[O.length];
        for(int i=0;i<E.length;i++){E[i]=(Inventaire) O[i];} 
        return E;
    }

    public static Inventaire toInventaire(Inventaire[] LE,String E){
        Inventaire A=null;
        for (int i = 0; i < LE.length; i++) {
            if (LE[i].getIDElement().equalsIgnoreCase(E)) {
                A=LE[i];
            }
        }
        return A;
    }
}
