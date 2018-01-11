package com.gmail.shelkovich.anton.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "t_hotels")
public class Hotel implements Serializable {

    private static final long serialVersionUID = -1464290852879899630L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "f_id")
    private Long id;

    @Column(name = "f_name")
    private String name;

    @Column(name = "f_rating")
    private Integer rating;

    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL)
    private final List<Room> rooms = new ArrayList<Room>(){
        @Override
        public boolean add(Room room) {
            room.setHotel(Hotel.this);
            return super.add(room);
        }
    };

    @Override
    public String toString() {
        return "Hotel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", rating=" + rating +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public List<Room> getRooms() {
        return rooms;
    }


}
