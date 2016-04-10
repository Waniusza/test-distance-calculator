package com.sokolow.janusz.calcdistance.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Waniusza
 */
//@Getter
//@Setter
//@NoArgsConstructor
@XmlRootElement
public class City {
    @XmlElement(name="id")
    int id;
    
    @XmlElement(name="name")
    String name;
    
//    @XmlElement(name="latitude")
//    Double latitude;
//    
//    @XmlElement(name="longitude")
//    Double longitude;

    public City() {
    }

    public City(int id, String name) {
        this.id = id;
        this.name = name;
    }

//    @Override
//    public String toString() {
//        return "City id: " + this.id + " name: " + this.name;
//    }
}
