package io.itgumby.service;

import io.itgumby.entity.Team;
import io.itgumby.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class TeamService {

    @Autowired
    private TeamRepository teamRepository;

    public List<Team> getAllTeams() {
        return teamRepository.findAll();
    }

    public Optional<Team> getTeamById(Long id) {
        return teamRepository.findById(id);
    }

    public List<Team> searchTeams(String schoolName) {
        return teamRepository.findBySchoolNameContainingIgnoreCase(schoolName);
    }

    public Team saveTeam(Team team) {
        return teamRepository.save(team);
    }

    public void deleteTeam(Long id) {
        teamRepository.deleteById(id);
    }

    public List<Team> getTeamsBySchoolSize(String schoolSize) {
        return teamRepository.findBySchoolSize(schoolSize);
    }

    public List<Team> getTeamsByConference(String conference) {
        return teamRepository.findByConference(conference);
    }
}
