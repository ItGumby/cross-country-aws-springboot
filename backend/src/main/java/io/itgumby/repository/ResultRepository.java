package io.itgumby.repository;

import io.itgumby.entity.Result;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ResultRepository extends JpaRepository<Result, Long> {
    @Query("SELECT r FROM Result r WHERE r.athlete.id = ?1 ORDER BY r.race.date DESC")
    List<Result> findByAthleteIdOrderByDateDesc(Long athleteId);

    @Query("SELECT r FROM Result r WHERE r.race.id = ?1 ORDER BY r.individualPlace")
    List<Result> findByRaceIdOrderByPlace(Long raceId);

    @Query("SELECT r FROM Result r WHERE r.athlete.team.id = ?1 ORDER BY r.race.date DESC")
    List<Result> findByTeamIdOrderByDateDesc(Long teamId);

    @Query("SELECT MIN(r.time) FROM Result r WHERE r.athlete.id = ?1")
    java.time.Duration findPersonalBest(Long athleteId);
}
