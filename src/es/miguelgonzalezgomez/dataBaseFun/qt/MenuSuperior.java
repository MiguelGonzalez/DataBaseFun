package es.miguelgonzalezgomez.dataBaseFun.qt;

import com.trolltech.qt.core.QEvent;
import com.trolltech.qt.gui.QAction;
import com.trolltech.qt.gui.QMenu;
import com.trolltech.qt.gui.QMenuBar;
import es.miguelgonzalezgomez.dataBaseFun.modelos.MConexion;
import es.miguelgonzalezgomez.dataBaseFun.qt.controladores.CMenuSuperior;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Miguel González
 */
public class MenuSuperior extends QMenuBar {
    
    private CMenuSuperior controlador;
    private QMenu achivoMenu;
    private QMenu conexionesMenu;
    private QMenu herramientasMenu;
    
    private QAction salirAction;
    private QAction nuevaConexion;
    private QAction preferencias;
    
    private Map<String, QMenu> conexiones;
    
    public MenuSuperior(
            CMenuSuperior controlador) {
        this.controlador = controlador;
        
        conexiones = new HashMap<String, QMenu>();
        
        crearOpcionesFichero();
        crearOpcionesConexiones();
        crearOpcionesHerramientas();
    }
        
    private void crearOpcionesFichero() {
        achivoMenu = addMenu(tr("&Archivo"));

        achivoMenu.addSeparator();
        crearOpcionSalir();
    }

    private void crearOpcionSalir() {
        salirAction = new QAction(tr("Salir"), this);
        salirAction.triggered.connect(controlador, "salirAplicacion()");
        
        achivoMenu.addAction(salirAction);
    }
    
    private void crearOpcionesConexiones() {
        conexionesMenu = addMenu(tr("&Conexiones"));
        
        crearOpcionNuevaConexion();
        conexionesMenu.addSeparator();
    }
    
    private void crearOpcionNuevaConexion() {
        nuevaConexion = new QAction(tr("Nueva conexión"), this);
        nuevaConexion.triggered.connect(controlador, "nuevaConexion()");
        
        conexionesMenu.addAction(nuevaConexion);
    }
    
    private void crearOpcionesHerramientas() {
        herramientasMenu = addMenu(tr("&Preferencias"));
        
        herramientasMenu.addSeparator();
        crearOpcionPreferencias();
    }
    
    private void crearOpcionPreferencias() {
        preferencias = new QAction(tr("Preferencias"), this);
        preferencias.triggered.connect(controlador, "preferencias()");
        
        herramientasMenu.addAction(preferencias);
    }
    
    @Override
    protected void changeEvent(QEvent event) {
        if (event.type() == QEvent.Type.LanguageChange) {
            achivoMenu.setTitle(tr("&Archivo"));
            conexionesMenu.setTitle(tr("&Conexiones"));
            herramientasMenu.setTitle(tr("&Preferencias"));
            
            salirAction.setText(tr("Salir"));
            nuevaConexion.setText(tr("Nueva conexión"));
            preferencias.setText(tr("Preferencias"));
        }
    }

    public void pintarNuevaConexion(MConexion mConexion) {
        QMenu conexion = new QMenu(mConexion.nombre, this);
        
        pintarOpcionesNuevaConexion(conexion, mConexion);
        
        conexiones.put(mConexion.nombre, conexion);

        conexionesMenu.addMenu(conexion);
    }
    
    private void pintarOpcionesNuevaConexion(QMenu conexion, MConexion mConexion) {
        QAction conexionAbrir = new QAction(tr("Abrir conexión"), this);
        conexion.addAction(conexionAbrir);
        
        QAction conexionEditar = new QAction(tr("Editar conexión"), this);
        conexion.addAction(conexionEditar);
        
        conexion.addSeparator();
        
        QAction conexionBorrar = new QAction(tr("Borrar conexión"), this);
        conexionBorrar.setData(mConexion);
        conexionBorrar.triggered.connect(controlador, "borrarConexion()");
        
        conexion.addAction(conexionBorrar);
    }

    public void despintarConexion(MConexion mConexion) {
        QMenu conexionBorrar = conexiones.get(mConexion.nombre);
        
        conexionesMenu.removeAction(conexionBorrar.menuAction());
    }
}