package Controladores;
/**
 * @author Johana Serrano, Erika Tique, Giovani...
 */
import javax.swing.table.DefaultTableModel;

public class MiModelo extends DefaultTableModel
{
   public boolean isCellEditable (int row, int column)
   {
    return false;
   }
}