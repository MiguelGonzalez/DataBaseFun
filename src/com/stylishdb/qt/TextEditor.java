package com.stylishdb.qt;

import com.trolltech.qt.core.Qt;
import com.trolltech.qt.core.Qt.KeyboardModifiers;
import com.trolltech.qt.gui.QApplication;
import com.trolltech.qt.gui.QClipboard;
import com.trolltech.qt.gui.QKeyEvent;
import com.trolltech.qt.gui.QPlainTextEdit;
import com.trolltech.qt.gui.QSizePolicy;
import com.trolltech.qt.gui.QTextCursor;
import com.stylishdb.domain.MTab;
import com.stylishdb.domain.holders.IndentationHolder;
import com.stylishdb.qt.controllers.CEditor;
import java.util.Objects;
import java.util.UUID;

/**
 *
 ** @author StylishDB
 */
public class TextEditor extends QPlainTextEdit {
    
    private final int NUM_SPACES_INDENT = 4;
    private UUID uuid;
    private CEditor controlador;
    
    public TextEditor(CEditor controlador) {
        super();
        
        this.controlador = controlador;
        uuid = UUID.randomUUID();
        setLineWrapMode(LineWrapMode.NoWrap);
        setTabStopWidth(40);
        setSizePolicy(new QSizePolicy(QSizePolicy.Policy.Expanding, QSizePolicy.Policy.Expanding));
        
        establecerEventos();
    }
    
    @Override
    public boolean equals(Object object) {
        if(object == null) {
            return false;
        }
        if(!(object instanceof TextEditor)) {
            return false;
        }
        TextEditor editorTextoComparar = (TextEditor) object;
        return uuid.equals(editorTextoComparar.uuid);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + Objects.hashCode(this.uuid);
        return hash;
    }
    
    public void establecerTexto(String texto) {
        texto = texto.replaceAll("\t", getTabIndent());
        
        QTextCursor curs = textCursor();
        curs.beginEditBlock();
        
        curs.movePosition(QTextCursor.MoveOperation.Start,
                QTextCursor.MoveMode.MoveAnchor);
        curs.movePosition(QTextCursor.MoveOperation.End,
                QTextCursor.MoveMode.KeepAnchor);
        curs.removeSelectedText();
        curs.insertText(texto);
        curs.movePosition(QTextCursor.MoveOperation.Start,
                QTextCursor.MoveMode.MoveAnchor);

        curs.endEditBlock();
        setTextCursor(curs);
    }
    
    public void pegarTexto(String texto) {
        texto = texto.replaceAll("\t", getTabIndent());
        insertPlainText(texto);
        
        // Hack: Al pegar un texto el cursor se queda en la primera línea
        // Con esto conseguimos que se coloque al final del texto pegado
        situarCursorAlFinalTextoPegado();
    }
    
    private void situarCursorAlFinalTextoPegado() {
        QTextCursor curs = textCursor();
        
        moverDerechaCursor(curs);
        
        if(!curs.atEnd()) {
            moverIzquierdaCursor(curs);
        }
    }
    
    private void moverDerechaCursor(QTextCursor curs) {
        curs.beginEditBlock();
        
        curs.movePosition(QTextCursor.MoveOperation.Right,
                QTextCursor.MoveMode.MoveAnchor);

        curs.endEditBlock();
        setTextCursor(curs);
    }
    
    private void moverIzquierdaCursor(QTextCursor curs) {
        curs.beginEditBlock();

        curs.movePosition(QTextCursor.MoveOperation.Left,
                QTextCursor.MoveMode.MoveAnchor);

        curs.endEditBlock();
        setTextCursor(curs);
    }
    
    private void establecerEventos() {
        textChanged.connect(controlador, "eventoTextoCambiado()");
        selectionChanged.connect(controlador, "eventoSeleccionCambiado()");
        verticalScrollBar().valueChanged.connect(controlador, "scrollBarCambiado()");
        cursorPositionChanged.connect(controlador, "cursorTextoCambiado()");
        blockCountChanged.connect(controlador, "blockCountCambiado()");
    }
    
    public int getPositionScrollBar() {
        return verticalScrollBar().value();
    }
   
    public MTab getModeloEditor() {
        return controlador.getModeloEditor();
    }
    
    @Override
    protected void keyPressEvent(QKeyEvent event) {        
        if(suprimirRehacerNativo(event)) {
            return;
        }
        
        if(esRehacer(event)) {
            return;
        }
        
        if(esCambiarSiguientePestana(event)) {
            controlador.cambiarSiguientePestana();
            return;
        }
        
        if(esCambiarAnteriorPestana(event)) {
            controlador.cambiarAnteriorPestana();
            return;
        }
        
        if(esPegarTexto(event)) {
            pegarTexto();
            return;
        }
        
        if(esIndentarDerecha(event)) {
            indentarDerecha();
            return;
        }
        
        if(esIndentarIzquierda(event)) {
            indentarIzquierda();
            return;
        }
                
        super.keyPressEvent(event);
    }
    
    private boolean esPegarTexto(QKeyEvent e) {
        KeyboardModifiers modifiers = e.modifiers();
        if(
                modifiers.isSet(Qt.KeyboardModifier.ControlModifier)) {
            if(e.key() == Qt.Key.Key_V.value()) {
                return true;
            }
        }
        
        return false;
    }
    
    private boolean suprimirRehacerNativo(QKeyEvent e) {
        KeyboardModifiers modifiers = e.modifiers();
        if(
                modifiers.isSet(Qt.KeyboardModifier.ControlModifier) &&
                modifiers.isSet(Qt.KeyboardModifier.ShiftModifier)) {
            if(e.key() == Qt.Key.Key_Z.value()) {
                return true;
            }
        }
        
        return false;
    }
    
    private boolean esRehacer(QKeyEvent e) {
        KeyboardModifiers modifiers = e.modifiers();
        if(
                modifiers.isSet(Qt.KeyboardModifier.ControlModifier)) {
            if(e.key() == Qt.Key.Key_Y.value()) {
                return true;
            }
        }
        
        return false;
    }
    
    private boolean esCambiarSiguientePestana(QKeyEvent e) {
        KeyboardModifiers modifiers = e.modifiers();
        if(
                modifiers.isSet(Qt.KeyboardModifier.ControlModifier)) {
            if(e.key() == Qt.Key.Key_Tab.value()) {
                return true;
            }
        }
        
        return false;
    }
    
    private boolean esCambiarAnteriorPestana(QKeyEvent e) {
        KeyboardModifiers modifiers = e.modifiers();
        if(
                modifiers.isSet(Qt.KeyboardModifier.ControlModifier) &&
                modifiers.isSet(Qt.KeyboardModifier.ShiftModifier)) {
            if(e.key() == Qt.Key.Key_Backtab.value()) {
                return true;
            }
        }
        
        return false;
    }
    
    private boolean esIndentarDerecha(QKeyEvent e) {
        if(e.key() == Qt.Key.Key_Tab.value()) {
            return true;
        }
        return false;
    }
    
    private boolean esIndentarIzquierda(QKeyEvent e) {
        KeyboardModifiers modifiers = e.modifiers();
        if(
                modifiers.isSet(Qt.KeyboardModifier.ShiftModifier)) {
            if(e.key() == Qt.Key.Key_Backtab.value()) {
                return true;
            }
        }

        return false;
    }
    
    private void pegarTexto() {
        String clipboardText = QApplication.clipboard().text(
                QClipboard.Mode.Clipboard);
        
        pegarTexto(clipboardText);
    }
    
    private void indentarDerecha() {
        if(textCursor().hasSelection()) {
            indentarSeleccionDerecha();
        } else {
            indentarSimpleDerecha();
        }
    }
    
    private void indentarSimpleDerecha() {
        int caracteresPrevios = textCursor().position() -
                textCursor().block().position();
        String tab = getTabIndentParcial(caracteresPrevios);
        insertPlainText(tab);
    }
    
    private void indentarSeleccionDerecha() {
        IndentationHolder indentacionHolder = new IndentationHolder();
        indentacionHolder.curs = textCursor();
        
        obtenemosFilasIndentar(indentacionHolder);
        indentamosFilas(indentacionHolder);
        seleccionamosTodasFilasIndentadas(indentacionHolder);
    }
    
    private void obtenemosFilasIndentar(IndentationHolder indentacionHolder) {
        QTextCursor curs = indentacionHolder.curs;
        
        int posInicial =  curs.anchor();
        int posFinal = curs.position();

        if(posInicial > posFinal) {
            int hold = posInicial;
            posInicial = posFinal;
            posFinal = hold;
        }
        indentacionHolder.posInicial = posInicial;
        indentacionHolder.posFinal = posFinal;

        curs.setPosition(posInicial, QTextCursor.MoveMode.MoveAnchor);
        indentacionHolder.bloqueInical = curs.block().blockNumber();

        curs.setPosition(posFinal, QTextCursor.MoveMode.MoveAnchor);
        indentacionHolder.bloqueFinal = curs.block().blockNumber();
    }
    
    private void indentamosFilas(IndentationHolder indentacionHolder) {
        QTextCursor curs = indentacionHolder.curs;
        
        curs.setPosition(indentacionHolder.posInicial,
                QTextCursor.MoveMode.MoveAnchor);
        curs.beginEditBlock();

        String tabIndent = getTabIndent();
        for(int i = 0; i <= (indentacionHolder.bloqueFinal - 
                indentacionHolder.bloqueInical); ++i) {
            curs.movePosition(QTextCursor.MoveOperation.StartOfBlock,
                    QTextCursor.MoveMode.MoveAnchor);

            curs.insertText(tabIndent);

            curs.movePosition(QTextCursor.MoveOperation.NextBlock,
                    QTextCursor.MoveMode.MoveAnchor);
        }

        curs.endEditBlock();
    }
        
    private void indentarIzquierda() {
        if(textCursor().hasSelection()) {
            indentarSeleccionIzquierda();
        } else {
            indentarSimpleIzquierda();
        }
    }
    
    private void indentarSimpleIzquierda() {
        IndentationHolder indentacionHolder = new IndentationHolder();
        indentacionHolder.curs = textCursor();
        indentacionHolder.curs.beginEditBlock();
        indentarFilaIzquierda(indentacionHolder);
        indentacionHolder.curs.endEditBlock();
    }
    
    private void indentarSeleccionIzquierda() {
        IndentationHolder indentacionHolder = new IndentationHolder();
        indentacionHolder.curs = textCursor();
                
        obtenemosFilasIndentarIzquierda(indentacionHolder);
        indentamosFilasIzquierda(indentacionHolder);
        seleccionamosTodasFilasIndentadas(indentacionHolder);
    }
    
    private void obtenemosFilasIndentarIzquierda(IndentationHolder indentacionHolder) {
        QTextCursor curs = indentacionHolder.curs;
        
        int posInicial =  curs.anchor();
        int posFinal = curs.position();

        if(posInicial > posFinal) {
            int hold = posInicial;
            posInicial = posFinal;
            posFinal = hold;
        }
        indentacionHolder.posInicial = posInicial;
        indentacionHolder.posFinal = posFinal;

        curs.setPosition(posInicial, QTextCursor.MoveMode.MoveAnchor);
        indentacionHolder.bloqueInical = curs.block().blockNumber();

        curs.setPosition(posFinal, QTextCursor.MoveMode.MoveAnchor);
        indentacionHolder.bloqueFinal = curs.block().blockNumber();
    }
    
    private void indentamosFilasIzquierda(IndentationHolder indentacionHolder) {
        QTextCursor curs = indentacionHolder.curs;
        
        curs.setPosition(indentacionHolder.posInicial,
                QTextCursor.MoveMode.MoveAnchor);
        curs.beginEditBlock();
        for(int i = 0; i <= (indentacionHolder.bloqueFinal - 
                indentacionHolder.bloqueInical); ++i) {
            
            indentarFilaIzquierda(indentacionHolder);
            
            curs.movePosition(QTextCursor.MoveOperation.NextBlock,
                    QTextCursor.MoveMode.MoveAnchor);
        }

        curs.endEditBlock();
    }
    
    private void indentarFilaIzquierda(IndentationHolder indentacionHolder) {
        QTextCursor curs = indentacionHolder.curs;
        curs.movePosition(QTextCursor.MoveOperation.StartOfBlock,
                QTextCursor.MoveMode.MoveAnchor);
        for(int i=0; i<NUM_SPACES_INDENT; i++) {
            
            curs.movePosition(QTextCursor.MoveOperation.NextCharacter,
                    QTextCursor.MoveMode.KeepAnchor);
            String primeraLetra =  curs.selectedText();

            curs.movePosition(QTextCursor.MoveOperation.PreviousCharacter,
                    QTextCursor.MoveMode.MoveAnchor);

            if(" ".equals(primeraLetra)) {
                curs.deleteChar();
            }
        }
    }
    
    private void seleccionamosTodasFilasIndentadas(
            IndentationHolder indentacionHolder) {
        QTextCursor curs = indentacionHolder.curs;
        
        curs.setPosition(indentacionHolder.posInicial,
                QTextCursor.MoveMode.MoveAnchor);
        curs.movePosition(QTextCursor.MoveOperation.StartOfBlock, 
                QTextCursor.MoveMode.MoveAnchor);
        
        while(curs.block().blockNumber() < indentacionHolder.bloqueFinal) {
            curs.movePosition(QTextCursor.MoveOperation.NextBlock,
                    QTextCursor.MoveMode.KeepAnchor);
        }

        curs.movePosition(QTextCursor.MoveOperation.EndOfBlock,
                QTextCursor.MoveMode.KeepAnchor);

        setTextCursor(curs);
    }
    
    public String getTabIndent() {
        String tabIndent = "";
        for(int i=0; i<NUM_SPACES_INDENT; i++) {
            tabIndent += " ";
        }
        return tabIndent;
    }
    
    public String getTabIndentParcial(int caracteresPrevios) {
        int numSpacesAppend = NUM_SPACES_INDENT - caracteresPrevios % NUM_SPACES_INDENT;
        String indentTab = "";
        for(int i=0; i<numSpacesAppend; i++) {
            indentTab += " ";
        }
        return indentTab;
    }

    public void establecerEditorVisible() {
        controlador.establecerEditorVisible();
    }

    public int getNumLineas() {
        return blockCount();
    }

    public int[] getLineasSeleccionadas() {
        int[] lineasSeleccionadas;
        
        IndentationHolder indentacionHolder = new IndentationHolder();
        indentacionHolder.curs = textCursor();
        obtenemosFilasIndentarIzquierda(indentacionHolder);
        
        int numLineasSeleccionadas = indentacionHolder.bloqueFinal -
                indentacionHolder.bloqueInical + 1;
        lineasSeleccionadas = new int[numLineasSeleccionadas];
        
        for(int i=1; i <= numLineasSeleccionadas; i++) {
            lineasSeleccionadas[i-1] =
                    indentacionHolder.bloqueInical + i;
        }
        
        return lineasSeleccionadas;
    }
}
