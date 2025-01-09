package com.mora.jobrecommendationapp.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "jobprovider")
public class JobProvider {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long jobProviderId;

    @Column
    private String companyName;

    @Column
    private String industry;

    @Column
    private String email;

    @Column
    private String userName;

    @Column
    private String password;

    @Column
    private String phoneNumber;

    @Column
    private String address;

    @Column
    private Date registeredDate;

    @OneToMany(mappedBy = "jobProvider", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore // Prevents serialization of the jobs list when JobProvider is serialized
    private List<Job> jobs = new ArrayList<>();
    @Column
    private String securityQuestion;

    @Column
    private String securityAnswerHash;

}


