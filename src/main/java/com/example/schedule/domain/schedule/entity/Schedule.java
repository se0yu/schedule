package com.example.schedule.domain.schedule.entity;

import com.example.schedule.common.entity.BaseEntity;
import com.example.schedule.domain.user.entity.User;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "schedule")
@AllArgsConstructor
@NoArgsConstructor
public class Schedule extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "text")
    private String contents;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


    public Schedule(String title, String contents, User user) {
        this.title = title;
        this.contents = contents;
        this.user = user;
    }

    public void updateSchedule(String title, String contents){
        this.title = title;
        this.contents = contents;
    }

}
