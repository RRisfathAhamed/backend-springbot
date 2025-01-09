package com.mora.jobrecommendationapp.repositories;

import com.mora.jobrecommendationapp.entities.Application;
import com.mora.jobrecommendationapp.entities.Job;
import com.mora.jobrecommendationapp.entities.JobSeeker;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface ApplicationRepository extends JpaRepository<Application, Long> {

    @Query("SELECT COUNT(a) FROM Application a WHERE a.job.jobId = :jobId")
    Long countByJobId(@Param("jobId") Long jobId);

    // Method to count applications by job seeker ID
    @Query("SELECT COUNT(a) FROM Application a WHERE a.jobSeeker.jobSeekerId = :jobSeekerId")
    long countByJobSeekerId(@Param("jobSeekerId") Long jobSeekerId);

    // Method to find jobs by job seeker ID
    @Query("SELECT a.job FROM Application a WHERE a.jobSeeker.jobSeekerId = :jobSeekerId")
    List<Job> findJobsByJobSeekerId(@Param("jobSeekerId") Long jobSeekerId);

    @Query("SELECT CASE WHEN COUNT(a) > 0 THEN true ELSE false END FROM Application a WHERE a.job.jobId = :jobId AND a.jobSeeker.jobSeekerId = :jobSeekerId")
    boolean findByJobSeekerIdAndJobId(@Param("jobSeekerId") Long jobSeekerId,@Param("jobId") Long jobId );

    @Query("SELECT a.jobSeeker FROM Application a WHERE a.job.jobId = :jobId")
    List<JobSeeker> findApplicationsByJobId(Long jobId);

//    @Query("DELETE FROM Application a WHERE a.jobSeeker.jobSeekerId = :jobSeekerId AND a.job.jobId = :jobId")
//    void deleteByJobSeekerIdAndJobId(Long jobSeekerId, Long jobId);
    @Modifying
    @Transactional
    @Query("DELETE FROM Application a WHERE a.jobSeeker.jobSeekerId = :jobSeekerId AND a.job.jobId = :jobId")
    void deleteByJobSeekerIdAndJobId(Long jobSeekerId, Long jobId);

}
