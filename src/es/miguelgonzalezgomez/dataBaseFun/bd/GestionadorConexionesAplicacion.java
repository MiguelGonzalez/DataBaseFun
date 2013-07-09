package es.miguelgonzalezgomez.dataBaseFun.bd;

import es.miguelgonzalezgomez.dataBaseFun.modelos.MAplicacion;
import es.miguelgonzalezgomez.dataBaseFun.modelos.MConexion;
import java.util.List;

/**
 *
 * @author Miguel González
 */
public class GestionadorConexionesAplicacion {
    
    private MAplicacion aplicacion;
    
    public GestionadorConexionesAplicacion() {
        aplicacion = MAplicacion.getInstance();
    }
    
    public void borrarConexion(MConexion mConexion) {
        aplicacion.removeConexion(mConexion);
    }
    
    public void addNuevaConexion(MConexion mConexion) {
        aplicacion.addNuevaConexion(mConexion);
    }
    
    public boolean existeNombreConexion(String nombreConexion) {
        List<MConexion> conexiones = aplicacion.getConexionesGuardadas();
        
        for(MConexion conexion : conexiones) {
            if(conexion.nombre.equals(nombreConexion)) {
                return true;
            }
        }
        
        return false;
    }
}