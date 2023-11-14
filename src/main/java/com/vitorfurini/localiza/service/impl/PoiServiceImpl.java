package com.vitorfurini.localiza.service.impl;

import com.vitorfurini.localiza.dto.PoiDto;
import com.vitorfurini.localiza.entity.Poi;
import com.vitorfurini.localiza.exception.BusinessRulesException;
import com.vitorfurini.localiza.mapper.PoiMapper;
import com.vitorfurini.localiza.repository.PoiRepository;
import com.vitorfurini.localiza.service.PoiService;
import com.vitorfurini.localiza.utils.CSVHelper;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.List;

@Log4j2
@Service
public class PoiServiceImpl implements PoiService {

    PoiRepository repository;

    private final PoiMapper poiMapper;

    public PoiServiceImpl(PoiRepository repository, PoiMapper poiMapper) {
        this.repository = repository;
        this.poiMapper = poiMapper;
    }

    public ResponseEntity<String> save(MultipartFile file) {
        String message = "";
        try {
            List<Poi> pois = CSVHelper.csvFileToPoiDocument(file.getInputStream());
            repository.saveAll(pois);
            message = "Upload do arquivo: " + file.getOriginalFilename() + " executado com sucesso!";
            return ResponseEntity.status(HttpStatus.OK).body(message);
        } catch (IOException ex) {
            log.error(message, ex);
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE,
                    "falha ao ler o arquivo csv: " + ex.getMessage());
        }
    }

    @Transactional
    public Poi addPOI(PoiDto poidto) {

        if (findByName(poidto.getName()).isEmpty()) {
            throw new BusinessRulesException(
                    String.format("POI %s já cadastrado!", poidto.getName()));
        }
        return repository.save(poiMapper.toEntity(poidto));
    }

    public List<Poi> findAll() {
        return repository.findAll();
    }

    public List<Poi> findByName(String poiName) {

        var poi = this.repository.findByNameIgnoreCase(poiName);

        if (poi.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        } else
            return poi;
    }
}
