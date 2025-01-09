package com.mora.jobrecommendationapp.repositories;

import com.mora.jobrecommendationapp.entities.JobProvider;
import com.mora.jobrecommendationapp.entities.PasswordResetTokenJobProvider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PasswordResetTokenJobProviderRepository extends JpaRepository<PasswordResetTokenJobProvider, Long> {

    PasswordResetTokenJobProvider findByToken(String token);

    Optional<PasswordResetTokenJobProvider> findByJobProvider(JobProvider jobProvider);
}
