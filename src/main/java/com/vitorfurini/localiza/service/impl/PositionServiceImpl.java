package com.vitorfurini.localiza.service.impl;

import com.vitorfurini.localiza.dto.PosicoesDto;
import com.vitorfurini.localiza.entity.Posicao;
import com.vitorfurini.localiza.repository.PositionRepository;
import com.vitorfurini.localiza.service.PositionService;
import com.vitorfurini.localiza.utils.CSVHelper;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@Log4j2
@Service
public class PositionServiceImpl implements PositionService {

    PositionRepository repository;

    private final ModelMapper modelMapper;

    public PositionServiceImpl(PositionRepository repository, ModelMapper modelMapper) {
        this.repository = repository;
        this.modelMapper = modelMapper;
    }

    public ResponseEntity<String> save(MultipartFile file) {
        String message = "";
        try {
            List<Posicao> positions = CSVHelper.csvToPosition(file.getInputStream());
            repository.saveAll(positions);
            message = "Upload do arquivo: " + file.getOriginalFilename() + " executado com sucesso!";
            return ResponseEntity.status(HttpStatus.OK).body(message);
        } catch (IOException e) {
            throw new RuntimeException("falha ao armazenar dados csv: " + e.getMessage());
        }
    }
    public List<Posicao> findAll() {
        return repository.findAll();
    }

    public List<Posicao> findByDateBetween(Date begin, Date end) {
        return repository.findByDateBetween(begin, end);
    }

    public List<Posicao> findByDateBetweenAndLicensePlate(Date begin, Date end, String licensePlate) {
        return repository.findByDateBetweenAndLicensePlateIgnoreCase(begin, end, licensePlate);
    }

    @Transactional
    public Posicao saveNewPosition(PosicoesDto posicoesDTO) {

        return repository.save(modelMapper.map(posicoesDTO, Posicao.class));
    }

    @Override
    public List<Posicao> findByLicensePlate(String licensePlate) {
        var posicao = this.repository.findByLicensePlate(licensePlate);

        if (posicao.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        } else
            return posicao;
    }
}
