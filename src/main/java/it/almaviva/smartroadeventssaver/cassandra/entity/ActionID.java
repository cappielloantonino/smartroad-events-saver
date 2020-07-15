package it.almaviva.smartroadeventssaver.cassandra.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.*;

import javax.xml.datatype.DatatypeConfigurationException;
import java.io.Serializable;

@Data
@AllArgsConstructor
@UserDefinedType(value = "actionid")
public class ActionID implements Serializable {

    @CassandraType(type = CassandraType.Name.BIGINT, userTypeName = "station_id")
    private long stationID;

    @CassandraType(type = CassandraType.Name.BIGINT, userTypeName = "sequence_number")
    private long sequenceNumber;
}
