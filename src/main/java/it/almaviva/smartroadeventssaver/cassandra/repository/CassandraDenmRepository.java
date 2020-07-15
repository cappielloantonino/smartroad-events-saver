package it.almaviva.smartroadeventssaver.cassandra.repository;

import it.almaviva.smartroadeventssaver.cassandra.entity.CassandraDenm;
import it.almaviva.smartroadeventssaver.cassandra.model.CassandraDenmPK;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CassandraDenmRepository extends CassandraRepository<CassandraDenm, CassandraDenmPK> {
}
