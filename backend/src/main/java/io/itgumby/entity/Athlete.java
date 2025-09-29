package io.itgumby.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "athletes")
public class Athlete {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private Integer grade; // 9-12

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;

    @OneToMany(mappedBy = "athlete", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Result> results;

    // Constructors
    public Athlete() {}

    public Athlete(String name, Integer grade, Team team) {
        this.name = name;
        this.grade = grade;
        this.team = team;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Integer getGrade() { return grade; }
    public void setGrade(Integer grade) { this.grade = grade; }

    public Team getTeam() { return team; }
    public void setTeam(Team team) { this.team = team; }

    public List<Result> getResults() { return results; }
    public void setResults(List<Result> results) { this.results = results; }
}
