package es.miguelgonzalezgomez.dataBaseFun.qt;

import com.trolltech.qt.gui.QTabWidget;
import com.trolltech.qt.gui.QWidget;
import es.miguelgonzalezgomez.dataBaseFun.qt.controladores.CPanelPestanasMostrarConsultas;

/**
 *
 * @author Miguel González
 */
public class PanelPestanasMostrarConsultas extends QTabWidget {
    
    private CPanelPestanasMostrarConsultas controlador;
    
    public PanelPestanasMostrarConsultas(CPanelPestanasMostrarConsultas controlador) {
        this.controlador = controlador;
    }
    
    public void addTab(String name, QWidget widget) {
        addTab(widget, name);
    }
}
