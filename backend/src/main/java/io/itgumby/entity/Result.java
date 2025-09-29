package io.itgumby.entity;

import jakarta.persistence.*;
import java.time.Duration;

@Entity
@Table(name = "results")
public class Result {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "race_id", nullable = false)
    private Race race;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "athlete_id", nullable = false)
    private Athlete athlete;

    @Column(nullable = false)
    private Duration time; // Race time

    private Integer individualPlace;
    private Integer teamPlace;

    // Constructors
    public Result() {}

    public Result(Race race, Athlete athlete, Duration time, Integer individualPlace, Integer teamPlace) {
        this.race = race;
        this.athlete = athlete;
        this.time = time;
        this.individualPlace = individualPlace;
        this.teamPlace = teamPlace;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Race getRace() { return race; }
    public void setRace(Race race) { this.race = race; }

    public Athlete getAthlete() { return athlete; }
    public void setAthlete(Athlete athlete) { this.athlete = athlete; }

    public Duration getTime() { return time; }
    public void setTime(Duration time) { this.time = time; }

    public Integer getIndividualPlace() { return individualPlace; }
    public void setIndividualPlace(Integer individualPlace) { this.individualPlace = individualPlace; }

    public Integer getTeamPlace() { return teamPlace; }
    public void setTeamPlace(Integer teamPlace) { this.teamPlace = teamPlace; }
}
