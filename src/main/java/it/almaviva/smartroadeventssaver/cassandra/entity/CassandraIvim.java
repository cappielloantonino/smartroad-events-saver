package it.almaviva.smartroadeventssaver.cassandra.entity;

import lombok.Data;
import org.springframework.data.cassandra.core.mapping.*;

@Data
@Table("ivim")
public class CassandraIvim extends CassandraEventEntity {
    /*
    @Frozen
    @Column
    @CassandraType(type = CassandraType.Name.UDT, userTypeName = "actionid")
    @PrimaryKey
    private ActionID actionid;

    @Column
    private long priority;

    @Column
    private boolean certified;
    */

    @Column
    private long validfrom;

    @Column
    private long validto;

    @Column
    private String payload;
}