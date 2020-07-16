package it.almaviva.smartroadeventssaver.cassandra.repository;

import it.almaviva.smartroadeventssaver.cassandra.entity.CassandraIvim;
import org.springframework.data.cassandra.repository.MapIdCassandraRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CassandraIvimRepository extends MapIdCassandraRepository<CassandraIvim> {
}
