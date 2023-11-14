package com.vitorfurini.localiza.repository;

import com.vitorfurini.localiza.entity.Posicao;
import com.vitorfurini.localiza.service.impl.PositionServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PositionRepositoryTest {

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
    public void getAll() {

        positionService.findAll();

        verify(repository, times(1)).findAll();
    }

    @Test
    public void searchByLicensePlate() {

        when(repository.findByLicensePlate(any())).thenReturn(listMock());

        positionService.findByLicensePlate(anyString());

        verify(repository, times(1)).findByLicensePlate(anyString());

    }

    public List<Posicao> listMock() {

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
