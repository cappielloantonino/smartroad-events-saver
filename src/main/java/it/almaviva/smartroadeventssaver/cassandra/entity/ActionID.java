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
//@PrimaryKeyClass
public class ActionID implements Serializable {

    //@PrimaryKeyColumn(name = "station_id", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    @CassandraType(type = CassandraType.Name.BIGINT)
    private long stationID;

    //@PrimaryKeyColumn(name = "sequence_number", ordinal = 1, type = PrimaryKeyType.PARTITIONED)
    @CassandraType(type = CassandraType.Name.BIGINT)
    private long sequenceNumber;
}
