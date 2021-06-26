package engine.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Answer {

   // @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<Integer> answer;

    public Answer() {
    }


    public List<Integer> getAnswer() {
        return answer;
    }

    public void setAnswer(List<Integer> answers) {
        this.answer = answers;
    }
}
