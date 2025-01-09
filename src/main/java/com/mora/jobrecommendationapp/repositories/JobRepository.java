package com.mora.jobrecommendationapp.repositories;

import com.mora.jobrecommendationapp.entities.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {
    @Query("SELECT COUNT(j) FROM Job j")
    long countJobs();
}
