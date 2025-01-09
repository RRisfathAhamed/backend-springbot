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
public class PasswordResetToken {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, unique = true)
    private String token;

    @OneToOne(targetEntity = JobSeeker.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "job_seeker_id")
    private JobSeeker jobSeeker;

    @Column(nullable = false)
    private LocalDateTime expiryDate;

    public PasswordResetToken(String token, JobSeeker jobSeeker) {
        this.token = token;
        this.jobSeeker = jobSeeker;
        this.expiryDate = LocalDateTime.now().plusHours(1); // Token valid for 24 hours
    }

    public boolean isExpired() {
        return expiryDate.isBefore(LocalDateTime.now());
    }
}
