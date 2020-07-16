package it.almaviva.smartroadeventssaver.cassandra.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Table("ivim_events")
public class CassandraIvim extends CassandraEventEntity {

    @PrimaryKeyColumn(name = "valid_from", ordinal = 3, type = PrimaryKeyType.CLUSTERED)
    private long validFrom;

    @PrimaryKeyColumn(name = "valid_to", ordinal = 4, type = PrimaryKeyType.CLUSTERED)
    private long validTo;

    @Column
    private String payload;


    public CassandraIvim(ActionID actionID, long priority, boolean certified, long validFrom, long validTo, String payload) {
        super(actionID, priority, certified);
        this.validFrom = validFrom;
        this.validTo = validTo;
        this.payload = payload;
    }
}
