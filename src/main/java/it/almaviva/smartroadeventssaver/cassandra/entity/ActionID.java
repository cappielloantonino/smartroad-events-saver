package it.almaviva.smartroadeventssaver.cassandra.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.*;

import javax.xml.datatype.DatatypeConfigurationException;
import java.io.Serializable;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActionID {
    private long station_id;
    private long sequence_number;
}