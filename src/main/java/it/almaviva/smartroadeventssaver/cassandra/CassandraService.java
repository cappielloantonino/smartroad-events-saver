package it.almaviva.smartroadeventssaver.cassandra;

import it.almaviva.smartroadeventssaver.cassandra.entity.CassandraDenm;
import it.almaviva.smartroadeventssaver.cassandra.entity.CassandraEventEntity;
import it.almaviva.smartroadeventssaver.cassandra.entity.CassandraIvim;
import it.almaviva.smartroadeventssaver.cassandra.repository.CassandraDenmRepository;
import it.almaviva.smartroadeventssaver.cassandra.repository.CassandraIvimRepository;
import it.almaviva.smartroadeventssaver.utils.CassandraException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CassandraService {

    @Autowired
    CassandraDenmRepository cassandraDenmRepository;

    @Autowired
    CassandraIvimRepository cassandraIvimRepository;


    public void write(CassandraEventEntity cassandraEvent) throws CassandraException {
        try {
            log.info("write - CassandraEventEntity: {}", cassandraEvent);
            if (cassandraEvent instanceof CassandraDenm)
                cassandraDenmRepository.insert((CassandraDenm) cassandraEvent);
            else if (cassandraEvent instanceof CassandraIvim)
                cassandraIvimRepository.insert((CassandraIvim) cassandraEvent);
        }
        catch(Exception e) {
            throw new CassandraException("CassandraService ERRORE: scrittura su DB fallita", e);
        }
    }
}
