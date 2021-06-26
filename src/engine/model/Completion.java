package engine.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.time.LocalDateTime;


@Entity
public class Completion {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    private Integer id;

    @ManyToOne
    //  @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;

   /* @JsonIgnore
    @ManyToOne
    // @JoinColumn(name = "quiz_id", nullable = false)
    // @JsonProperty("id")
    //  @JsonProperty(value = "id", access = JsonProperty.Access.READ_ONLY)
    private Quiz quiz;*/

    @Column(columnDefinition = "TIMESTAMP", nullable = false)
    private LocalDateTime completedAt = LocalDateTime.now();

    @JsonProperty(value = "id", access = JsonProperty.Access.READ_ONLY)
    Integer quizId;


    public Completion() {
    }

    public Completion(Integer id, User user, LocalDateTime completedAt) {
        this.quizId = id;
        this.user = user;
        this.completedAt = completedAt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    /*public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }*/

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(LocalDateTime completedAt) {
        this.completedAt = completedAt;
    }

    public Integer getQuizId() {
        return quizId;
    }

    public void setQuizId(Integer quizId) {
        this.quizId = quizId;
    }
}
