package com.vitorfurini.localiza.rest;

import com.vitorfurini.localiza.dto.PosicoesDto;
import com.vitorfurini.localiza.entity.Posicao;
import com.vitorfurini.localiza.service.PositionService;
import com.vitorfurini.localiza.utils.CSVHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import javax.validation.Valid;

@Api(tags = "Position Controller")
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/position")
public class PositionController {

    @Autowired
    private PositionService positionService;

    @PostMapping("/upload")
    @ApiOperation("Recurso disponível para carga do documento Position na base de dados a partir de um arquivo CSV")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        String message;

        if (CSVHelper.hasCSVFormat(file)) {
            message = String.valueOf(positionService.save(file));
            return ResponseEntity.status(HttpStatus.OK).body(message);
        } else {
            message = "Faça o upload do seu arquivo csv!";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
        }
    }

    @GetMapping("/list")
    @ApiOperation("Recurso disponível para listar todos os POIS cadastrados na base de dados.")
    public ResponseEntity<List<Posicao>> getPois() {
        List<Posicao> pois = positionService.findAll();

        if (CollectionUtils.isEmpty(pois)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(pois, HttpStatus.OK);
    }

    @GetMapping("findByLicensePlate/{licensePlate}")
    @ApiOperation("Recurso disponível para buscar a posição de um veiculo pela placa")
    public ResponseEntity<Object> findByLicensePlate(@RequestParam String licensePlate) {

        var response = positionService.findByLicesePlate(licensePlate);

        return ResponseEntity.ok().body(response);
    }

    @PostMapping
    @ApiOperation("Recurso disponível para cadastrar uma nova posição na base de dados")
    public ResponseEntity<?> createPosition(@RequestBody @Valid PosicoesDto posicoesDto) {

        var response = positionService.saveNewPosition(posicoesDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
