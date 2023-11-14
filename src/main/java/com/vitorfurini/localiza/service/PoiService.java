package com.vitorfurini.localiza.service;

import com.vitorfurini.localiza.dto.PoiDto;
import com.vitorfurini.localiza.entity.Poi;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PoiService {


    ResponseEntity<String> save(MultipartFile file);

    List<Poi> findAll();

    Poi addPOI(PoiDto poidto);

    List<Poi> findByName(String name);
}
