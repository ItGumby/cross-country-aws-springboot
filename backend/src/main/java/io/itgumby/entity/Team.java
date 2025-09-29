package io.itgumby.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "teams")
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String schoolName;

    private String schoolSize; // 5A-1A
    private String conference;

    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Athlete> athletes;

    // Constructors
    public Team() {}

    public Team(String schoolName, String schoolSize, String conference) {
        this.schoolName = schoolName;
        this.schoolSize = schoolSize;
        this.conference = conference;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getSchoolName() { return schoolName; }
    public void setSchoolName(String schoolName) { this.schoolName = schoolName; }

    public String getSchoolSize() { return schoolSize; }
    public void setSchoolSize(String schoolSize) { this.schoolSize = schoolSize; }

    public String getConference() { return conference; }
    public void setConference(String conference) { this.conference = conference; }

    public List<Athlete> getAthletes() { return athletes; }
    public void setAthletes(List<Athlete> athletes) { this.athletes = athletes; }
}
