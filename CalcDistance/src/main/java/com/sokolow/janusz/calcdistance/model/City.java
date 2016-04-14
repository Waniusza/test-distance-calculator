package com.sokolow.janusz.calcdistance.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Waniusza
 */
@Getter
@Setter
@XmlRootElement
public class City {

    @XmlElement(name = "id")
    long id;

    @XmlElement(name = "name")
    String name;

    @XmlElement(name = "latitude")
    double latitude;

    @XmlElement(name = "longitude")
    double longitude;

    public City() {
    }

    public City(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public City(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return "City " + this.name + " ( " + this.latitude + " : " + this.longitude + " )";
    }

}
