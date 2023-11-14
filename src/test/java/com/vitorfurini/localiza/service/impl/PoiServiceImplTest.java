package com.vitorfurini.localiza.service.impl;

import com.vitorfurini.localiza.dto.PoiDto;
import com.vitorfurini.localiza.entity.Poi;
import com.vitorfurini.localiza.exception.BusinessRulesException;
import com.vitorfurini.localiza.mapper.PoiMapper;
import com.vitorfurini.localiza.repository.PoiRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PoiServiceImplTest {

    @Mock
    private PoiRepository repository;

    @Mock
    private PoiServiceImpl poiService;

    @Mock
    private PoiMapper poiMapper;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        this.poiService = new PoiServiceImpl(repository, poiMapper);
    }

    @Test
    public void sholdSuccessWhenUploadCsvFile() {

        Path path = Paths.get("src/main/resources/others/base_pois_def.csv");
        String name = "posicoes.txt";
        String originalFileName = "posicoes.txt";
        String contentType = "text/csv";
        byte[] content = null;
        try {
            content = Files.readAllBytes(path);
        } catch (final IOException ignored) {
        }
        MultipartFile result = new MockMultipartFile(name,
                originalFileName, contentType, content);

        ResponseEntity<String> responseEntity = poiService.save(result);

        assertEquals(responseEntity.toString(), "<200 OK OK,Upload do arquivo: posicoes.txt executado com sucesso!,"
                + "[]>");
    }

    @Test
    public void createPoi() {
        when(repository.save(getMock())).thenReturn(getMock());

        poiService.addPOI(getDtoMock());

        verify(repository, times(1)).save(any());
    }

    @Test(expected = BusinessRulesException.class)
    public void shouldSuccessWhenCreatePoi() {
        when(repository.findByNameIgnoreCase(getDtoMock().getName())).thenReturn(listPoi());

        poiService.addPOI(getDtoMock());

        verify(repository, times(1)).save(any());
    }

    @Test
    public void shouldSuccessWhenFindAll() {
        when(repository.findAll()).thenReturn(listPoi());

        poiService.findAll();

        verify(repository, atLeastOnce()).findAll();

    }

    @Test
    public void shouldSuccessWhenFindByName() {


        when(repository.findByNameIgnoreCase("PONTO 1")).thenReturn(listPoi());

        poiService.findByName("PONTO 1");

        verify(repository, atLeastOnce()).findByNameIgnoreCase("PONTO 1");

    }

    @Test(expected = ResponseStatusException.class)
    public void shouldNotFoundWhenFindByName() {


        when(repository.findByNameIgnoreCase("PONTO132132")).thenReturn(listPoi());

        List<Poi> responseStatus = poiService.findByName("PONTO1321");

        verify(responseStatus.get(1).equals(HttpStatus.NOT_FOUND));

    }


    public Poi getMock(){
        Poi poi = new Poi();
        poi.setRadius(300);
        poi.setLatitude(-25.56742701740896);
        poi.setName("A procura de um emprego");
        poi.setLongitude(-51.47653363645077);
        poi.setId("6244d10e920cb429d33b2bc2");
        return poi;
    }

    public Poi getExistsMock(){
        Poi poi = new Poi();
        poi.setRadius(300);
        poi.setLatitude(-25.56742701740896);
        poi.setName("PONTO 1");
        poi.setLongitude(-51.47653363645077);
        poi.setId("655311188613d90883f6e7cb");
        return poi;
    }

    public PoiDto getDtoMock(){
        PoiDto poiDto = new PoiDto();
        poiDto.setRadius(300);
        poiDto.setLatitude(-25.56742701740896);
        poiDto.setName("PONTO 1");
        poiDto.setLongitude(-51.47653363645077);
        return poiDto;
    }

    List<Poi> listPoi(){
        List<Poi> list = new ArrayList<>();
        Poi poi = new Poi();
        poi.setRadius(300);
        poi.setLatitude(-25.56742701740896);
        poi.setName("A procura de um emprego");
        poi.setLongitude(-51.47653363645077);
        poi.setId("6244d10e920cb429d33b2bc2");
        list.add(poi);
        return list;
    }

    List<PoiDto> listPoiDto(){
        List<PoiDto> list = new ArrayList<>();
        PoiDto poi = new PoiDto();
        poi.setRadius(300);
        poi.setLatitude(-25.56742701740896);
        poi.setName("A procura de um emprego");
        poi.setLongitude(-51.47653363645077);
        list.add(poi);
        return list;
    }



}
