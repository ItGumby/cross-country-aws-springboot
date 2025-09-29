package io.itgumby.repository;

import io.itgumby.entity.Athlete;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AthleteRepository extends JpaRepository<Athlete, Long> {
    List<Athlete> findByNameContainingIgnoreCase(String name);

    @Query("SELECT a FROM Athlete a WHERE a.team.id = ?1")
    List<Athlete> findByTeamId(Long teamId);

    @Query("SELECT a FROM Athlete a WHERE a.grade = ?1")
    List<Athlete> findByGrade(Integer grade);
}
