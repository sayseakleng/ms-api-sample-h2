package kh.com.foss.sample.entity;

import jakarta.persistence.*;
import kh.com.foss.sample.constant.GenderType;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_seq")
    @SequenceGenerator(name = "users_seq", sequenceName = "users_seq")
    private Long userId;

    @Column(length = 20, nullable = false, unique = true)
    private String phone;

    @Column(length = 50, nullable = false)
    private String firstName;

    @Column(length = 50, nullable = false)
    private String lastName;

    @Enumerated(EnumType.STRING)
    @Column(length = 1, nullable = false)
    private GenderType gender;
}
