package element;

import connect.*;

import java.lang.reflect.*;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.net.ConnectException;
import java.math.BigDecimal;

public class Generalisation{
    String prefix="PRS";
    int longPK=7;
    String FonctionOracle="getSeqinf";

    public int getlongPK()
	{
		return this.longPK;
	}

	public void setlongPK(int empn)
	{
		this.longPK=empn;
	}

    public String getprefix()
	{
		return this.prefix;
	}

	public void setprefix(String enam)
	{
		this.prefix=enam;
	}

    public String getFonctionOracle()
	{
		return this.FonctionOracle;
	}

	public void setFonctionOracle(String enam)
	{
		this.FonctionOracle=enam;
	}

    public static String encodeData(Object obj, String separator) throws Exception {
        Field[] fieldsName = obj.getClass().getDeclaredFields();
        String[] data = new String[fieldsName.length];
        for (int i = 0; i < data.length; i++) {
            Object value = obj.getClass().getMethod("get"+ fieldsName[i].getName()).invoke(obj);
            if(value!=null)data[i] = fieldsName[i].getName().concat(":" + value.toString());
        }
        return String.join(separator, data);
    }

    // public void Historiser(String action,Connection connection,String table) throws Exception{
    //     HistoricCompte H= new HistoricCompte();
    //     H.setHistoric_id(H.ConstruirePK(connection));
    //     H.setOrigin_table(this.getClass().getSimpleName());
    //     H.setAction(action);
    //     H.setModification_date(new Date(System.currentTimeMillis()).toString());
    //     H.setValue(encodeData(this, ";"));

    //     Insert(table, H, connection);
    // }

    public String ConstruirePK(Connection connection) throws Exception
    {
        boolean Exist=true;
        Statement stmt;

        if(connection == null) {
            connection=Connexion.IConnex();
            stmt = connection.createStatement();
            Exist = false;
        } else {
            stmt = connection.createStatement();
        }
        String PK="";
        int Valiny=0;
        String Request = "select " + this.getFonctionOracle() + " from dual" ;
        
        ResultSet resultat=stmt.executeQuery(Request); 
        while(resultat.next())
        {
            Valiny=resultat.getInt(1);
        }
        PK=completePK(this.getprefix(),this.getlongPK(),Valiny);
        stmt.close();
        if(Exist==false)
        { 
            connection.close();
        }
        return PK;
    }

    public static String completePK(String prefix,int taille,int seqpers)
    {
        int a=prefix.length();
        int b=String.valueOf(seqpers).length();
        int c=taille-(a+b);
        String o="0";
        for(int i=1;i<c;i++)
        {
            o+="0";
        }
        String Valiny = prefix + o + seqpers;
        return Valiny;
    }

    public Object[] Select(String request,Connection connection)throws Exception{  
        Statement  stmt = connection.createStatement();   
        ResultSet result= stmt.executeQuery(request);       
        Field[] tableauAttribut = this.getClass().getDeclaredFields();
        Vector<Object> resulttab=new Vector<>();
        int taille=0;
        while(result.next()){
                Object temp= this.getClass().getConstructor().newInstance();
                for(int i=0; i<tableauAttribut.length; i++){
                    Method setterEmpno = this.getClass().getDeclaredMethod("set"+ tableauAttribut[i].getName(), tableauAttribut[i].getType());
                    if(tableauAttribut[i].getType()==Integer.class){
                        setterEmpno.invoke(temp,result.getInt(i+1));
                    }
                    if(tableauAttribut[i].getType().getSimpleName().equals("String"))setterEmpno.invoke(temp,result.getString(i+1));
                    if(tableauAttribut[i].getType().getSimpleName().equals("Date"))setterEmpno.invoke(temp,result.getDate(i+1));
                    if(tableauAttribut[i].getType().getSimpleName().equals("LocalDate"))setterEmpno.invoke(temp,result.getDate(i+1).toLocalDate());
                    if(tableauAttribut[i].getType().getSimpleName().equals("int"))setterEmpno.invoke(temp,result.getInt(i+1));
                    if (tableauAttribut[i].getType().getSimpleName().equals("double"))setterEmpno.invoke(temp, result.getDouble(i + 1));
                    if (tableauAttribut[i].getType().getSimpleName().equals("float"))setterEmpno.invoke(temp, result.getFloat(i + 1));
                }
                taille++;
                resulttab.add(temp);
            }
            Object[] tab=new Object[taille];
            for (int i = 0; i < tab.length; i++) {
                tab[i]=resulttab.get(i);
            }
            stmt.close();
            return tab;
        }
    
    
        public static void Insert(String Table, Object objet ,Connection connection) throws Exception{
            boolean Exist=true;
            Statement stmt;
    
            if(connection == null) {
                connection=Connexion.IConnex();
                stmt = connection.createStatement();
                Exist = false;
            } else {
                stmt = connection.createStatement();
            }
            Field[] tableauAttribut = objet.getClass().getDeclaredFields();
            int NBField = tableauAttribut.length;
            Object temp= objet.getClass().getConstructor().newInstance();
            String concatenation ="";
            for(int i=0;i<NBField;i++)
            {
                Method getter = objet.getClass().getDeclaredMethod("get"+ tableauAttribut[i].getName());     
                if(tableauAttribut[i].getType().getSimpleName().equals("int") || tableauAttribut[i].getType().getSimpleName().equals("float"))concatenation += getter.invoke(objet);
                if(tableauAttribut[i].getType()==Integer.class)concatenation += getter.invoke(objet);     
                if(tableauAttribut[i].getType().getSimpleName().equals("Date") || tableauAttribut[i].getType().getSimpleName().equals("LocalDate"))concatenation +=  "to_date('" + getter.invoke(objet) + "', 'yyyy-mm-dd')";
                if(tableauAttribut[i].getType().getSimpleName().equals("String"))concatenation +=  "'"+ getter.invoke(objet) +"'";
                if(i<NBField-1)concatenation += ",";
            }
    
            String request="INSERT INTO " + Table + " VALUES( "+ concatenation +")";
            System.out.println(request);
            
            stmt.executeQuery(request);  
            stmt.close();
            if(Exist==false)
            {
                connection.close();
            }
        }
    
        public static void UpdDel (String Request,Connection connection) throws Exception{
            boolean Exist=true;
            Statement stmt;
    
            if(connection == null) {
                connection=Connexion.IConnex();
                stmt = connection.createStatement();
                Exist = false;
            } else {
                stmt = connection.createStatement();
            }
            stmt.executeQuery(Request);  
            stmt.close();
            if(Exist==false)
            {
                connection.close();
            }
        }

        public static int[] ToNumber(String list[]){
            int[] V=new int[list.length];
            for(int i=1;i<list.length;i++){
                if(list[i].equalsIgnoreCase("Souhaiter"))V[i]=20;
                if(list[i].equalsIgnoreCase("TresBien"))V[i]=15;
                if(list[i].equalsIgnoreCase("Bien"))V[i]=10;
                if(list[i].equalsIgnoreCase("Moyen"))V[i]=5;
                if(list[i].equalsIgnoreCase("mauvais"))V[i]=1;          
            }
            return V;
        }

        
}