package es.miguelgonzalezgomez.dataBaseFun.bd;

import es.miguelgonzalezgomez.dataBaseFun.bd.domain.ObtenerUrlConexion;
import es.miguelgonzalezgomez.dataBaseFun.modelos.MConexion;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.NClob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Miguel González
 */
public class ManejadorConsulta {

    private MConexion conexion;
    
    private Connection connection = null;
    private Statement statement = null;
    private ResultSet rsQuery = null;
    
    private int numConsultaLanzando;
    private AnalizadorTextoConsulta analizadorTextoConsulta;
    
    private List<Integer> tiposColumnaConsultaActual;
    
    public ManejadorConsulta(
            MConexion conexion,
            String consultaSQL) {
        this.conexion = conexion;
        
        numConsultaLanzando = 0;
        analizadorTextoConsulta = new AnalizadorTextoConsulta(
                consultaSQL,
                conexion.tipoDeBaseDeDatos);
    }
    
    public void conectarContraBaseDeDatos(String consultaSQL) throws
            ManejadorConsultaErrorSQL,
            ManejadorConsultaNoHayConexion {
        conectar();
    }
    
    private void conectar() throws ManejadorConsultaNoHayConexion {
        String urlConexion = ObtenerUrlConexion.getUrlConexion(
                conexion.tipoDeBaseDeDatos,
                conexion.ip,
                conexion.puerto,
                conexion.sid);
        try {
            connection = DriverManager.getConnection(
                    urlConexion,
                    conexion.usuario,
                    conexion.password);
        } catch (SQLException ex) {
            throw new ManejadorConsultaNoHayConexion(ex);
        }
    }
    
    public boolean next() {
        if(analizadorTextoConsulta.numConsultasExistentes() > numConsultaLanzando) {
            numConsultaLanzando++;
            return true;
        }
        return false;
    }
    
    public boolean isEjecutarQuery() {
        return analizadorTextoConsulta.isEjecutarQuery(numConsultaLanzando);
    }
    
    public void lanzarConsultaActual() throws ManejadorConsultaErrorSQL {
        try {
            if(analizadorTextoConsulta.isEjecutarQuery(numConsultaLanzando)) {
                statement = connection.createStatement();
                rsQuery = statement.executeQuery(
                        analizadorTextoConsulta.getConsulta(
                                numConsultaLanzando - 1
                        )
                );
                tiposColumnaConsultaActual = getTiposColumnas();
            }
        } catch (SQLException ex) {
            throw new ManejadorConsultaErrorSQL(ex);
        }
    }
    
    public boolean haySiguienteFila() throws ManejadorConsultaErrorSQL {
        try {
            return rsQuery.next();
        } catch (SQLException ex) {
            throw new ManejadorConsultaErrorSQL(ex);
        }
    }
    
    public int getNumColumnas() throws ManejadorConsultaErrorSQL {
        try {
            return rsQuery.getMetaData().getColumnCount();
        } catch (SQLException ex) {
            throw new ManejadorConsultaErrorSQL(ex);
        }
    }
    
    public List<String> getNombresColumnas() throws ManejadorConsultaErrorSQL {
        List<String> nombresColumnas = new ArrayList<>();
        
        try {
            int numColumnas = getNumColumnas();
            for(int i=1; i<=numColumnas; i++) {
                nombresColumnas.add(rsQuery.getMetaData().getColumnName(i));
            }
        } catch (SQLException ex) {
            throw new ManejadorConsultaErrorSQL(ex);
        }
        
        return nombresColumnas;
    }
    
    public List<Integer> getTiposColumnas() throws ManejadorConsultaErrorSQL {
        List<Integer> tiposColumna = new ArrayList<>();
        
        try {
            int numColumnas = getNumColumnas(); 
            for(int i=1; i<=numColumnas; i++) {
                int tipoColumna = rsQuery.getMetaData().getColumnType(i);
                tiposColumna.add(tipoColumna);
            }
        } catch (SQLException ex) {
            throw new ManejadorConsultaErrorSQL(ex);
        }
        
        return tiposColumna;
    }
    
    public List<String> getFila() throws ManejadorConsultaErrorSQL {
        List<String> fila = new ArrayList<>();
        
        try {
            int numColumnas = getNumColumnas(); 
            for(int i=1; i<=numColumnas; i++) {
                int tipoColumna = tiposColumnaConsultaActual.get(i - 1).intValue();
                
                String datoColumna = getDatoColumna(i, tipoColumna);
               
                fila.add(datoColumna);
            }
        } catch (SQLException ex) {
            throw new ManejadorConsultaErrorSQL(ex);
        }
        
        return fila;
    }
    
    private String getDatoColumna(int numColumna, int tipoColumna) throws
                ManejadorConsultaErrorSQL {
        try {
            switch(tipoColumna) {
                case java.sql.Types.ARRAY:
                    Array array = rsQuery.getArray(numColumna);
                    if(array == null) {
                        return "NULL";
                    }
                    return array.getArray().toString();
                case java.sql.Types.BIGINT:
                    return Long.toString(rsQuery.getLong(numColumna));
                case java.sql.Types.BINARY: 
                    return "BINARY";
                case java.sql.Types.BIT: 
                    return "BIT";
                case java.sql.Types.BLOB: 
                    Blob blob = rsQuery.getBlob(numColumna);
                    return blob == null ? "NULL" : blob.toString();
                case java.sql.Types.BOOLEAN:
                    return Boolean.toString(rsQuery.getBoolean(numColumna));
                case java.sql.Types.CHAR: 
                    return rsQuery.getString(numColumna);
                case java.sql.Types.CLOB: 
                    Clob clob = rsQuery.getClob(numColumna);
                    return clob == null ? "NULL" : clob.toString();
                case java.sql.Types.DATALINK: 
                    return "DATALINK";
                case java.sql.Types.DATE: 
                    Date date = rsQuery.getDate(numColumna);
                    return date == null ? "NULL" : date.toString();
                case java.sql.Types.DECIMAL: 
                    return Double.toString(rsQuery.getDouble(numColumna));
                case java.sql.Types.DISTINCT: 
                    return "DISTINCT";
                case java.sql.Types.DOUBLE: 
                    return Double.toString(rsQuery.getDouble(numColumna));
                case java.sql.Types.FLOAT: 
                    return Float.toString(rsQuery.getFloat(numColumna));
                case java.sql.Types.INTEGER: 
                    return Integer.toString(rsQuery.getInt(numColumna));
                case java.sql.Types.JAVA_OBJECT: 
                    return "JAVA_OBJECT";
                case java.sql.Types.LONGNVARCHAR: 
                    return rsQuery.getString(numColumna);
                case java.sql.Types.LONGVARBINARY: 
                    return rsQuery.getString(numColumna);
                case java.sql.Types.LONGVARCHAR: 
                    return rsQuery.getString(numColumna);
                case java.sql.Types.NCHAR: 
                    return rsQuery.getNString(numColumna);
                case java.sql.Types.NCLOB: 
                    NClob nClob = rsQuery.getNClob(numColumna);
                    return nClob == null? "NULL" : nClob.toString();
                case java.sql.Types.NULL: 
                    return "NULL";
                case java.sql.Types.NUMERIC: 
                    return Float.toString(rsQuery.getFloat(numColumna));
                case java.sql.Types.NVARCHAR: 
                    return rsQuery.getNString(numColumna);
                case java.sql.Types.OTHER: 
                    return "OTHER";
                case java.sql.Types.REAL: 
                    return Double.toString(rsQuery.getDouble(numColumna));
                case java.sql.Types.REF: 
                    return rsQuery.getRef(numColumna).getObject().toString();
                case java.sql.Types.ROWID: 
                    return "ROWID";
                case java.sql.Types.SMALLINT: 
                    return Integer.toString(rsQuery.getInt(numColumna));
                case java.sql.Types.SQLXML: 
                    return "SQLXML";
                case java.sql.Types.STRUCT: 
                    return "STRUCT";
                case java.sql.Types.TIME: 
                    return rsQuery.getTime(numColumna).toString();
                case java.sql.Types.TIMESTAMP: 
                    Timestamp timestamp = rsQuery.getTimestamp(numColumna);
                    return timestamp == null? "NULL" : timestamp.toString();
                case java.sql.Types.TINYINT: 
                    return Integer.toString(rsQuery.getInt(numColumna));
                case java.sql.Types.VARBINARY: 
                    return "VARBINARY";
                case java.sql.Types.VARCHAR:
                    return rsQuery.getString(numColumna);
            }
        } catch (SQLException ex) {
            throw new ManejadorConsultaErrorSQL(ex);
        }
        return "";
    }
    
    public void cerrarConsultaSQL() {
        if(rsQuery != null) {
            try {
                rsQuery.close();
            } catch (SQLException ex) {
                //No manejamos los errores al cerrar la conexión
            }
        }
        if(statement != null) {
            try {
                statement.close();
            } catch (SQLException ex) {
                //No manejamos los errores al cerrar la conexión
            }
        }
        if(connection != null) {
            try {
                connection.close();
            } catch (SQLException ex) {
                //No manejamos los errores al cerrar la conexión
            }
        }
        
    }
}
