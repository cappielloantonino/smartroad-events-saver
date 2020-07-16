package it.almaviva.smartroadeventssaver.cassandra;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.config.SchemaAction;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;


@Configuration
@EnableCassandraRepositories
public class CassandraConfig extends AbstractCassandraConfiguration {

    @Value("${cassandra.contact-points}")
    private String contactPoints;

    @Value("${cassandra.port}")
    private int port;

    @Value("${cassandra.keyspace}")
    private String keyspace;

    @Value("${cassandra.local-datacenter}")
    private String localDatacenter;

    @Value("${cassandra.schema-action}")
    private String schemaAction;


    @Override
    public String getContactPoints() {
        return this.contactPoints;
    }

    @Override
    protected int getPort() {
        return this.port;
    }

    @Override
    public String getKeyspaceName() {
        return this.keyspace;
    }

    @Override
    protected String getLocalDataCenter() {
        return this.localDatacenter;
    }

    @Override
    public SchemaAction getSchemaAction() {
        return SchemaAction.valueOf(this.schemaAction);
    }
}