package it.almaviva.smartroadeventssaver.cassandra.entity.factory;

import it.almaviva.etsi.Etsi;
import it.almaviva.etsi.common.TimestampIts;
import it.almaviva.etsi.header.StationID;
import it.almaviva.etsi.ivim.Ivim;
import it.almaviva.etsi.ivim.container.IviIdentificationNumber;
import it.almaviva.smartroadeventssaver.cassandra.entity.ActionID;
import it.almaviva.smartroadeventssaver.cassandra.entity.CassandraEventEntity;
import it.almaviva.smartroadeventssaver.cassandra.entity.CassandraIvim;


class CassandraIvimFactory extends CassandraEventsFactory {

    private Etsi etsi;

    public CassandraIvimFactory(Etsi etsiObj) {
        this.etsi = etsiObj;
    }

    public CassandraEventEntity createCassandraEvent(String payload) {
        Ivim ivim = (Ivim) etsi;
        StationID stationID = ivim.getHeader().getStationID();
        IviIdentificationNumber iviIdentificationNumber = ivim.getIvi().getMandatory().getIviIdentificationNumber();
        TimestampIts validFrom = ivim.getIvi().getMandatory().getValidFrom();
        TimestampIts validTo = ivim.getIvi().getMandatory().getValidTo();
        long priority = ivim.getPriority();
        boolean certified = ivim.isCertified();

        CassandraIvim cassandraIvim = new CassandraIvim();
        cassandraIvim.setActionID(new ActionID(stationID.value, iviIdentificationNumber.value));
        cassandraIvim.setValidFrom(validFrom.value);
        cassandraIvim.setValidTo(validTo.value);
        cassandraIvim.setPriority(priority);
        cassandraIvim.setCertified(certified);
        cassandraIvim.setPayload(payload);

        return cassandraIvim;
    }
}
