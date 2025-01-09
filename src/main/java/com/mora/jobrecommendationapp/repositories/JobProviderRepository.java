package com.mora.jobrecommendationapp.repositories;

import com.mora.jobrecommendationapp.entities.JobProvider;
import com.mora.jobrecommendationapp.entities.JobSeeker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobProviderRepository extends JpaRepository<JobProvider, Long> {
    JobProvider findByUserName(String userName);

    JobProvider findByEmail(String email);
}
