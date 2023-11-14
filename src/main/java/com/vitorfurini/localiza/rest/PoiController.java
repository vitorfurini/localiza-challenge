package com.vitorfurini.localiza.rest;

import com.vitorfurini.localiza.dto.PoiDto;
import com.vitorfurini.localiza.entity.Poi;
import com.vitorfurini.localiza.responses.Response;
import com.vitorfurini.localiza.service.PoiService;
import com.vitorfurini.localiza.utils.CSVHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;

@Api(tags = "POI Controller")
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/poi")
public class PoiController {

    @Autowired
    private PoiService poiService;

    @PostMapping("/upload")
    @ApiOperation("Recurso disponível para carga do documento Poi na base de dados a partir de um arquivo CSV")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        String message = "";

        if (CSVHelper.hasCSVFormat(file)) {
                 message = String.valueOf(poiService.save(file));
            return ResponseEntity.status(HttpStatus.OK).body(message);
        } else {
            message = "Faça o upload do seu arquivo csv!";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
        }
    }

    @GetMapping("/list")
    @ApiOperation("Recurso disponível para listar todos os POIS cadastrados na base de dados.")
    public ResponseEntity<List<Poi>> getPois() {
        List<Poi> pois = poiService.findAll();

        if (CollectionUtils.isEmpty(pois)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(pois, HttpStatus.OK);
    }

    @PostMapping(path = "/create")
    @ApiOperation("Recurso disponível para cadastrar um POI na base de dados.")
    public ResponseEntity<Poi> create(@RequestBody @Valid PoiDto poidto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errors = new ArrayList<>();
            bindingResult.getAllErrors().forEach(erro -> errors.add(erro.getDefaultMessage()));
            return ResponseEntity.badRequest().body(new Response<Poi>(errors).getData());
        }

        return ResponseEntity.ok(new Response<>(this.poiService.addPOI(poidto)).getData());
    }

    @GetMapping(path = "/findByName/{name}")
    @ApiOperation("Recurso disponível para buscar um POI pelo nome na base de dados.")
    public ResponseEntity<Object> findByName(@RequestParam @Valid String name) {

        var response = poiService.findByName(name);

        return ResponseEntity.ok().body(response);
    }
}
