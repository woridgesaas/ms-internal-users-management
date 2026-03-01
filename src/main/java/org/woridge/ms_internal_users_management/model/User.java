package org.woridge.ms_internal_users_management.model;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.woridge.ms_internal_users_management.enumeration.Role;
import org.woridge.ms_internal_users_management.enumeration.Status;
import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name="users")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy=GenerationType.UUID)
    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phoneNumber;
    private String CIN;
    @Enumerated(EnumType.STRING)
    private Status status; //deleted or no
    private String address;
    private String picturePath;
    @Enumerated(EnumType.STRING)
    private Role role;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="department_id")
    private Department department;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;
    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY)
    private BankInformation bankInformation;
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private Set<Education> educations = new HashSet<>();
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private Set<ProfessionalExperience> professionalExperiences = new HashSet<>();
    private Instant createdAt;
    private Instant updatedAt;
}

