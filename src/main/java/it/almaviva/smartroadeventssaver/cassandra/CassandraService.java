package it.almaviva.smartroadeventssaver.cassandra;

import it.almaviva.smartroadeventssaver.cassandra.entity.CassandraEventEntity;
import it.almaviva.smartroadeventssaver.cassandra.repository.CassandraEventsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CassandraService {

    @Autowired
    CassandraEventsRepository cassandraRepository;

    public boolean write(CassandraEventEntity cassandraEvent) {
        // try
        cassandraRepository.insert(cassandraEvent);

        // catch

        return true;
    }
}
