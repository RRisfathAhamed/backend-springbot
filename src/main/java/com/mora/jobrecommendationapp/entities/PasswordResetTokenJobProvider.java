package com.mora.jobrecommendationapp.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PasswordResetTokenJobProvider {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, unique = true)
    private String token;

    @OneToOne(targetEntity = JobProvider.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "job_provider_id")
    private JobProvider jobProvider;

    @Column(nullable = false)
    private LocalDateTime expiryDate;

    public PasswordResetTokenJobProvider(String token, JobProvider jobProvider) {
        this.token = token;
        this.jobProvider = jobProvider;
        this.expiryDate = LocalDateTime.now().plusHours(1); // Token valid for 1 hour
    }

    public boolean isExpired() {
        return expiryDate.isBefore(LocalDateTime.now());
    }
}
