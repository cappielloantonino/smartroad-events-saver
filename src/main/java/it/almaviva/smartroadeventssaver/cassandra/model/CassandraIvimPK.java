package it.almaviva.smartroadeventssaver.cassandra.model;


import it.almaviva.smartroadeventssaver.cassandra.entity.ActionID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.Frozen;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyClass;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@PrimaryKeyClass
public class CassandraIvimPK implements Serializable {
    @Frozen
    @CassandraType(type = CassandraType.Name.UDT, userTypeName = "actionid")
    @PrimaryKeyColumn(name = "action_id", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    private ActionID actionID;

    @PrimaryKeyColumn(ordinal = 1, type = PrimaryKeyType.CLUSTERED)
    private long priority;

    @PrimaryKeyColumn(ordinal = 2, type = PrimaryKeyType.CLUSTERED)
    private boolean certified;

    //

    @PrimaryKeyColumn(ordinal = 3, type = PrimaryKeyType.CLUSTERED)
    private long validfrom;

    @PrimaryKeyColumn(ordinal = 4, type = PrimaryKeyType.CLUSTERED)
    private long validto;
}
