package it.almaviva.smartroadeventssaver.cassandra.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.*;

import static org.springframework.data.cassandra.core.mapping.Embedded.OnEmpty.USE_NULL;

@NoArgsConstructor
@AllArgsConstructor
@Data
public abstract class CassandraEventEntity {

    @Embedded(onEmpty = USE_NULL)
    @PrimaryKeyColumn(name = "action_id", type = PrimaryKeyType.PARTITIONED)
    protected ActionID actionID;

    @PrimaryKeyColumn(ordinal = 1, type = PrimaryKeyType.CLUSTERED)
    protected long priority;

    @PrimaryKeyColumn(ordinal = 2, type = PrimaryKeyType.CLUSTERED)
    protected boolean certified;
}
