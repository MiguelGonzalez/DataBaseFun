package es.miguelgonzalezgomez.dataBaseFun.qt.pestanaVistaResultado;

import com.trolltech.qt.gui.QTabWidget;
import com.trolltech.qt.gui.QVBoxLayout;
import com.trolltech.qt.gui.QWidget;
import es.miguelgonzalezgomez.dataBaseFun.qt.controladores.pestanaVistaResultado.QWidgetResultadosConsulta;

/**
 *
 * @author Miguel González
 */
public class WidgetResultadosConsulta extends QWidget {
    
    private QVBoxLayout widgetLayout;
    private QTabWidget pestanasTiposVistas;
    private QWidgetResultadosConsulta controlador;
    
    public WidgetResultadosConsulta(QWidgetResultadosConsulta controlador) {
        this.controlador = controlador;
        
        crearComponentesInterfaz();
        posicionarComponentesInterfaz();
    }
    
    private void crearComponentesInterfaz() {
        widgetLayout = new QVBoxLayout();   
        pestanasTiposVistas = new QTabWidget();
    }
    
    public void pintarVistaDatosConsulta(VistaDatosConsulta vistaDatos) {
        pestanasTiposVistas.addTab(
                vistaDatos,
                "Datos consulta"
        );
    }
    
    public void pintarVistaDatosTextoPlano(VistaDatosTextoPlano vistaDatos) {
        pestanasTiposVistas.addTab(
                vistaDatos,
                "Texto plano"
        );
    }
    
    public void pintarVistaDatosTabla(VistaDatosTabla vistaDatos) {
        pestanasTiposVistas.addTab(
                vistaDatos,
                "Datos columnas"
        );
    }
    
    public void pintarVistaDatosInformacion(VistaDatosInformacion vistaDatos) {
        pestanasTiposVistas.addTab(
                vistaDatos,
                "Información"
        );
    }
    
    private void posicionarComponentesInterfaz() {
        widgetLayout.addWidget(pestanasTiposVistas);
        setLayout(widgetLayout);
    }
}