package com.mora.jobrecommendationapp.repositories;

import com.mora.jobrecommendationapp.entities.JobProvider;
import com.mora.jobrecommendationapp.entities.JobSeeker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface JobSeekerRepository extends JpaRepository<JobSeeker, Long> {
    JobSeeker findByUserName(String userName);

    JobSeeker findByEmail(String email);
}
