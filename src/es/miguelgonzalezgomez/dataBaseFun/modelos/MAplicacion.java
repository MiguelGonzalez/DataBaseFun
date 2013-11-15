package es.miguelgonzalezgomez.dataBaseFun.modelos;

/**
 *
 * @author Miguel González
 */
public class MAplicacion {
    
    public MConexionesGuardadas mConexionesGuardadas;
    public MPestanasEditorAbiertas mPestanasEditorAbiertas;
    
    private static MAplicacion INSTANCE = null;
    
    private MAplicacion() {
        inicializarModelos();
    }
    
    private void inicializarModelos() {
        mConexionesGuardadas = new MConexionesGuardadas();
        mPestanasEditorAbiertas = new MPestanasEditorAbiertas(this);
    }
    
    public static MAplicacion getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new MAplicacion();
        }
        return INSTANCE;
    }
}
