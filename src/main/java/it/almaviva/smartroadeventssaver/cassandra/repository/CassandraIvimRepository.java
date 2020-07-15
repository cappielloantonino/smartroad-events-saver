package it.almaviva.smartroadeventssaver.cassandra.repository;


import it.almaviva.smartroadeventssaver.cassandra.entity.CassandraIvim;
import it.almaviva.smartroadeventssaver.cassandra.model.CassandraIvimPK;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CassandraIvimRepository extends CassandraRepository<CassandraIvim, CassandraIvimPK> {
}
