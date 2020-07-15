package it.almaviva.smartroadeventssaver.cassandra.model;

import it.almaviva.smartroadeventssaver.cassandra.entity.ActionID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.Frozen;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CassandraPK {

    @Frozen
    @CassandraType(type = CassandraType.Name.UDT, userTypeName = "actionid")
    @PrimaryKeyColumn(name = "action_id", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    protected ActionID actionID;

    @PrimaryKeyColumn(ordinal = 1, type = PrimaryKeyType.CLUSTERED)
    protected long priority;

    @PrimaryKeyColumn(ordinal = 2, type = PrimaryKeyType.CLUSTERED)
    protected boolean certified;
}
