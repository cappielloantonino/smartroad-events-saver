package it.almaviva.smartroadeventssaver.cassandra.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.*;

@NoArgsConstructor
@Data
public abstract class CassandraEventEntity {
    @Frozen
    @CassandraType(type = CassandraType.Name.UDT, userTypeName = "actionid")
    @PrimaryKeyColumn(name = "action_id", ordinal = 5, type = PrimaryKeyType.PARTITIONED)
    protected ActionID actionID;

    @PrimaryKeyColumn(ordinal = 5, type = PrimaryKeyType.CLUSTERED)
    protected long priority;

    @PrimaryKeyColumn(ordinal = 6, type = PrimaryKeyType.CLUSTERED)
    protected boolean certified;
}
