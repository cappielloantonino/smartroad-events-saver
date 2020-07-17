CREATE KEYSPACE IF NOT EXISTS smartroad
    WITH replication = {'class': 'SimpleStrategy', 'replication_factor' : 3};

USE smartroad;

CREATE TABLE IF NOT EXISTS smartroad.denm_events (
    station_id bigint,
    sequence_number bigint,
    detection_time bigint,
    reference_time bigint,
    validity_duration bigint,
    priority bigint,
    certified boolean,
    payload text,

    PRIMARY KEY ((station_id, sequence_number), detection_time, reference_time, validity_duration, priority, certified)
);

CREATE TABLE IF NOT EXISTS smartroad.ivim_events (
    station_id bigint,
    sequence_number bigint,
    valid_from bigint,
    valid_to bigint,
    priority bigint,
    certified boolean,
    payload text,

    PRIMARY KEY ((station_id, sequence_number), valid_from, valid_to, priority, certified)
);