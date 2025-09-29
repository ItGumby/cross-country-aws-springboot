package io.itgumby.repository;

import io.itgumby.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {
    List<Team> findBySchoolNameContainingIgnoreCase(String schoolName);

    @Query("SELECT t FROM Team t WHERE t.schoolSize = ?1")
    List<Team> findBySchoolSize(String schoolSize);

    @Query("SELECT t FROM Team t WHERE t.conference = ?1")
    List<Team> findByConference(String conference);
}
