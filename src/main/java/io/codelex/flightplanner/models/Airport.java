package io.codelex.flightplanner.models;



import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;


import java.util.Objects;
@Entity
@Table(name = "airport")
public class Airport {
    @NotBlank
    private String country;
    @NotBlank
    private String city;
    @Id
    @Column(name = "airport")
    @NotBlank
    private String airport;

    public Airport() {
    }

    public Airport(String country, String city, String airport) {
        this.country = country;
        this.city = city;
        this.airport = airport;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAirport() {
        return airport;
    }

    public void setAirport(String airport) {
        this.airport = airport;

    }

    public boolean equals(Airport airport) {
        return this.country.trim().equalsIgnoreCase(airport.country.trim())
                && this.city.trim().equalsIgnoreCase(airport.city.trim())
                && this.airport.trim().equalsIgnoreCase(airport.airport.trim());
    }

    @Override
    public int hashCode() {
        return Objects.hash(country, city, airport);
    }

    @Override
    public String toString() {
        return "Airport{" +
                "country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", airport='" + airport + '\'' +
                '}';
    }
    public boolean containsPhrase(String phrase) {
        return this.country.toLowerCase().trim().contains(phrase.toLowerCase().trim())
                || this.city.toLowerCase().trim().contains(phrase.toLowerCase().trim())
                || this.airport.toLowerCase().trim().contains(phrase.toLowerCase().trim());
    }
}
