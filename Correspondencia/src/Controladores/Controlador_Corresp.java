package Controladores;
/**
 *
 * @author Johana Serrano, Erika Tique, Giovani...
 */
import Modelos.Modelo_Corresp;
import Vistas.FrmCorresp_Salida;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class Controlador_Corresp implements ActionListener,MouseListener{

    FrmCorresp_Salida VtnCorresp ;
    Modelos.Modelo_Corresp modelo = new Modelo_Corresp();

       public String FormatoFecha(Date fecha){
       SimpleDateFormat output = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
       String formattedTime = output.format(fecha);
       return formattedTime;
       }
              
    public enum AccionMVC{
        __ENTER__,
        __SALIDA__,
        __LOSTFOCUS__
    }

    public Controlador_Corresp(FrmCorresp_Salida vista){
        this.VtnCorresp = vista;
    
        for (int i = 0; i < modelo.LlenarComboBox().length; i++) {
            vista.jCombTipoD.addItem(modelo.LlenarComboBox()[i]);
        }
    
        for (int i = 0; i < modelo.LlenarRadicadoEntr().length; i++) {
            vista.jCombRadicadoEnt.addItem(modelo.LlenarRadicadoEntr()[i]);
        }
        
        for (int i = 0; i < modelo.LlenarMensajero().length; i++) {
            vista.jCombmensajero.addItem(modelo.LlenarMensajero()[i]);
        }
   
        vista.jTextNumRadS.setText(modelo.radicado_Salida());
    }
    
    public Calendar FormatoHora(){
    Calendar calendario = Calendar.getInstance();
        int  Hora, Minutos, Segundos;
        
        Hora = calendario.get(Calendar.HOUR_OF_DAY);
        Minutos = calendario.get(Calendar.MINUTE);
        Segundos = calendario.get(Calendar.SECOND);
        return calendario;
        
    }
    public void iniciar(){
        try{
                UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows."
                    + "WindowsLookAndFeel");
                SwingUtilities.updateComponentTreeUI(VtnCorresp);
            VtnCorresp.setVisible(true);
            } catch (UnsupportedLookAndFeelException ex) {}
              catch (ClassNotFoundException ex) {
              JOptionPane.showMessageDialog(VtnCorresp, "Error de driver de "
                      + "video: "+ex.getMessage());
                }
          catch (InstantiationException ex) {}
          catch (IllegalAccessException ex) {}

           VtnCorresp.setVisible(true);
           VtnCorresp.setLocationRelativeTo(VtnCorresp);
       
        this.VtnCorresp.jTextDuc.setActionCommand("__ENTER__");
        this.VtnCorresp.jTextDuc.addActionListener(this);  

        this.VtnCorresp.jCombRadicadoEnt.setActionCommand("__LOSTFOCUS__");
        this.VtnCorresp.jCombRadicadoEnt.addActionListener(this);        //aqui Importante 
        
        this.VtnCorresp.jBSalida.setActionCommand("__SALIDA__");
        this.VtnCorresp.jBSalida.addActionListener(this);
    }
    
     @Override
    public void mousePressed(MouseEvent e) {}
     @Override
    public void mouseReleased(MouseEvent e) {}
     @Override
    public void mouseEntered(MouseEvent e) {}
     @Override
    public void mouseExited(MouseEvent e) { }
     @Override
     
     public void mouseClicked(MouseEvent e) {
     }
      
     @Override
    public void actionPerformed(ActionEvent e) {
        switch (AccionMVC.valueOf(e.getActionCommand())){
            
            case __ENTER__:{
                this.VtnCorresp.TableUsuarios.setModel(this.modelo.
                    getTablaUsuarios(this.VtnCorresp.jTextDuc.getText())); 
                    break;
                }
            case __SALIDA__:{
                String radicadoselec = (String)this.VtnCorresp.jCombRadicadoEnt.getSelectedItem();
                String radicadosalida = "";
                
                if (radicadoselec.equals("Seleccione el Radicado Entrada")){
                    radicadosalida = "null";      
                }else{
                    radicadosalida = (String)this.VtnCorresp.jCombRadicadoEnt.getSelectedItem();
                }                                
                
                if (this.modelo.Nuevo(radicadosalida,
                      this.VtnCorresp.jTextDuc.getText(),
                      modelo.retornarCodigo((String)this.VtnCorresp.jCombTipoD.getSelectedItem()),
                      FormatoFecha(this.VtnCorresp.jDateFchaE.getDate()),
                      FormatoFecha(this.VtnCorresp.jDateFchaElab.getDate()),
                      (String)this.VtnCorresp.jTextAreaRta.getText(),
                      this.VtnCorresp.jTextFuncionarioD.getText(),
                      this.VtnCorresp.jTextAreaAsunto.getText(),
                      (String)this.VtnCorresp.jTextAreaDocEnvio.getText(),
                      this.VtnCorresp.jTextDependenciaD.getText(),
                      this.modelo.hallarDocEmpleado(this.VtnCorresp.jCombmensajero.getSelectedItem().toString()))){                                        
                    
                }
                    this.VtnCorresp.jCombRadicadoEnt.requestFocus();
                    
                    this.VtnCorresp.jTextNumRadS.setText("");
                    this.VtnCorresp.jTextDuc.setText("");
                    this.VtnCorresp.jCombTipoD.setSelectedItem("");
                    this.VtnCorresp.jDateFchaE.setDate(null);
                    this.VtnCorresp.jDateFchaElab.setDate(null);
                    this.VtnCorresp.jTextAreaRta.setText("");
                    this.VtnCorresp.jTextFuncionarioD.setText("");
                    this.VtnCorresp.jTextAreaAsunto.setText("");
                    this.VtnCorresp.jTextAreaDocEnvio.setText("");
                    this.VtnCorresp.jTextDependenciaD.setText("");
                    this.VtnCorresp.jCombmensajero.setSelectedItem("");
                    break;
                } 
            }
        }
}

