package com.gmail.shelkovich.anton.model;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "t_rooms")
public class Room implements Serializable {

    private static final long serialVersionUID = -2505155146584093004L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "f_id")
    private Long id;

    @Column(name = "f_number")
    private Integer number;


    @Column(name = "f_description")
    private String description;

    @Column(name = "f_price")
    private BigDecimal price;

    @ManyToOne
    @JoinColumn(name = "f_hotel_id")
    private Hotel hotel;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "room")
    private RoomInfo roomInfo;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "t_room_guests",
        joinColumns = @JoinColumn(name = "f_room_id"),
        inverseJoinColumns = @JoinColumn(name = "f_guest_id"))
    private Set<Guest> guests = new HashSet<>();

    @Override
    public String toString() {
        return "Room{" +
                "id=" + id +
                ", number=" + number +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", hotel=" + hotel +
                ", roomInfo=" + roomInfo +
                ", guests=" + guests +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public RoomInfo getRoomInfo() {
        return roomInfo;
    }

    public void setRoomInfo(RoomInfo roomInfo) {
        roomInfo.setRoom(this);
        this.roomInfo = roomInfo;
    }

    public Set<Guest> getGuests() {
        return guests;
    }

    public void setGuests(Set<Guest> guests) {
        this.guests = guests;
    }
}
