package org.woridge.ms_internal_users_management.model;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.woridge.ms_internal_users_management.enumeration.Role;
import org.woridge.ms_internal_users_management.enumeration.Status;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name="users")
@Getter
@Setter
@Builder
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
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="department_id")
    private Department department;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;
    private Instant createdAt;
    private Instant updatedAt;
}

