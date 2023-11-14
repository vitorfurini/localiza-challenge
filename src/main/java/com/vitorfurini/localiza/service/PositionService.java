package com.vitorfurini.localiza.service;

import com.vitorfurini.localiza.dto.PosicoesDto;
import com.vitorfurini.localiza.entity.Posicao;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PositionService {
    ResponseEntity<String> save(MultipartFile file);

    List<Posicao> findAll();

    Posicao saveNewPosition(PosicoesDto posicoesDto);

    List<Posicao> findByLicensePlate(String licensePlate);
}
