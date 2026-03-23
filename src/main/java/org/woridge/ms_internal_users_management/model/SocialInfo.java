package org.woridge.ms_internal_users_management.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "social_info")
public class SocialInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String cnssNumber;
    private Integer familyDeductions = 0;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}
