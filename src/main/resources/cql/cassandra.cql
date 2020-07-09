CREATE KEYSPACE IF NOT EXISTS smartroad
    WITH replication = {'class': 'SimpleStrategy', 'replication_factor' : 3};

USE smartroad;

CREATE TABLE tableTest (
    name  varchar,
    age int,

    PRIMARY KEY (name)
);