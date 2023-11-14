package com.vitorfurini.localiza.repository;

import com.vitorfurini.localiza.entity.Posicao;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface PositionRepository extends MongoRepository<Posicao, Long> {

    @Override
    List<Posicao> findAll();

    List<Posicao> findByLicensePlate(String licensePlate);

    List<Posicao> findByDateBetween(Date begin, Date end);

    List<Posicao> findByDateBetweenAndLicensePlateIgnoreCase(Date begin, Date end, String licensePlate);
}
