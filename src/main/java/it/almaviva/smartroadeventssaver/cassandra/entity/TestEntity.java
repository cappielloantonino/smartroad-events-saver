package it.almaviva.smartroadeventssaver.cassandra.entity;

import lombok.Data;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;


@Data
@Table("tableTest")
public class TestEntity {
    @Column
    @PrimaryKey
    private String name;

    @Column
    private Integer age;


    protected TestEntity() {}

    public TestEntity(
            String name,
            Integer age)
    {
        this.name = name;
        this.age = age;
    }
}