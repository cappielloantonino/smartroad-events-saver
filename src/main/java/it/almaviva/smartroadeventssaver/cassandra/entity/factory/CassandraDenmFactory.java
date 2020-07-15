package it.almaviva.smartroadeventssaver.cassandra.entity.factory;

import it.almaviva.etsi.Etsi;
import it.almaviva.etsi.common.SequenceNumber;
import it.almaviva.etsi.common.TimestampIts;
import it.almaviva.etsi.common.ValidityDuration;
import it.almaviva.etsi.denm.Denm;
import it.almaviva.etsi.header.StationID;
import it.almaviva.smartroadeventssaver.cassandra.entity.ActionID;
import it.almaviva.smartroadeventssaver.cassandra.entity.CassandraDenm;
import it.almaviva.smartroadeventssaver.cassandra.entity.CassandraEventEntity;
import it.almaviva.smartroadeventssaver.cassandra.model.CassandraDenmPK;

public class CassandraDenmFactory extends CassandraEventsFactory {

    private Etsi etsi;

    public CassandraDenmFactory(Etsi etsiObj) {
        this.etsi = etsiObj;
    }

    public CassandraEventEntity createCassandraEvent(String payload) {
        Denm denm = (Denm) etsi;
        StationID stationID = denm.getHeader().getStationID(); // long
        SequenceNumber sequenceNumber = denm.getDenm().getManagement().getActionID().getSequenceNumber(); // long
        TimestampIts detectionTime = denm.getDenm().getManagement().getDetectionTime(); // long
        TimestampIts referenceTime = denm.getDenm().getManagement().getReferenceTime(); // long
        ValidityDuration validityDuration = denm.getDenm().getManagement().getValidityDuration(); // long
        Long priority = denm.getPriority();
        boolean certified = denm.isCertified();

        /*
        CassandraDenm cassandraDenm = new CassandraDenm();
        cassandraDenm.setActionID(new ActionID(stationID.value, sequenceNumber.value));
        cassandraDenm.setDetectionTimestamp(detectionTime.value);
        cassandraDenm.setReferenceTimestamp(referenceTime.value);
        cassandraDenm.setValidityDuration(validityDuration.value);
        cassandraDenm.setPriority(priority);
        cassandraDenm.setCertified(certified);
        cassandraDenm.setPayload(payload);
        */

        CassandraDenm cassandraDenm = new CassandraDenm();
        CassandraDenmPK denmPK = new CassandraDenmPK();
        denmPK.setActionID(new ActionID(stationID.value, sequenceNumber.value));
        denmPK.setCertified(certified);
        denmPK.setPriority(priority);
        denmPK.setDetectionTimestamp(detectionTime.value);
        denmPK.setReferenceTimestamp(referenceTime.value);
        denmPK.setValidityDuration(validityDuration.value);
        cassandraDenm.setDenmPK(denmPK);
        cassandraDenm.setPayload(payload);

        return cassandraDenm;
    }
}
