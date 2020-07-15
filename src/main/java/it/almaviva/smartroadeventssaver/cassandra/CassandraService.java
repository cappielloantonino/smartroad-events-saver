package it.almaviva.smartroadeventssaver.cassandra;

import it.almaviva.smartroadeventssaver.cassandra.entity.CassandraDenm;
import it.almaviva.smartroadeventssaver.cassandra.entity.CassandraEventEntity;
import it.almaviva.smartroadeventssaver.cassandra.repository.CassandraDenmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CassandraService {

    @Autowired
    CassandraDenmRepository cassandraDenmRepository;

    public boolean write(CassandraEventEntity cassandraEvent) {
        // try
        cassandraDenmRepository.insert((CassandraDenm) cassandraEvent);

        // catch

        return true;
    }
}
