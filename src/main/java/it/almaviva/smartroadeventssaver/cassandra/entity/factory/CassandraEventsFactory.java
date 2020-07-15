package it.almaviva.smartroadeventssaver.cassandra.entity.factory;

import it.almaviva.etsi.Etsi;
import it.almaviva.smartroadeventssaver.cassandra.entity.CassandraEventEntity;


public abstract class CassandraEventsFactory {
    public static CassandraEventsFactory getFactory(Etsi etsi) {
        switch (etsi.getMessageType()) {
            case DENM: return new CassandraDenmFactory(etsi);
            case IVIM: return new CassandraIvimFactory(etsi);
            default: return null;
        }
    }

    public abstract CassandraEventEntity createCassandraEvent(String payload);
}
