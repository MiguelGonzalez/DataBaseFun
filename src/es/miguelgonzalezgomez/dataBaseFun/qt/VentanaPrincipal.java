package es.miguelgonzalezgomez.dataBaseFun.qt;

import com.trolltech.qt.gui.QCloseEvent;
import com.trolltech.qt.gui.QMainWindow;
import es.miguelgonzalezgomez.dataBaseFun.DataBaseFun;
import es.miguelgonzalezgomez.dataBaseFun.qt.controladores.CVentanaPrincipal;

/**
 *
 * @author Miguel González
 */
public class VentanaPrincipal extends QMainWindow {
    
    private CVentanaPrincipal controlador;
    
    public VentanaPrincipal(String tituloVentana,
            es.miguelgonzalezgomez.dataBaseFun.qt.controladores.CVentanaPrincipal controlador) {
        setWindowTitle(tr(tituloVentana));
                
        this.controlador = controlador;
    }

    @Override
    protected void closeEvent(QCloseEvent event) {
        super.closeEvent(event);
        
        DataBaseFun.salirAplicacion();
    }
}
