package es.miguelgonzalezgomez.dataBaseFun.modelos;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author Miguel González
 */
public class MConexionesGuardadas {
    private List<MConexion> conexionesGuardadas;
    
    private transient List<ConexionListener> conexionListeners;
    
    public MConexionesGuardadas() {
        conexionesGuardadas = new ArrayList<>();
        conexionListeners = new ArrayList<>();
    }
    
    public MConexion getMConexion(UUID uuidConexion) {
        for(MConexion conexion : conexionesGuardadas) {
            if(conexion.uuidConexion.equals(uuidConexion)) {
                return conexion.clone();
            }
        }
        return null;
    }
    
    public MConexion getMConexionNombre(String nombreConexion) {
        for(MConexion conexion : conexionesGuardadas) {
            if(conexion.nombre.equals(nombreConexion)) {
                return conexion.clone();
            }
        }
        return null;
    }
    
    public List<MConexion> getConexionesGuardadas() {
        List<MConexion> conexionCopy = new ArrayList<>();
        for(MConexion conexion : conexionesGuardadas) {
            conexionCopy.add(conexion.clone());
        }

        return conexionCopy;
    }
    
    public void addConexionListener(ConexionListener conexionListener) {
        conexionListeners.add(conexionListener);
    }
    
    public void removeConexionListener(ConexionListener conexionListener) {
        conexionListeners.remove(conexionListener);
    }
    
    public void addNuevaConexion(MConexion conexion) {
        conexionesGuardadas.add(conexion.clone());
        
        notificarNuevaConexion(conexion);
    }
    
    public void removeConexion(MConexion conexion) {
        conexionesGuardadas.remove(conexion);
        
        notificarEliminadaConexion(conexion);
    }
    
    public void editadaConexion(MConexion mConexionVieja,
            MConexion mConexionNueva) {
        for(MConexion conexion : conexionesGuardadas) {
            if(conexion.equals(mConexionVieja)) {
                conexion.nombre = mConexionNueva.nombre;
                conexion.tipoDeBaseDeDatos = mConexionNueva.tipoDeBaseDeDatos;
                conexion.sid = mConexionNueva.sid;
                conexion.ip = mConexionNueva.ip;
                conexion.puerto = mConexionNueva.puerto;
                conexion.usuario = mConexionNueva.usuario;
                conexion.password = mConexionNueva.password;
            }
        }
        
        notificarModificadaConexion(mConexionVieja,
                mConexionNueva);
    }
    
    private void notificarNuevaConexion(MConexion conexion) {
        for(ConexionListener conexionListener : getCopiaConexionListeners()) {
            conexionListener.nuevaConexion(conexion);
        }
    }
    
    private void notificarEliminadaConexion(MConexion conexion) {
        for(ConexionListener conexionListener : getCopiaConexionListeners()) {
            conexionListener.eliminadaConexion(conexion);
        }
    }
    
    private void notificarModificadaConexion(MConexion conexionVieja,
            MConexion conexionEditada) {
        for(ConexionListener conexionListener : getCopiaConexionListeners()) {
            conexionListener.modificadaConexion(conexionVieja, conexionEditada);
        }
    }
    
    private List<ConexionListener> getCopiaConexionListeners() {
        List<ConexionListener> conexionListenersCopy = new
                ArrayList<>(conexionListeners);
        return conexionListenersCopy;
    }

    public MConexion getConexionCopia(MConexion mConexion) {
        List<MConexion> conexionesGuardadasCopy = new
                ArrayList<>(conexionesGuardadas);
        for(MConexion conexionGuardadaCopy : conexionesGuardadasCopy) {
            if(conexionGuardadaCopy.equals(mConexion)) {
                return conexionGuardadaCopy.clone();
            }
        }
        return new MConexion();
    }
}
