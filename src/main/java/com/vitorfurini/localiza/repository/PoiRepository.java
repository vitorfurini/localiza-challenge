package com.vitorfurini.localiza.repository;

import com.vitorfurini.localiza.entity.Poi;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface PoiRepository extends MongoRepository<Poi, Long> {

    @Override
    List<Poi> findAll();

    List<Poi> findByNameIgnoreCase(String name);
}
