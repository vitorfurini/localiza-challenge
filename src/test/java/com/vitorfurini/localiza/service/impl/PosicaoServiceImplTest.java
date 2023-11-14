package com.vitorfurini.localiza.service.impl;

import com.vitorfurini.localiza.dto.PosicoesDto;
import com.vitorfurini.localiza.entity.Posicao;
import com.vitorfurini.localiza.repository.PositionRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PosicaoServiceImplTest {

    @Mock
    private PositionRepository repository;

    @Mock
    private PositionServiceImpl positionService;

    @Mock
    private ModelMapper modelMapper;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        this.positionService = new PositionServiceImpl(repository, modelMapper);
    }

    @Test
    public void createPosition() {
        when(repository.save(getMock())).thenReturn(getMock());

        positionService.saveNewPosition(getDtoMock());

        verify(repository, times(1)).save(any());
    }

    @Test
    public void sholdSuccessWhenUploadCsvFile() {

        Path path = Paths.get("src/main/resources/others/posicoes.csv");
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

        ResponseEntity<String> responseEntity = positionService.save(result);

        assertEquals(responseEntity.toString(), "<200 OK OK,Upload do arquivo: posicoes.txt executado com sucesso!,"
                + "[]>");
    }

    @Test
    public void shouldSuccessWhenFindAll() {
        when(repository.findAll()).thenReturn(listMock());

        positionService.findAll();

        verify(repository, atLeastOnce()).findAll();

    }

    @Test
    public void shouldSuccessWhenFindByDateBetween() throws ParseException {

        String dataBegin = "2023-11-14";
        String dataEnd = "2023-12-31";
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");

        Date dateStart = formato.parse(dataBegin);
        Date dateEnd = formato.parse(dataEnd);

        when(repository.findByDateBetween(dateStart, dateEnd)).thenReturn(listMock());

        positionService.findByDateBetween(dateStart, dateEnd);

        verify(repository, atLeastOnce()).findByDateBetween(dateStart, dateEnd);

    }

    @Test
    public void shouldSuccessWhenFindByDateBetweenAndLicensePlate() throws ParseException {

        String dataBegin = "2023-11-14";
        String dataEnd = "2023-12-31";
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");

        Date dateStart = formato.parse(dataBegin);
        Date dateEnd = formato.parse(dataEnd);

        when(repository.findByDateBetweenAndLicensePlateIgnoreCase(dateStart, dateEnd, "PLACA001")).thenReturn(listMock());

        positionService.findByDateBetweenAndLicensePlate(dateStart, dateEnd, "PLACA001");

        verify(repository, atLeastOnce()).findByDateBetweenAndLicensePlateIgnoreCase(dateStart, dateEnd, "PLACA001");

    }

    @Test
    public void shouldSuccessWhenFindByLicensePlate() {


        when(repository.findByLicensePlate("PLACA001")).thenReturn(listMock());

        positionService.findByLicensePlate("PLACA001");

        verify(repository, atLeastOnce()).findByLicensePlate("PLACA001");

    }


    public PosicoesDto getDtoMock() {
        PosicoesDto posicoesDto = new PosicoesDto();

        posicoesDto.setDate(new Date());
        posicoesDto.setIgnition(false);
        posicoesDto.setLongitude(-51.47653363645077);
        posicoesDto.setLatitude(-25.56742701740896);
        posicoesDto.setLicensePlate("TESTE001");
        posicoesDto.setVelocity(0);

        return posicoesDto;
    }

    public Posicao getMock() {
        Posicao posicao = new Posicao();
        posicao.setDate(new Date());
        posicao.setIgnition(false);
        posicao.setLongitude(-51.47653363645077);
        posicao.setLatitude(-25.56742701740896);
        posicao.setVelocity(0);
        posicao.setLicensePlate("TESTE001");
        posicao.setId("6244d10e920cb429d33b2bc2");

        return posicao;
    }

    List<Posicao> listMock() {
        List<Posicao> list = new ArrayList<>();
        Posicao posicao = new Posicao();
        posicao.setDate(new Date());
        posicao.setIgnition(false);
        posicao.setLongitude(-51.47653363645077);
        posicao.setLatitude(-25.56742701740896);
        posicao.setVelocity(0);
        posicao.setLicensePlate("TESTE001");
        posicao.setId("6244d10e920cb429d33b2bc2");

        list.add(posicao);
        return list;
    }


}
