package com.dukejiang.action_send.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name="transmission")
public class Transmission extends BaseEntity{
    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "audience_email")
    private String audience_email;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "audience_id", referencedColumnName = "id", nullable = false)
    private Audience audience;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;
}
