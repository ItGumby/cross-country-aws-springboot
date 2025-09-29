package io.itgumby.repository;

import io.itgumby.entity.Race;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface RaceRepository extends JpaRepository<Race, Long> {
    List<Race> findByMeetNameContainingIgnoreCase(String meetName);

    @Query("SELECT r FROM Race r WHERE r.date BETWEEN ?1 AND ?2 ORDER BY r.date DESC")
    List<Race> findByDateBetween(LocalDate startDate, LocalDate endDate);

    @Query("SELECT r FROM Race r WHERE r.division = ?1")
    List<Race> findByDivision(String division);

    @Query("SELECT r FROM Race r ORDER BY r.date DESC")
    List<Race> findAllOrderByDateDesc();
}
