package it.almaviva.smartroadeventssaver.cassandra;

import it.almaviva.smartroadeventssaver.cassandra.entity.TestEntity;
import it.almaviva.smartroadeventssaver.cassandra.repository.TestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cassandra")
public class CassandraController {
    @Autowired
    TestRepository testRepository;

    @GetMapping("/save")
    public Boolean save(@RequestParam String name, @RequestParam Integer age) {
        System.out.println("save");
        testRepository.insert(new TestEntity(name, age));
        return true;
    }

    @GetMapping("/count")
    public long count() {
        System.out.println("count");

        return testRepository.count();
    }
}
