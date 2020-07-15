package it.almaviva.smartroadeventssaver.cassandra.model;

import com.datastax.oss.driver.api.core.type.DataType;
import it.almaviva.smartroadeventssaver.cassandra.entity.ActionID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@PrimaryKeyClass
public class CassandraDenmPK implements Serializable {

    @Frozen
    @CassandraType(type = CassandraType.Name.UDT, userTypeName = "actionid")
    @PrimaryKeyColumn(name = "action_id", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    private ActionID actionID;

    @PrimaryKeyColumn(ordinal = 1, type = PrimaryKeyType.CLUSTERED)
    private long priority;

    @PrimaryKeyColumn(ordinal = 2, type = PrimaryKeyType.CLUSTERED)
    private boolean certified;

    //

    @PrimaryKeyColumn(name = "detection_time", ordinal = 3, type = PrimaryKeyType.CLUSTERED)
    private long detectionTimestamp;

    @PrimaryKeyColumn(name = "reference_time", ordinal = 4, type = PrimaryKeyType.CLUSTERED)
    private long referenceTimestamp;

    @PrimaryKeyColumn(name = "validity_duration", ordinal = 5, type = PrimaryKeyType.CLUSTERED)
    private long validityDuration;
}
