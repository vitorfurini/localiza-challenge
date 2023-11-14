package com.vitorfurini.localiza.utils;

import com.vitorfurini.localiza.entity.Poi;
import com.vitorfurini.localiza.entity.Posicao;
import com.vitorfurini.localiza.exception.FileUploadException;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CSVHelper {

    private static final Logger log = LoggerFactory.getLogger(CSVHelper.class);

    public static String TYPE = "text/csv";

    public static boolean hasCSVFormat(MultipartFile file) {
        return TYPE.equals(file.getContentType());
    }

    public static List<Poi> csvFileToPoiDocument(InputStream is) {
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
                CSVParser csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim())) {

            List<Poi> pois = new ArrayList<>();

            Iterable<CSVRecord> csvRecords = csvParser.getRecords();

            for (CSVRecord csvRecord : csvRecords) {
                String name = csvRecord.get("nome");
                Integer radius = Integer.parseInt(csvRecord.get("raio"));
                Double latitude = Double.valueOf(csvRecord.get("latitude"));
                Double longitude = Double.valueOf(csvRecord.get("longitude"));

                Poi poi = new Poi();
                poi.setName(name);
                poi.setRadius(radius);
                poi.setLatitude(latitude);
                poi.setLongitude(longitude);

                pois.add(poi);
            }

            return pois;
        } catch (IOException e) {
            throw new FileUploadException("Falha ao analisar o arquivo CSV: " + e.getMessage());
        }
    }

    public static List<Posicao> csvToPosition(InputStream is) {
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
                CSVParser csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {

            List<Posicao> positions = new ArrayList<>();
            Iterable<CSVRecord> csvRecords = csvParser.getRecords();

            for (CSVRecord csvRecord : csvRecords) {
                String plate = csvRecord.get("placa");
                int velocity = Integer.parseInt(csvRecord.get("velocidade"));
                Double longitude = Double.valueOf(csvRecord.get("longitude"));
                Double latitude = Double.valueOf(csvRecord.get("latitude"));
                boolean ignition = Boolean.parseBoolean(csvRecord.get("ignicao"));

                Date date = new Date();
                try {
                    date = new Date(csvRecord.get("data_posicao"));
                } catch (Exception ex) {
                    log.error("Erro no formato da data", ex);
                }

                Posicao position = new Posicao();
                position.setLicensePlate(plate);
                position.setVelocity(velocity);
                position.setIgnition(ignition);
                position.setLatitude(latitude);
                position.setLongitude(longitude);
                position.setDate(date);

                positions.add(position);
            }
            return positions;
        } catch (IOException e) {
            throw new FileUploadException("Falha ao analisar o arquivo CSV: " + e.getMessage());
        }
    }
}
