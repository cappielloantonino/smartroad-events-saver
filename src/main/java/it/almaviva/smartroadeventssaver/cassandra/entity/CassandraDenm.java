package it.almaviva.smartroadeventssaver.cassandra.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table("denm_events")
public class CassandraDenm extends CassandraEventEntity {

    @PrimaryKeyColumn(name = "detection_time", ordinal = 3, type = PrimaryKeyType.CLUSTERED)
    private long detectionTimestamp;

    @PrimaryKeyColumn(name = "reference_time", ordinal = 4, type = PrimaryKeyType.CLUSTERED)
    private long referenceTimestamp;

    @PrimaryKeyColumn(name = "validity_duration", ordinal = 5, type = PrimaryKeyType.CLUSTERED)
    private long validityDuration;

    @Column
    private String payload;


    public CassandraDenm(ActionID actionID, long priority, boolean certified, long detectionTimestamp, long referenceTimestamp, long validityDuration, String payload) {
        super(actionID, priority, certified);
        this.detectionTimestamp = detectionTimestamp;
        this.referenceTimestamp = referenceTimestamp;
        this.validityDuration = validityDuration;
        this.payload = payload;
    }
}
