package es.miguelgonzalezgomez.dataBaseFun.qt.controladores.pestanaVistaResultado;

import com.trolltech.qt.gui.QWidget;
import es.miguelgonzalezgomez.dataBaseFun.bd.ManejadorConsulta;
import es.miguelgonzalezgomez.dataBaseFun.bd.ManejadorConsultaErrorSQL;
import es.miguelgonzalezgomez.dataBaseFun.bd.ManejadorConsultaNoHayConexion;
import es.miguelgonzalezgomez.dataBaseFun.bd.domain.ResultadoEjecutarConsulta;
import es.miguelgonzalezgomez.dataBaseFun.modelos.MConexion;
import es.miguelgonzalezgomez.dataBaseFun.qt.pestanaVistaResultado.PestanaMostrarResultadoConsulta;
import es.miguelgonzalezgomez.dataBaseFun.qt.modals.ModalMostrarAviso;

/**
 *
 * @author Miguel González
 */
public class CPestanaMostrarConsulta {
    
    private ManejadorConsulta manejadorConsulta;
    private PestanaMostrarResultadoConsulta pestanaResultado;
    
    private CVistaDatosConsulta controladorVistaDatosConsulta;
    
    private String consultaSQL;
    private MConexion mConexion;
    private ResultadoEjecutarConsulta resultado;
    
    public CPestanaMostrarConsulta(MConexion mConexion,
            String consultaSQL) {
        this.consultaSQL = consultaSQL;
        this.mConexion = mConexion;
        
        crearControladoresYComponentes();
        ejecutarPasosLanzarQuery();
    }
    
    private void crearControladoresYComponentes() {
        manejadorConsulta = new ManejadorConsulta();
        controladorVistaDatosConsulta = new CVistaDatosConsulta();
        
        pestanaResultado = new PestanaMostrarResultadoConsulta(this);
        
        pestanaResultado.pintarVistaDatosConsulta(
                controladorVistaDatosConsulta.getVistaDatosConsulta()
        );
    }
    
    private void ejecutarPasosLanzarQuery() {
        if(conectarContraBaseDeDatos()) {
            if(ejecutarConsulta()) {
                pintarRespuestaConsulta();
            }
        }
    }
    
    public QWidget getPestanaResultado() {
        return pestanaResultado;
    }
    
    private boolean conectarContraBaseDeDatos() {
        try {
            manejadorConsulta.conectarContraBaseDeDatos(mConexion);
            return true;
        } catch (ManejadorConsultaNoHayConexion ex) {
            mostrarErrorEnPantalla(
                    "Connection exception",
                    ex.getMessage()
            );
        }
        return false;
    }
    
    private boolean ejecutarConsulta() {
        try {
            resultado = 
                    manejadorConsulta.ejecutarConsulta(mConexion, consultaSQL);
            return true;
        } catch (ManejadorConsultaErrorSQL ex) {
            mostrarErrorEnPantalla(
                    "Error al ejecutar la consulta",
                    ex.getMessage()
            );
        }
        return false;
    }
    
    private void pintarRespuestaConsulta() {
        controladorVistaDatosConsulta.pintarDatosConsulta(resultado);
    }

    
    private void mostrarErrorEnPantalla(String tituloError, String mensajeError) {
        ModalMostrarAviso.mostrarErrorEnPantalla(tituloError,
                mensajeError);
    }
    
}
