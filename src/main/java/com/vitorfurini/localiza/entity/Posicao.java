package com.vitorfurini.localiza.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
@Document(collection = "posicao")
public class Posicao implements GeoLocation, Comparable<Posicao> {

    @Id
    private String id;
    private String licensePlate;
    private Date date;
    private int velocity;
    private Double latitude;
    private Double longitude;
    private boolean ignition;

    @Override
    public int compareTo(Posicao o) {
        int compare = this.licensePlate.compareTo(o.getLicensePlate());

        if (compare == 0) {
            compare = this.getDate().compareTo(o.getDate());
        }
        return compare;
    }
}
