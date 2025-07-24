package com.example.schedule.domain.comment.entity;

import com.example.schedule.common.entity.BaseEntity;
import com.example.schedule.domain.schedule.entity.Schedule;
import com.example.schedule.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "comments")
@AllArgsConstructor
@NoArgsConstructor
public class Comment extends BaseEntity {

    //댓글 내용, 유저 고유 식별자, 일정 고유 식별자

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "text")
    private String content;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;

    public Comment (String content, User user, Schedule schedule){
        this.content = content;
        this.user = user;
        this.schedule = schedule;
    }

    public void updateComment(String content){
        this.content = content;
    }

}
