package engine.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
public class Quiz {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)
    @NotNull (message = "The setting 'id' must not be empty")
    private int id;

    @Column
    @NotBlank(message = "The setting 'title' must not be blank")
    private String title;

    @Column
    @NotBlank(message = "The setting 'text' must not be blank")
    private String text;

    @Column
    @NotNull(message = "The setting 'options' must not be empty ")
    @Size(min = 2, message = "Must be min 2 answers")
    @ElementCollection
    private List<String> options;

    @Column
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    //@NotBlank(message = "Answer field is not be null")
    @ElementCollection
    private List<Integer> answer;

    @ManyToOne
 //   @JoinColumn(name = "email")
    @JsonIgnore
    private User user;


    protected Quiz() {

    }

    public Quiz(int id, String title, String text, List<String> options, List<Integer> answer) {
        this.id = id;
        this.title = title;
        this.text = text;
        this.options = options;
        this.answer = answer;
    }

    public Quiz(int id, @NotEmpty String title, @NotEmpty String text, @Size(min = 2) List<String> options) {
        this.id = id;
        this.title = title;
        this.text = text;
        this.options = options;
        this.answer = null;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    public List<Integer> getAnswer() {
        return answer;
    }

    public void setAnswer(List<Integer> answer) {
        this.answer = answer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean checkAnswers(List<Integer> answers) {
        if (answer == null) {
            return answers == null || answers.isEmpty();
        }

        return answer.size() == answers.size() && answers.containsAll(answer);
    }
}
