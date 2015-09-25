package Modelos;

import Controladores.MiModelo;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane;

/**
 * @author Johana Serrano, Erika Tique, Giovani...
 */
public class Modelo_Corresp extends Conexion {
    private String[][] mensajeros;
    
    public String radicado_Salida () {
        int total = 0;
        String radicado="";
        String sql = "SELECT count(*) as Total FROM correspondecia_salida;";      
        try {
            PreparedStatement pstm = this.getConexion().prepareStatement(sql);
            ResultSet Resultado = pstm.executeQuery();
            Resultado.next();
            total = Resultado.getInt("Total");
            Resultado.close();                                 
            total+=1;
            if (total < 9){
              radicado="000"+total;  
            }else if (total < 99){
              radicado="00"+total;  
            }else if (total < 999){
              radicado="0"+total;  
            }else {
                radicado=String.valueOf(total);
            }       
            return radicado;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            return radicado;
        }    
    }    
    public  DefaultTableModel getTablaUsuarios(String doc){
        MiModelo tableModel = new MiModelo();
        int Registros = 0;
        String[] ColumNames = {"Documento","Nombres","Apellidos"};
        String sql = "SELECT count(*) as Total FROM Personas where documento = '"+doc+"';";       
        try {
            PreparedStatement pstm = this.getConexion().prepareStatement(sql);
            ResultSet Resultado = pstm.executeQuery();
            Resultado.next();
            Registros = Resultado.getInt("Total");
            Resultado.close();                                 
            } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            }
            Object[][] data = new String[Registros][3];
            try {
                sql = "SELECT Documento, Nombres, Apellidos FROM Personas "
                 + "where documento = '"+doc+"';";
                PreparedStatement pstm = this.getConexion().prepareStatement(sql);
                ResultSet resultado = pstm.executeQuery();
                int i=0;
              while(resultado.next()){
                  data[i][0] = resultado.getString("Documento");                 
                  data[i][1] = resultado.getString("Nombres");
                  data[i][2] = resultado.getString("Apellidos");
                  i++;                  
              }
              resultado.close();
              tableModel.setDataVector(data, ColumNames);
                } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e.getMessage());
                }
                return tableModel;
    }
    
    public boolean Nuevo(String  Nume_Radi_Entr, String Documento, String Codi_Tipo, String Fech_Envi, String Fech_Elab, String Rta, 
         String Asunto, String Func_Dest, String Depe_Dest, String Docu_Envi, String Mensajero){
           
        if(Valida_Datos (Nume_Radi_Entr, Documento, Codi_Tipo,  Fech_Envi,  Fech_Elab,  Rta, Asunto, Func_Dest, Depe_Dest, Docu_Envi, Mensajero)){ 
             String SQl = "Insert into correspondecia_salida (Nume_Radi_Entr,Documento,Codi_Tipo,Fech_Envi,Fech_Elab,Rta,Asunto,Func_Dest,Depe_Dest,Docu_Envi,Mensajero)"
                         + "Values("+ Nume_Radi_Entr+",'"+ Documento+"','"+ Codi_Tipo+"','"+Fech_Envi+"','"+Fech_Elab+"','"+Rta+"','"+Asunto+"','"+Func_Dest+"','"+Depe_Dest+"','"+Docu_Envi+"','"+Mensajero+"');";
                    JOptionPane.showMessageDialog(null, SQl);                   
            try {
                PreparedStatement pstm = this.getConexion().prepareStatement(SQl);
                    pstm.execute();
                    pstm.close();
                    return true;
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e.getMessage());
                }
                  return false;
                }
                  else 
                  return false;
                }
      
    private boolean Valida_Datos (String Nume_Radi_Entr, String Documento, String Codi_Tipo, String Fech_Envi, String Fech_Elab, String Rta, 
                    String Asunto,String Func_Dest, String Depe_Dest, String Docu_Envi, String Mensajero){
              if (Nume_Radi_Entr.length() > 0 && Documento.length() > 0  && Codi_Tipo.length() > 0 && Rta.length() > 0 && Depe_Dest.length() > 0 && Docu_Envi.length() > 0 && Mensajero.length() > 0) {
                  return true;
                }else{
                  return false;
                }
                }
    
    public String[] LlenarComboBox(){
           String SQL = "Select Nomb_Tipo From tipos_de_documentos;";
           int i = 0;        
           try{
                PreparedStatement Sentencia = this.getConexion().prepareStatement(SQL);
                ResultSet Resultado = Sentencia.executeQuery();                
                while(Resultado.next()){                
                i++;
            }      
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e.getMessage());
                }
                String[] Combo = new String[i];
                SQL = "Select Nomb_Tipo From tipos_de_documentos order by Nomb_Tipo;";
            try{
                PreparedStatement Sentencia = this.getConexion().prepareStatement(SQL);
                ResultSet Resultado = Sentencia.executeQuery();
                i = 0;
                while(Resultado.next()){
                  Combo[i] = Resultado.getString("Nomb_Tipo");
                  i++;
                }
                Resultado.close();
            }catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e.getMessage());
                }
            return Combo;  
    }
    
    //Llenar Radicado//
    public String[] LlenarRadicadoEntr(){
            String SQL = "Select Nume_Radi_Entr From correspondencia_entrada "
            + "where Estado = 'Activo' and Requ_Rta = 'si';";
            int i = 0;     
            try{
                PreparedStatement Sentencia = this.getConexion().prepareStatement(SQL);
                ResultSet Resultado = Sentencia.executeQuery();                
                while(Resultado.next()){                
                  i++;
                }      
            }catch (SQLException e) {
//                JOptionPane.showMessageDialog(null, e.getMessage());
                }
                String[] Combo = new String[i];
                SQL = "Select Nume_Radi_Entr From correspondencia_entrada "
                    + "where Estado = 'Activo' and Requ_Rta = 'si' "
                    + "order by Nume_Radi_Entr;";           
            try{
                PreparedStatement Sentencia = this.getConexion().prepareStatement(SQL);
                ResultSet Resultado = Sentencia.executeQuery();
                i = 0;
                while(Resultado.next()){
                Combo[i] = Resultado.getString("Nume_Radi_Entr");
                i++;
                }
                Resultado.close();
            }catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e.getMessage());
                }
            return Combo;  
        }   
                
    //Llenar Mensajeros//
    public String[] LlenarMensajero(){
            String SQL ="select concat(P.Nombres,\" \",Apellidos) as NombreE\n" +
            "From Empleados E join Personas P on (E.Documento = P.Documento) join (select HL.Documento\n" +
             "	from Historial_Laboral HL join Cargos C on (HL.Codi_Carg = C.Codi_Carg)\n" + 
             "	where C.Nomb_Carg = 'Mensajero' \n" +
             "	and HL.Fech_Egre is NULL) M on (E.Documento = M.Documento);";
            int i = 0;
            try {
                PreparedStatement Sentencia = this.getConexion().prepareStatement(SQL);
                ResultSet Resultado = Sentencia.executeQuery();                
                while(Resultado.next()){                
                  i++;
                }      
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e.getMessage());
            }
            
            String[] Combo = new String[i];
            SQL ="select concat(P.Nombres,\" \",Apellidos) as NombreE\n" +
              "From Empleados E join Personas P on (E.Documento = P.Documento) join (select HL.Documento\n" +
              "	from Historial_Laboral HL join Cargos C on (HL.Codi_Carg = C.Codi_Carg)\n" +
              "	where C.Nomb_Carg = 'Mensajero' \n" +
              "	and HL.Fech_Egre is NULL) M on (E.Documento = M.Documento);";
            try {
                PreparedStatement Sentencia = this.getConexion().prepareStatement(SQL);
                ResultSet Resultado = Sentencia.executeQuery();
                i = 0;
                while(Resultado.next()){
                  Combo[i] = Resultado.getString("NombreE");
                  i++;
                }
                Resultado.close();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e.getMessage());
            }return Combo;  
    }
    
    //Retorno Codigo// 
    public String retornarCodigo (String nombre) {                
        String sql = "SELECT codi_tipo FROM tipos_de_documentos WHERE Nomb_tipo='"+ nombre +"';";
        String codigo="";      
        try {
            PreparedStatement pstm = this.getConexion().prepareStatement(sql);
            ResultSet Resultado = pstm.executeQuery();
            Resultado.next();            
            codigo = Resultado.getString("codi_tipo");
            Resultado.close();
            return codigo;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            return codigo;
        }  
    }
    
    public String hallarDocEmpleado(String nombre){        
        
        mensajeros = this.Mensajero();
        int i = 0;               
        for (; i < mensajeros.length;) {
            if(!mensajeros[1][i].equals(nombre))
               i++;
            else{
                break;
            }
        }
//        JOptionPane.showMessageDialog(null, mensajeros[0][i]);
    return mensajeros[0][i];
    }
    
    //Matriz Mensajero
    private String[][] Mensajero(){         
        int n = 0;
        String sql = "select count(*) as total" +
              " From Empleados E join Personas P on (E.Documento = P.Documento) join (select HL.Documento\n" +
              "	from Historial_Laboral HL join Cargos C on (HL.Codi_Carg = C.Codi_Carg)\n" +
              "	where C.Nomb_Carg = 'Mensajero' \n" +
              "	and HL.Fech_Egre is NULL) M on (E.Documento = M.Documento);";
                            
        try {
            PreparedStatement pstm = this.getConexion().prepareStatement(sql);
            ResultSet Resultado = pstm.executeQuery();
            Resultado.next();            
            n = Resultado.getInt("total");
            Resultado.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }                        
        
        String[][] Mensj = new String[2][n];

        sql = "select P.Documento, concat(P.Nombres,\" \",Apellidos) as NombreE\n" +
              " From Empleados E join Personas P on (E.Documento = P.Documento) join (select HL.Documento\n" +
              "	from Historial_Laboral HL join Cargos C on (HL.Codi_Carg = C.Codi_Carg)\n" +
              "	where C.Nomb_Carg = 'Mensajero' \n" +
              "	and HL.Fech_Egre is NULL) M on (E.Documento = M.Documento);";        
        
        try {
            PreparedStatement Sentencia = this.getConexion().prepareStatement(sql);
            ResultSet Resultado = Sentencia.executeQuery();
            int i = 0;
            while(Resultado.next()){
              Mensj[0][i] = Resultado.getString("Documento");
              Mensj[1][i] = Resultado.getString("NombreE");
              i++;
            }
            Resultado.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return Mensj;
    } 
}




