package com.gmail.shelkovich.anton.model;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "t_room_infos")
public class RoomInfo implements Serializable {

    private static final long serialVersionUID = 2671800590826931818L;
    @Id
    @GenericGenerator(name = "gena", strategy = "foreign", parameters = @Parameter(name = "property", value = "room"))
    @GeneratedValue(generator = "gena")
    @Column(name = "f_room_id")
    private Long id;

    @Column(name = "f_max_people")
    private Integer maxPeople;

    @Column(name = "f_has_shower")
    private Boolean hasShower;

    @Column(name = "f_has_tv")
    private Boolean hasTv;

    @OneToOne
    @PrimaryKeyJoinColumn
    private Room room;

    @Override
    public String toString() {
        return "RoomInfo{" +
                "id=" + id +
                ", maxPeople=" + maxPeople +
                ", hasShower=" + hasShower +
                ", hasTv=" + hasTv +
                '}';
    }

    public Room getRoom() {
        return room;
    }

    void setRoom(Room room) {
        this.room = room;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getMaxPeople() {
        return maxPeople;
    }

    public void setMaxPeople(Integer maxPeople) {
        this.maxPeople = maxPeople;
    }

    public Boolean getHasShower() {
        return hasShower;
    }

    public void setHasShower(Boolean hasShower) {
        this.hasShower = hasShower;
    }

    public Boolean getHasTv() {
        return hasTv;
    }

    public void setHasTv(Boolean hasTv) {
        this.hasTv = hasTv;
    }
}
