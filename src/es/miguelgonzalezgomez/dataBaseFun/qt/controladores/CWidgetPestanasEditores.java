package es.miguelgonzalezgomez.dataBaseFun.qt.controladores;

import com.trolltech.qt.gui.QIcon;
import com.trolltech.qt.gui.QTabWidget;
import com.trolltech.qt.gui.QWidget;
import es.miguelgonzalezgomez.dataBaseFun.gestionadores.GEditoresAplicacion;
import es.miguelgonzalezgomez.dataBaseFun.estilos.ObtencionEstilo;
import es.miguelgonzalezgomez.dataBaseFun.modelos.MPestanaEditor;
import static es.miguelgonzalezgomez.dataBaseFun.modelos.MPestanaEditorEvento.*;
import es.miguelgonzalezgomez.dataBaseFun.modelos.MPestanasEditorAbiertas;
import es.miguelgonzalezgomez.dataBaseFun.modelos.PestanaEditorListener;
import es.miguelgonzalezgomez.dataBaseFun.qt.EditorTexto;
import es.miguelgonzalezgomez.dataBaseFun.qt.WidgetPestanasEditores;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Miguel González
 */
public class CWidgetPestanasEditores {

    private WidgetPestanasEditores widgetPestanasEditores;

    private GEditoresAplicacion editoresAplicacion;
    private CPestanasEditores cPestanasEditores;
    
    private Map<MPestanaEditor, CEditor> relacionPestanaEditor;
    
    public CWidgetPestanasEditores() {
        editoresAplicacion = new GEditoresAplicacion();
        cPestanasEditores = new CPestanasEditores(this);
        relacionPestanaEditor = new HashMap<>();
        
        inicializarWidget();
        establecerTabBar();
        cargarPestanasAbiertas();
        
        escuchaEditoresAplicacion();
    }
    
    private void inicializarWidget() {
        widgetPestanasEditores = new WidgetPestanasEditores(this);
    }
    
    private void establecerTabBar() {
        widgetPestanasEditores.setPestanasEditor(
                cPestanasEditores.getTabBar()
        );
        
        widgetPestanasEditores.currentChanged.connect(this, "cambiadaPestana()");
        
        widgetPestanasEditores.setStyleSheet(
                ObtencionEstilo.getEstiloVentana("tabsEditoresWidget.css")
        );

    }
    
    private void cargarPestanasAbiertas() {
        for(MPestanaEditor pestanaEditor :
                editoresAplicacion.getPestanasEditores()) {
            addTab(pestanaEditor);
        }
    }
    
    public QTabWidget getVistaPestanasEditores() {
        return widgetPestanasEditores;
    }
    
    public void tabMoveRequested(int fromIndex, int toIndex) {
        QWidget w = widgetPestanasEditores.widget(fromIndex);
        QIcon icon = widgetPestanasEditores.tabIcon(fromIndex);
        String text = widgetPestanasEditores.tabText(fromIndex);

        widgetPestanasEditores.removeTab(fromIndex);
        widgetPestanasEditores.insertTab(toIndex, w, icon, text);
        widgetPestanasEditores.setCurrentIndex(toIndex);
    }
    
    private void escuchaEditoresAplicacion() {
        CWidgetPestanasEditoresEscuchaCambios escuchaCambiosPestana = new
                CWidgetPestanasEditoresEscuchaCambios(this);
        editoresAplicacion.addPestanasEditorListener(escuchaCambiosPestana);
    }

    public void addTab(MPestanaEditor pestanaEditor) {
        CEditor cEditor = new CEditor(pestanaEditor);
        
        relacionPestanaEditor.put(pestanaEditor, cEditor);
        
        widgetPestanasEditores.addTab(
                cEditor.getEditorTexto(),
                pestanaEditor.mConexion.nombre
        );
    }
    
    public void buscarYDeshacerPestana(MPestanaEditor pestanaEditor) {
        CEditor cEditor = relacionPestanaEditor.get(pestanaEditor);
        
        if(cEditor != null) {
            cEditor.deshacer();
        }
    }
    
    public void buscarYRehacerPestana(MPestanaEditor pestanaEditor) {
        CEditor cEditor = relacionPestanaEditor.get(pestanaEditor);
        
        if(cEditor != null) {
            cEditor.rehacer();
        }
    }
    
    private void cambiadaPestana() {
        EditorTexto editorTexto = (EditorTexto) widgetPestanasEditores.
                currentWidget();
        
        editorTexto.estaVisible();
    }
    
    public void comprobarYRenombrarPestanaEditor(MPestanaEditor pestanaEditorEditada) {
        for(int i=0; i<relacionPestanaEditor.size(); i++) {
            EditorTexto editorTexto = (EditorTexto) widgetPestanasEditores.widget(i);
            
            if(pestanaEditorEditada.equals(editorTexto.getModeloEditor())) {
                widgetPestanasEditores.setTabText(i, pestanaEditorEditada.nombrePestana);
            }
        }
    }
}
