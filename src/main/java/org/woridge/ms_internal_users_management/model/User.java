package org.woridge.ms_internal_users_management.model;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.woridge.ms_internal_users_management.enumeration.Role;
import org.woridge.ms_internal_users_management.enumeration.Status;
import java.time.Instant;
import java.time.LocalDate;
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
    @Column(name = "birthdate")
    private LocalDate birthDate;
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
    @OneToOne(mappedBy = "user" ,cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private SocialInfo socialInfo;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", CIN='" + CIN + '\'' +
                ", status=" + status +
                ", address='" + address + '\'' +
                ", picturePath='" + picturePath + '\'' +
                ", role=" + role +
                ", birthDate=" + birthDate +
                ", departmentId=" + (department != null ? department.getId() : null) + 
                ", postId=" + (post != null ? post.getId() : null) +
                ", hasBankInformation=" + (bankInformation != null) +
                ", educationsCount=" + (educations != null ? educations.size() : 0) +
                ", professionalExperiencesCount=" + (professionalExperiences != null ? professionalExperiences.size() : 0) +
                ", socialInfoId=" + (socialInfo != null ? socialInfo.getId() : null) +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}

