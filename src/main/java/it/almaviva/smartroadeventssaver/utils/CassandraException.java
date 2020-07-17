package it.almaviva.smartroadeventssaver.utils;


public class CassandraException extends Exception {

    public CassandraException() {
    }

    public CassandraException(String message) {
        super(message);
    }

    public CassandraException(String message, Throwable cause) {
        super(message, cause);
    }

    public CassandraException(Throwable cause) {
        super(cause);
    }
}
