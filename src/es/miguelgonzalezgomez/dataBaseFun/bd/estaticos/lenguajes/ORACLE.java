package es.miguelgonzalezgomez.dataBaseFun.bd.estaticos.lenguajes;

import es.miguelgonzalezgomez.dataBaseFun.bd.domain.TiposBasesDeDatos;

/**
 *
 * @author Miguel González
 */
public class ORACLE implements DatosBaseDatos {
    
    public static String nombrePresentable = "Oracle";
    public static String claseDriver = "oracle.jdbc.driver.OracleDriver";
    public static String puertoDriver = "1521";
    
    public static String[][] palabrasClaveDelimitadoresConsulta = {
        {"SELECT",";"},
        {"UPDATE",";"},
        {"INSERT",";"},
        {"DELETE",";"},
        {"DROP",";"},
        {"TRUNCATE",";"},
        {"MERGE",";"},
        {"PROCEDURE","END;"}
    };

    public static String[] palabrasClaveEjecutarConsulta = {
        "SELECT"
    };
    
    public static String[] palabrasClavesReservadas = {
        "ACCESS","ACCOUNT","ACTIVATE","ADD","ADMIN","ADVISE","AFTER",
        "ALL","ALL_ROWS","ALLOCATE","ALTER","ANALYZE","AND","ANY",
        "ARCHIVE","ARCHIVELOG","ARRAY","AS","ASC","AT","AUDIT","AUTHENTICATED",
        "AUTHORIZATION","AUTOEXTEND","AUTOMATIC","BACKUP","BECOME","BEFORE",
        "BEGIN","BETWEEN","BFILE","BITMAP","BLOB","BLOCK","BODY","BY","CACHE",
        "CACHE_INSTANCES","CANCEL","CASCADE","CAST","CFILE","CHAINED","CHANGE",
        "CHAR","CHAR_CS","CHARACTER","CHECK","CHECKPOINT","CHOOSE","CHUNK",
        "CLEAR","CLOB","CLONE","CLOSE","CLOSE_CACHED_OPEN_CURSORS","CLUSTER",
        "COALESCE","COLUMN","COLUMNS","COMMENT","COMMIT","COMMITTED","COMPATIBILITY",
        "COMPILE","COMPLETE","COMPOSITE_LIMIT","COMPRESS","COMPUTE","CONNECT",
        "CONNECT_TIME","CONSTRAINT","CONSTRAINTS","CONTENTS","CONTINUE","CONTROLFILE",
        "CONVERT","COST","CPU_PER_CALL","CPU_PER_SESSION","CREATE","CURRENT",
        "CURRENT_SCHEMA","CURREN_USER","CURSOR","CYCLE","DANGLING","DATABASE",
        "DATAFILE","DATAFILES","DATAOBJNO","DATE","DBA","DBHIGH","DBLOW","DBMAC",
        "DEALLOCATE","DEBUG","DEC","DECIMAL","DECLARE","DEFAULT","DEFERRABLE",
        "DEFERRED","DEGREE","DELETE","DEREF","DESC","DIRECTORY","DISABLE",
        "DISCONNECT","DISMOUNT","DISTINCT","DISTRIBUTED","DML","DOUBLE",
        "DROP","DUMP","EACH","ELSE","ENABLE","END","ENFORCE","ENTRY","ESCAPE",
        "EXCEPT","EXCEPTIONS","EXCHANGE","EXCLUDING","EXCLUSIVE","EXECUTE",
        "EXISTS","EXPIRE","EXPLAIN","EXTENT","EXTENTS","EXTERNALLY",
        "FAILED_LOGIN_ATTEMPTS","FALSE","FAST","FILE","FIRST_ROWS","FLAGGER",
        "FLOAT","FLOB","FLUSH","FOR","FORCE","FOREIGN","FREELIST","FREELISTS",
        "FROM","FULL","FUNCTION","GLOBAL","GLOBALLY","GLOBAL_NAME","GRANT",
        "GROUP","GROUPS","HASH","HASHKEYS","HAVING","HEADER","HEAP","IDENTIFIED",
        "IDGENERATORS","IDLE_TIME","IF","IMMEDIATE","IN","INCLUDING","INCREMENT",
        "INDEX","INDEXED","INDEXES","INDICATOR","IND_PARTITION","INITIAL",
        "INITIALLY","INITRANS","INSERT","INSTANCE","INSTANCES","INSTEAD","INT",
        "INTEGER","INTERMEDIATE","INTERSECT","INTO","IS","ISOLATION",
        "ISOLATION_LEVEL","KEEP","KEY","KILL","LABEL","LAYER","LESS","LEVEL",
        "LIBRARY","LIKE","LIMIT","LINK","LIST","LOB","LOCAL","LOCK","LOCKED",
        "LOG","LOGFILE","LOGGING","LOGICAL_READS_PER_CALL",
        "LOGICAL_READS_PER_SESSION","LONG","MANAGE","MASTER","MAX","MAXARCHLOGS",
        "MAXDATAFILES","MAXEXTENTS","MAXINSTANCES","MAXLOGFILES","MAXLOGHISTORY",
        "MAXLOGMEMBERS","MAXSIZE","MAXTRANS","MAXVALUE","MIN","MEMBER","MINIMUM",
        "MINEXTENTS","MINUS","MINVALUE","MLSLABEL","MLS_LABEL_FORMAT","MODE",
        "MODIFY","MOUNT","MOVE","MTS_DISPATCHERS","MULTISET","NATIONAL","NCHAR",
        "NCHAR_CS","NCLOB","NEEDED","NESTED","NETWORK","NEW","NEXT","NOARCHIVELOG",
        "NOAUDIT","NOCACHE","NOCOMPRESS","NOCYCLE","NOFORCE","NOLOGGING","NOMAXVALUE",
        "NOMINVALUE","NONE","NOORDER","NOOVERRIDE","NOPARALLEL","NOPARALLEL",
        "NOREVERSE","NORMAL","NOSORT","NOT","NOTHING","NOWAIT","NULL","NUMBER",
        "NUMERIC","NVARCHAR2","OBJECT","OBJNO","OBJNO_REUSE","OF","OFF","OFFLINE",
        "OID","OIDINDEX","OLD","ON","ONLINE","ONLY","OPCODE","OPEN","OPTIMAL",
        "OPTIMIZER_GOAL","OPTION","OR","ORDER","ORGANIZATION","OSLABEL","OVERFLOW",
        "OWN","PACKAGE","PARALLEL","PARTITION","PASSWORD","PASSWORD_GRACE_TIME",
        "PASSWORD_LIFE_TIME","PASSWORD_LOCK_TIME","PASSWORD_REUSE_MAX",
        "PASSWORD_REUSE_TIME","PASSWORD_VERIFY_FUNCTION","PCTFREE","PCTINCREASE",
        "PCTTHRESHOLD","PCTUSED","PCTVERSION","PERCENT","PERMANENT","PLAN",
        "PLSQL_DEBUG","POST_TRANSACTION","PRECISION","PRESERVE","PRIMARY",
        "PRIOR","PRIVATE","PRIVATE_SGA","PRIVILEGE","PRIVILEGES","PROCEDURE",
        "PROFILE","PUBLIC","PURGE","QUEUE","QUOTA","RANGE","RAW","RBA","READ",
        "READUP","REAL","REBUILD","RECOVER","RECOVERABLE","RECOVERY","REF",
        "REFERENCES","REFERENCING","REFRESH","RENAME","REPLACE","RESET",
        "RESETLOGS","RESIZE","RESOURCE","RESTRICTED","RETURN","RETURNING",
        "REUSE","REVERSE","REVOKE","ROLE","ROLES","ROLLBACK","ROW","ROWID",
        "ROWNUM","ROWS","RULE","SAMPLE","SAVEPOINT","SB4","SCAN_INSTANCES",
        "SCHEMA","SCN","SCOPE","SD_ALL","SD_INHIBIT","SD_SHOW","SEGMENT",
        "SEG_BLOCK","SEG_FILE","SELECT","SEQUENCE","SERIALIZABLE","SESSION",
        "SESSION_CACHED_CURSORS","SESSIONS_PER_USER","SET","SHARE","SHARED",
        "SHARED_POOL","SHRINK","SIZE","SKIP","SKIP_UNUSABLE_INDEXES","SMALLINT",
        "SNAPSHOT","SOME","SORT","SPECIFICATION","SPLIT","SQL_TRACE","STANDBY",
        "START","STATEMENT_ID","STATISTICS","STOP","STORAGE","STORE","STRUCTURE",
        "SUCCESSFUL","SWITCH","SYS_OP_ENFORCE_NOT_NULL$","SYS_OP_NTCIMG$",
        "SYNONYM","SYSDATE","SYSDBA","SYSOPER","SYSTEM","TABLE","TABLES",
        "TABLESPACE","TABLESPACE_NO","TABNO","TEMPORARY","THAN","THE","THEN",
        "THREAD","TIMESTAMP","TIME","TO","TOPLEVEL","TRACE","TRACING",
        "TRANSACTION","TRANSITIONAL","TRIGGER","TRIGGERS","TRUE","TRUNCATE",
        "TX","TYPE","UB2","UBA","UID","UNARCHIVED","UNDO","UNION","UNIQUE",
        "UNLIMITED","UNLOCK","UNRECOVERABLE","UNTIL","UNUSABLE","UNUSED",
        "UPDATABLE","UPDATE","USAGE","USE","USER","USING","VALIDATE","VALIDATION",
        "VALUE","VALUES","VARCHAR","VARCHAR2","VARYING","VIEW","WHEN","WHENEVER",
        "WHERE","WITH","WITHOUT","WORK","WRITE","WRITEDOWN","WRITEUP","XID","YEAR"
    };
    
    public static String[] funcionesReservadas = {
        
    };
    
    public static String[] funcionesEspecialesReservadas = {
        
    };
    
    
    @Override
    public String[] getPalabrasReservadas() {
        return palabrasClavesReservadas;
    }

    @Override
    public String[] getFuncionesReservadas() {
        return funcionesReservadas;
    }

    @Override
    public String[] getFuncionesEspecialesReservadas() {
        return funcionesEspecialesReservadas;
    }

    @Override
    public String getClaseDriver() {
        return claseDriver;
    }

    @Override
    public String getPuertoDriver() {
        return puertoDriver;
    }

    @Override
    public boolean equals(TiposBasesDeDatos.TIPO_BASE_DATOS tipoBaseDatos) {
        return tipoBaseDatos.equals(TiposBasesDeDatos.TIPO_BASE_DATOS.ORACLE);
    }
    
    @Override
    public String toString() {
        return nombrePresentable;
    }
    
    @Override
    public boolean tieneComentarioDeLinea() {
        return true;
    }

    @Override
    public boolean tieneComentarioDeBloque() {
        return true;
    }
    
    @Override
    public String[] getPalabrasClaveEjecutarConsulta() {
        return palabrasClaveEjecutarConsulta;
    }
    
    @Override
    public String[][] getPalabrasClaveDelimitadoresConsulta() {
        return palabrasClaveDelimitadoresConsulta;
    }
}
