package com.mora.jobrecommendationapp.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "jobseeker")
public class JobSeeker {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long jobSeekerId;

    @Column(length = 50)
    private String firstName;

    @Column(length = 50)
    private String lastName;

    @Column(length = 50, unique = true)
    private String userName;

    @Column(length = 100, unique = true)
    private String email;

    @Column(length = 255)
    private String password;

    @Column(length = 20)
    private String phoneNumber;

    @Column(length = 255)
    private String address;

    @Column(length = 10)
    private String dob;

    @Column(length = 10)
    private String gender;

    @Column(length = 25)
    private String registeredDate;

    @Column(length = 255)
    private String education;

    @Column(length = 10000)
    private String experience;

    @Column(length = 10000)
    private String skills;
    @Column
    private Boolean isCvUploaded;
    @OneToMany(mappedBy = "jobSeeker", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore // Prevents serialization of the jobs list when JobProvider is serialized
    private List<Application> applications= new ArrayList<>();
    @Lob
    @Column(name = "cv_file")
    private byte[] cvFile;

    @Column(name = "cv_file_type")
    private String cvFileType;

    @Column(name = "cv_file_name")
    private String cvFileName;

    @Column
    private String securityQuestion;

    @Column
    private String securityAnswerHash;

}
