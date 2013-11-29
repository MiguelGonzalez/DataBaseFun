package es.miguelgonzalezgomez.dataBaseFun.qt.controladores;

import es.miguelgonzalezgomez.dataBaseFun.estilos.ObtencionEstilo;
import es.miguelgonzalezgomez.dataBaseFun.domain.MConexion;
import es.miguelgonzalezgomez.dataBaseFun.domain.MPestana;
import es.miguelgonzalezgomez.dataBaseFun.domain.PestanaListener;
import es.miguelgonzalezgomez.dataBaseFun.qt.EditorTexto;
import es.miguelgonzalezgomez.dataBaseFun.qt.ContadorLineas;
import es.miguelgonzalezgomez.dataBaseFun.qt.ContenedorEditor;
import es.miguelgonzalezgomez.dataBaseFun.qt.restaltadoEditor.ConstruirSyntaxHighlighter;

/**
 *
 * @author Miguel González
 */
public class CEditor extends CMiControladorGenerico
        implements PestanaListener {
    
    private MPestana mPestanaEditor;
    
    private EditorTexto editorTexto;
    private ContadorLineas contadorLineas;
    private ContenedorEditor contenedorEditor;
    

    public CEditor(MPestana mPestanaEditor) {
        super();

        this.mPestanaEditor = mPestanaEditor;
        
        construirEditorTexto();
        construirContadorLineas();
        establecerResaltadoSintaxis();
        establecerTextoModeloPestana();
        construirContenedorEditorTexto();
        
        inicializarListeners();
    }
    
    private void inicializarListeners() {
        mPestanaEditor.addPestanaListener(this);
    }
    
    private void construirEditorTexto() {
        editorTexto = new EditorTexto(this);
        editorTexto.setStyleSheet(
                ObtencionEstilo.getEstiloVentana("editor.css")
        );
    }
    
    private void construirContadorLineas() {
        contadorLineas = new ContadorLineas(this);
        contadorLineas.setStyleSheet(
                ObtencionEstilo.getEstiloVentana("editorContadorLineas.css")
        );
    }
    
    private void construirContenedorEditorTexto() {
        contenedorEditor = new ContenedorEditor(this);
 
        contenedorEditor.establecerContadorLineas(contadorLineas);
        contenedorEditor.establecerEditorTexto(editorTexto);
    }
    
    private void establecerResaltadoSintaxis() {
        MConexion mConexion = mAplicacion.mConexionesGuardadas.
                getMConexion(mPestanaEditor.uuidConexion);
        ConstruirSyntaxHighlighter.establecerSyntaxHighlighter(
                mConexion.getTipoDeBaseDeDatos(),
                editorTexto.document()
        );
    }
    
    private void establecerTextoModeloPestana() {
        String textoEditor = mPestanaEditor.getTextoEditor();
        editorTexto.establecerTexto(textoEditor);
    }
        
    public ContenedorEditor getContenedorEditor() {
        return contenedorEditor;
    }
    
    public MPestana getModeloEditor() {
        return mPestanaEditor;
    }
    
    public void eventoTextoCambiado() {
        String textoEditor = editorTexto.document().toPlainText();
        mPestanaEditor.setTextoEditor(textoEditor);
    }
    
    public void eventoSeleccionCambiado() {
        boolean hayTextoSeleccionado = editorTexto.textCursor().hasSelection();
        String textoSeleccionado = "";
        if(hayTextoSeleccionado) {
            textoSeleccionado = editorTexto.textCursor().selectedText();
        }
        mPestanaEditor.setTextoSeleccionado(
                textoSeleccionado,
                hayTextoSeleccionado);
    }
    
    public void cursorTextoCambiado() {
        actualizarSeleccion();
        actualizarScrollBar();
    }
    
    public void scrollBarCambiado() {
        actualizarScrollBar();
    }
    
    public void blockCountCambiado() {
        int numLineas = editorTexto.getNumLineas();
        contadorLineas.pintarContadorLineas(numLineas);
        
        actualizarScrollBar();
        actualizarSeleccion();
    }

    public void cambiarSiguientePestana() {
        controladorPestanasAbiertas.cambiarSiguientePestana();
    }

    public void cambiarAnteriorPestana() {
        controladorPestanasAbiertas.cambiarAnteriorPestana();
    }

    public void establecerEditorVisible() {
        pestanasAbiertas.establecerEditorActivo(mPestanaEditor);
    }
    
    private synchronized void actualizarSeleccion() {
        int lineasSeleccionadas[] = editorTexto.getLineasSeleccionadas();
        contadorLineas.lineasSeleccionadas(lineasSeleccionadas);
    }
    
    private synchronized void actualizarScrollBar() {
        int pos = editorTexto.getPositionScrollBar();
        contadorLineas.setPosicionScrollBar(pos);
    }

    @Override
    public void textoModificado(MPestana mPestana) {}

    @Override
    public void textoSeleccionado(MPestana mPestana) {}

    @Override
    public void renombrada(MPestana mPestana) {}
    
    @Override
    public void textoFormateado(MPestana mPestana) {
        editorTexto.establecerTexto(
            mPestana.getTextoEditor()
        );
    }
    
    protected void deshacerTexto() {
        editorTexto.undo();
    }
    
    protected void rehacerTexto() {
        editorTexto.redo();
    }
    
    public void quitarListeners() {
        mPestanaEditor.removePestanaListener(this);
    }
}
