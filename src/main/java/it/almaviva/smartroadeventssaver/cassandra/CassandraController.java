package it.almaviva.smartroadeventssaver.cassandra;

import it.almaviva.smartroadeventssaver.cassandra.entity.CassandraDenm;
import it.almaviva.smartroadeventssaver.cassandra.repository.CassandraDenmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cassandra")
public class CassandraController {
    @Autowired
    CassandraDenmRepository cassandraDenmRepository;

    @GetMapping("/save")
    public Boolean save(@RequestParam String name, @RequestParam Integer age) {
        System.out.println("save");

        CassandraDenm cassandraDenm = new CassandraDenm();

        cassandraDenmRepository.insert(cassandraDenm);

        return true;
    }

    @GetMapping("/count")
    public long count() {
        System.out.println("count");

        return cassandraDenmRepository.count();
    }
}
