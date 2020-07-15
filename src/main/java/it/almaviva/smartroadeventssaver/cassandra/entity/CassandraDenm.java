package it.almaviva.smartroadeventssaver.cassandra.entity;

import it.almaviva.smartroadeventssaver.cassandra.model.CassandraDenmPK;
import lombok.Data;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.*;

@Data
@Table("denm")
public class CassandraDenm extends CassandraEventEntity {
    /*
    @Frozen
    @Column
    @CassandraType(type = CassandraType.Name.UDT, userTypeName = "actionid")
    @PrimaryKey
    private ActionId actionid;

    @Column
    private long priority;

    @Column
    private boolean certified;

    @PrimaryKeyColumn(ordinal = 2, type = PrimaryKeyType.CLUSTERED)
    private long detectionTimestamp;

    @PrimaryKeyColumn(ordinal = 3, type = PrimaryKeyType.CLUSTERED)
    private long referenceTimestamp;

    @PrimaryKeyColumn(ordinal = 4, type = PrimaryKeyType.CLUSTERED)
    private long validityDuration;

    @Column
    private String payload;
    */

    @PrimaryKey
    private CassandraDenmPK denmPK;
}
