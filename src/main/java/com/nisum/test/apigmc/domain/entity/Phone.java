package com.nisum.test.apigmc.domain.entity;

import javax.persistence.*;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "PHONE")
public class Phone extends AuditEntity<UUID> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_PHONE")
    private Long id;

    @Column(name = "PHONE_NUMBER")
    private String number;

    @Column(name = "CITY_CODE",length = 5)
    private String citycode;

    @Column(name = "COUNTRY_CODE",length = 5)
    private String countrycode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_USER", nullable = false)
    private User user;

    public Phone() {
    }

    public Phone(String number, String citycode, String countrycode, User user) {
        this.number = number;
        this.citycode = citycode;
        this.countrycode = countrycode;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getCitycode() {
        return citycode;
    }

    public void setCitycode(String citycode) {
        this.citycode = citycode;
    }

    public String getCountrycode() {
        return countrycode;
    }

    public void setCountrycode(String countrycode) {
        this.countrycode = countrycode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Phone phone = (Phone) o;
        return Objects.equals(id, phone.id) && Objects.equals(number, phone.number) && Objects.equals(citycode, phone.citycode) && Objects.equals(countrycode, phone.countrycode) && Objects.equals(user, phone.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, number, citycode, countrycode, user);
    }

    @Override
    public String toString() {
        return "Phone{" +
                "id=" + id +
                ", number='" + number + '\'' +
                ", citycode='" + citycode + '\'' +
                ", countrycode='" + countrycode + '\'' +
                ", user=" + user +
                '}';
    }
}
