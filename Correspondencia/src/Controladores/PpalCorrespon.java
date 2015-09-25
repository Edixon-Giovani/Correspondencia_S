package Controladores;
import Vistas.FrmCorresp_Salida;
/**
 * @author Johana Serrano, Erika Tique, Giovani...
 */
public class PpalCorrespon {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        iniciar();
    }
    public static void iniciar(){
        new Controlador_Corresp(new FrmCorresp_Salida()).iniciar();
    }
}
