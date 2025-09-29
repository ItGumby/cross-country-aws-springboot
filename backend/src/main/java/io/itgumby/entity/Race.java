package io.itgumby.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "races")
public class Race {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String meetName;

    @Column(nullable = false)
    private LocalDate date;

    private String location;
    private String division;
    private Double distance; // in miles
    private String weatherConditions;

    @OneToMany(mappedBy = "race", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Result> results;

    // Constructors
    public Race() {}

    public Race(String meetName, LocalDate date, String location, String division, Double distance) {
        this.meetName = meetName;
        this.date = date;
        this.location = location;
        this.division = division;
        this.distance = distance;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getMeetName() { return meetName; }
    public void setMeetName(String meetName) { this.meetName = meetName; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getDivision() { return division; }
    public void setDivision(String division) { this.division = division; }

    public Double getDistance() { return distance; }
    public void setDistance(Double distance) { this.distance = distance; }

    public String getWeatherConditions() { return weatherConditions; }
    public void setWeatherConditions(String weatherConditions) { this.weatherConditions = weatherConditions; }

    public List<Result> getResults() { return results; }
    public void setResults(List<Result> results) { this.results = results; }
}
