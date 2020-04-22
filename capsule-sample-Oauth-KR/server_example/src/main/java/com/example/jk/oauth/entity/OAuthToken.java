package com.example.jk.oauth.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = OAuthToken.TABLE_NAME)
public class OAuthToken {
    protected static final String TABLE_NAME = "oauth_token";

    @Id
    @Column(length = 36)
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;

    private String code;
    private String accessToken;
    private String refreshToken;

    private Date expire;
    private int expiresIn;

    private Date createdDate;
}
