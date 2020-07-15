package it.almaviva.smartroadeventssaver.cassandra.repository;

import it.almaviva.smartroadeventssaver.cassandra.entity.ActionID;
import it.almaviva.smartroadeventssaver.cassandra.entity.CassandraEventEntity;
import it.almaviva.smartroadeventssaver.cassandra.entity.TestEntity;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CassandraEventsRepository extends CassandraRepository<CassandraEventEntity, ActionID> {
}
