package org.woridge.ms_internal_users_management.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "bank_informations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BankInformation {

    @Id
    @GeneratedValue(strategy=GenerationType.UUID)
    private UUID id;
    private String name;
    private String accountNumber;
    private String codeIban;
    private String agency;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;
}
