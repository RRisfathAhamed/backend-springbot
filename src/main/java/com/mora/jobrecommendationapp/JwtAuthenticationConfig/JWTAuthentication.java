package com.mora.jobrecommendationapp.JwtAuthenticationConfig;

import com.mora.jobrecommendationapp.entities.JobProvider;
import com.mora.jobrecommendationapp.entities.JobSeeker;
import com.mora.jobrecommendationapp.repositories.JobProviderRepository;
import com.mora.jobrecommendationapp.repositories.JobSeekerRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.Date;

@Service
public class JWTAuthentication {
    @Autowired
    private JobSeekerRepository jobSeekerRepository;
    @Autowired
    private JobProviderRepository jobProviderRepository;

    public String generateToken(String userName) {
        //JobSeeker jobSeeker = jobSeekerRepository.findById(Long.valueOf(userName)).orElse(null);
        JobSeeker jobSeeker = jobSeekerRepository.findByUserName(userName);

        byte[] secretKeyBytes= Keys.secretKeyFor(SignatureAlgorithm.HS512).getEncoded();
        String secretKey= Base64.getEncoder().encodeToString(secretKeyBytes);
        assert jobSeeker != null;
        String token= Jwts.builder()
                .claim("userName",jobSeeker.getUserName())
                .claim("jobSeekerId",jobSeeker.getJobSeekerId())
                .claim("role","JobSeeker")
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+1000*60*60*10))
                .signWith(SignatureAlgorithm.HS512,secretKey)
                .compact();
        return token;

    }

    public String generateTokenForJobProvider(String userName) {
        JobProvider jobProvider = jobProviderRepository.findByUserName(userName);

        byte[] secretKeyBytes= Keys.secretKeyFor(SignatureAlgorithm.HS512).getEncoded();
        String secretKey= Base64.getEncoder().encodeToString(secretKeyBytes);
        assert jobProvider != null;
        String token= Jwts.builder()
                .claim("userName",jobProvider.getUserName())
                .claim("jobProviderId",jobProvider.getJobProviderId())
                .claim("role","JobProvider")
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+1000*60*60*10))
                .signWith(SignatureAlgorithm.HS512,secretKey)
                .compact();
        return token;

    }
}





