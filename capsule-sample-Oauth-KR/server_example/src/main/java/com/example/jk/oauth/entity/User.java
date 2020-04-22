package com.example.jk.oauth.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Data
@Entity
@Table(name=User.TABLE_NAME)
public class User {
    protected static final String TABLE_NAME = "oauth_user";

    @Id
    @Column(length = 36)
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;

    private String loginId;
    private String password;

    private String accessToken;
}
