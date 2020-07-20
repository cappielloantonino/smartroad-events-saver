package it.almaviva.smartroadeventssaver.cassandra;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.almaviva.etsi.denm.Denm;
import it.almaviva.etsi.ivim.Ivim;
import it.almaviva.smartroadeventssaver.cassandra.entity.CassandraDenm;
import it.almaviva.smartroadeventssaver.cassandra.entity.CassandraEventEntity;
import it.almaviva.smartroadeventssaver.cassandra.entity.CassandraIvim;
import it.almaviva.smartroadeventssaver.cassandra.entity.factory.CassandraEventsFactory;
import it.almaviva.smartroadeventssaver.cassandra.repository.CassandraDenmRepository;
import it.almaviva.smartroadeventssaver.cassandra.repository.CassandraIvimRepository;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;


import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CassandraTest {
    @Mock
    CassandraDenmRepository cassandraDenmRepositoryMock;

    @Mock
    CassandraIvimRepository cassandraIvimRepositoryMock;

    /*@Spy
    CassandraDenmRepository cassandraDenmRepositorySpy;*/

    @InjectMocks
    CassandraService cassandraService;

    public void manageDenmFromAmqpMock(String denmMessage) throws JsonProcessingException{
        ObjectMapper objectMapper = new ObjectMapper();
        Denm obj = objectMapper.readValue(denmMessage, Denm.class);
        System.out.println("Denm object: " + obj);

        // scrittura cassandra su tabella denm
        CassandraEventEntity cassandraEventEntity = CassandraEventsFactory.getFactory(obj).createCassandraEvent(denmMessage);
        CassandraDenm cassandraDenm = (CassandraDenm)  cassandraEventEntity;
        System.out.println("cassandraDenm: " + cassandraDenm);

        when(cassandraDenmRepositoryMock.insert(any(CassandraDenm.class))).thenReturn(cassandraDenm);
        CassandraDenm cassandraInsert = cassandraDenmRepositoryMock.insert(cassandraDenm);
        System.out.println("cassandraInsert: " + cassandraInsert);
        verify(cassandraDenmRepositoryMock).insert(cassandraDenm);
        Assertions.assertEquals(cassandraDenm, cassandraInsert);
    }

    public void manageIvimFromAmqpMock(String ivimMessage) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Ivim obj = objectMapper.readValue(ivimMessage, Ivim.class);
        System.out.println("Ivim object: " + obj);

        // scrittura cassandra su tabella ivim
        CassandraEventEntity cassandraEventEntity = CassandraEventsFactory.getFactory(obj).createCassandraEvent(ivimMessage);
        CassandraIvim cassandraIvim = (CassandraIvim)  cassandraEventEntity;
        System.out.println("cassandraIvim: " + cassandraIvim);

        when(cassandraIvimRepositoryMock.insert(any(CassandraIvim.class))).thenReturn(cassandraIvim);
        CassandraIvim cassandraInsert = cassandraIvimRepositoryMock.insert(cassandraIvim);
        System.out.println("cassandraInsert: " + cassandraInsert);
        verify(cassandraIvimRepositoryMock).insert(cassandraIvim);
        Assertions.assertEquals(cassandraIvim, cassandraInsert);
    }

    @Test
    public void writingDenmTest() {
        try {
            this.manageDenmFromAmqpMock("{\"messageType\":\"DENM\",\"header\":{\"protocolVersion\":{\"value\":1},\"messageID\":{\"value\":1},\"stationID\":{\"value\":1234567}},\"denm\":{\"management\":{\"actionID\":{\"originatingStationID\":{\"value\":20},\"sequenceNumber\":{\"value\":30}},\"detectionTime\":{\"value\":45000000000},\"referenceTime\":{\"value\":1},\"termination\":null,\"eventPosition\":{\"latitude\":{\"value\":40487111},\"longitude\":{\"value\":-79494789},\"positionConfidenceEllipse\":{\"semiMajorConfidence\":{\"value\":500},\"semiMinorConfidence\":{\"value\":400},\"semiMajorOrientation\":{\"value\":10}},\"altitude\":{\"altitudeValue\":{\"value\":2000},\"altitudeConfidence\":\"alt_000_02\"}},\"relevanceDistance\":null,\"relevanceTrafficDirection\":null,\"validityDuration\":{\"value\":600},\"transmissionInterval\":{\"value\":1},\"stationType\":{\"value\":0}},\"situation\":{\"informationQuality\":{\"value\":1},\"eventType\":{\"causeCode\":{\"value\":3},\"subCauseCode\":{\"value\":0}},\"linkedCause\":null,\"eventHistory\":null},\"location\":{\"eventSpeed\":{\"speedValue\":{\"value\":0},\"speedConfidence\":{\"value\":1}},\"eventPositionHeading\":{\"headingValue\":{\"value\":0},\"headingConfidence\":{\"value\":10}},\"traces\":[[{\"pathPosition\":{\"deltaLatitude\":{\"value\":20},\"deltaLongitude\":{\"value\":20},\"deltaAltitude\":{\"value\":12800}},\"pathDeltaTime\":{\"value\":1}},{\"pathPosition\":{\"deltaLatitude\":{\"value\":22},\"deltaLongitude\":{\"value\":22},\"deltaAltitude\":{\"value\":12800}},\"pathDeltaTime\":null}]],\"roadType\":\"urban_NoStructuralSeparationToOppositeLanes\"},\"alacarte\":{\"lanePosition\":null,\"impactReduction\":{\"heightLonCarrLeft\":{\"value\":1},\"heightLonCarrRight\":{\"value\":1},\"posLonCarrLeft\":{\"value\":1},\"posLonCarrRight\":{\"value\":1},\"positionOfPillars\":[{\"value\":1}],\"posCentMass\":{\"value\":63},\"wheelBaseVehicle\":{\"value\":1},\"turningRadius\":{\"value\":1},\"posFrontAx\":{\"value\":1},\"positionOfOccupants\":{\"row1LeftOccupied\":true,\"row1RightOccupied\":true,\"row1MidOccupied\":true,\"row1NotDetectable\":true,\"row1NotPresent\":true,\"row2LeftOccupied\":true,\"row2RightOccupied\":true,\"row2MidOccupied\":false,\"row2NotDetectable\":false,\"row2NotPresent\":false,\"row3LeftOccupied\":false,\"row3RightOccupied\":false,\"row3MidOccupied\":false,\"row3NotDetectable\":false,\"row3NotPresent\":true,\"row4LeftOccupied\":false,\"row4RightOccupied\":false,\"row4MidOccupied\":false,\"row4NotDetectable\":false,\"row4NotPresent\":true},\"vehicleMass\":{\"value\":20},\"requestResponseIndication\":\"response\"},\"externalTemperature\":{\"value\":1},\"roadWorks\":{\"lightBarSirenInUse\":{\"lightBarActivated\":true,\"sirenActivated\":true},\"closedLanes\":{\"hardShoulderStatus\":\"availableForStopping\",\"drivingLaneStatus\":[false,true,true]},\"restriction\":[{\"value\":0}],\"speedLimit\":{\"value\":20},\"incidentIndication\":{\"causeCode\":{\"value\":0},\"subCauseCode\":{\"value\":0}},\"recommendedPath\":[{\"latitude\":{\"value\":20},\"longitude\":{\"value\":20},\"positionConfidenceEllipse\":{\"semiMajorConfidence\":{\"value\":1},\"semiMinorConfidence\":{\"value\":1},\"semiMajorOrientation\":{\"value\":0}},\"altitude\":{\"altitudeValue\":{\"value\":200},\"altitudeConfidence\":\"alt_000_02\"}}],\"startingPointSpeedLimit\":null,\"trafficFlowRule\":null,\"referenceDenms\":null},\"positioningSolution\":\"noPositioningSolution\",\"stationaryVehicle\":{\"stationarySince\":\"equalOrGreater15Minutes\",\"stationaryCause\":{\"causeCode\":{\"value\":3},\"subCauseCode\":{\"value\":0}},\"carryingDangerousGoods\":null,\"numberOfOccupants\":{\"value\":30},\"vehicleIdentification\":{\"wminumber\":{},\"vds\":{}},\"energyStorageType\":{\"hydrogenStorage\":false,\"electricEnergyStorage\":false,\"liquidPropaneGas\":false,\"compressedNaturalGas\":false,\"diesel\":false,\"gasoline\":true,\"ammonia\":false}}}}}");
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void readingDenmCountTest() {
        long nullCount = 0;
        when(cassandraDenmRepositoryMock.count()).thenReturn(nullCount);
        long cassandraCount = cassandraDenmRepositoryMock.count();
        Assertions.assertEquals(nullCount, cassandraCount);
        verify(cassandraDenmRepositoryMock).count();
    }

    @Test
    public void readingDenmFindTest() {
        List<CassandraDenm> cassandraVoid = new ArrayList<>();
        when(cassandraDenmRepositoryMock.findAll()).thenReturn(cassandraVoid);
        List<CassandraDenm> cassandraFind = cassandraDenmRepositoryMock.findAll();
        Assertions.assertEquals(cassandraVoid, cassandraFind);
        verify(cassandraDenmRepositoryMock).findAll();
    }

    @Test
    @Disabled
    public void writingIvimTest() {
        try {
            this.manageIvimFromAmqpMock("");
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void readingIvimCountTest() {
        long nullCount = 0;
        when(cassandraIvimRepositoryMock.count()).thenReturn(nullCount);
        long cassandraCount = cassandraIvimRepositoryMock.count();
        Assertions.assertEquals(nullCount, cassandraCount);
        verify(cassandraIvimRepositoryMock).count();
    }

    @Test
    public void readingIvimFindTest() {
        List<CassandraIvim> cassandraVoid = new ArrayList<>();
        when(cassandraIvimRepositoryMock.findAll()).thenReturn(cassandraVoid);
        List<CassandraIvim> cassandraFind = cassandraIvimRepositoryMock.findAll();
        Assertions.assertEquals(cassandraVoid, cassandraFind);
        verify(cassandraIvimRepositoryMock).findAll();
    }
}
