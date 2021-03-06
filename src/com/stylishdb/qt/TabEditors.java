package com.stylishdb.qt;

import com.trolltech.qt.core.Qt;
import com.trolltech.qt.gui.QKeyEvent;
import com.trolltech.qt.gui.QTabBar;
import com.trolltech.qt.gui.QTabWidget;
import com.stylishdb.domain.MTab;
import com.stylishdb.domain.MTabListener;
import com.stylishdb.qt.controllers.CTabEditor;
import java.util.ArrayList;
import java.util.List;

/**
 *
 ** @author StylishDB
 */
public class TabEditors extends QTabWidget 
        implements MTabListener {
    
    private List<MTab> editoresTexto;
    
    
    public TabEditors() {
        editoresTexto = new ArrayList<>();
    }
    
    public void addTabEditorTexto(MTab mPestana) {
        CTabEditor cPestanaEditor = new CTabEditor(mPestana);
        HolderEditor contenedorEditor = cPestanaEditor.getContenedorEditor();
        
        editoresTexto.add(mPestana);
        
        int index =addTab(
                contenedorEditor, mPestana.toString()
                );
        setCurrentIndex(index);
        
        mPestana.addPestanaListener(this);
    }
    
    public void removeTabEditorTexto(MTab mPestana) {
        int posicionPestana = -1;
        
        for(int i=0; i<editoresTexto.size() && posicionPestana == -1; i++) {
            MTab mPestanaEditor = editoresTexto.get(i);
            
            if(mPestanaEditor.equals(mPestana)) {
                posicionPestana = i;
            }
        }
        
        if(posicionPestana != -1) {
            editoresTexto.remove(mPestana);
            removeTab(posicionPestana);
        }
        
        mPestana.removePestanaListener(this);
    }
    
    public void setPestanasEditor(QTabBar tabBar) {
        setTabBar(tabBar);
    }
    
    @Override
    protected void keyPressEvent(QKeyEvent event) {
         if(esCambiarSiguientePestana(event)) {
            return;
        }
        
        if(esCambiarAnteriorPestana(event)) {
            return;
        }
        
        super.keyPressEvent(event);
    }
    
    private boolean esCambiarSiguientePestana(QKeyEvent e) {
        Qt.KeyboardModifiers modifiers = e.modifiers();
        if(modifiers.isSet(Qt.KeyboardModifier.ControlModifier)) {
            if(e.key() == Qt.Key.Key_Tab.value()) {
                return true;
            }
        }
        
        return false;
    }
    
    private boolean esCambiarAnteriorPestana(QKeyEvent e) {
        Qt.KeyboardModifiers modifiers = e.modifiers();
        
        if(modifiers.isSet(Qt.KeyboardModifier.ControlModifier) &&
                modifiers.isSet(Qt.KeyboardModifier.ShiftModifier)) {
            if(e.key() == Qt.Key.Key_Backtab.value()) {
                return true;
            }
        }
        
        return false;
    }

    @Override
    public void textoModificado(MTab mPestana) {}

    @Override
    public void textoSeleccionado(MTab mPestana) {}

    @Override
    public void renombrada(MTab mPestana) {
        for(int i=0; i<editoresTexto.size(); i++) {
            MTab mPestanaEditor = editoresTexto.get(i);
            
            if(mPestanaEditor.equals(mPestana)) {
                setTabText(i, mPestana.toString());
            }
        }
    }

    @Override
    public void textoFormateado(MTab mPestana) {}
}
