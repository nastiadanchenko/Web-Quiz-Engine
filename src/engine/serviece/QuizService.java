package engine.serviece;

import engine.model.*;
import engine.repositiry.QuizAuthenticationRepository;
import engine.repositiry.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class QuizService {

    private final QuizRepository quizRepository;

    private final QuizAuthenticationRepository quizAuthenticationRepository;


    @Autowired
    public QuizService(QuizRepository quizRepository, QuizAuthenticationRepository quizAuthenticationRepository) {
        this.quizRepository = quizRepository;
        this.quizAuthenticationRepository = quizAuthenticationRepository;
    }

    public Page<Quiz> getAllQuizzes(int page) {
        return quizRepository.findAll(PageRequest.of(page, 10, Sort.by("id")));
    }

    public Optional<Quiz> findQuiz(Integer id) {
        return quizRepository.findById(id);
    }

    public Quiz save(Quiz quiz, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        quiz.setUser(user);
        return quizRepository.save(quiz);
    }

    public QuizResult evaluateQuiz(int id, Answer answer, Authentication authentication) {
        List<Integer> originalAnswer = findQuiz(id).get().getAnswer();
        List<Integer> providedAnswer= answer.getAnswer();

        if (originalAnswer.size() != providedAnswer.size())  return new QuizResult(false, "Wrong answer! Please, try again.");
        for (int i = 0; i < originalAnswer.size(); i++) {
            if (originalAnswer.get(i) != providedAnswer.get(i)) return new QuizResult(false, "Wrong answer! Please, try again.");
        }

        Completion completion= new Completion(id,(User) authentication.getPrincipal(), LocalDateTime.now());
        quizAuthenticationRepository.save(completion);
        return new QuizResult(true, "Congratulations, you're right!");
    }

    public void delete(Quiz quiz) {
        quizRepository.delete(quiz);
    }

    public boolean userOwnsQuiz(User user, Quiz quiz) {
        return user.equals(quiz.getUser());
    }

    public Page<Completion> getCompletionsByUser(int page, Principal principal) {
        return quizAuthenticationRepository.findAllByUserOrderByCompletedAtDesc(
                principal.getName(), PageRequest.of(page, 10));
    }

 /*   public Optional<Result> getResult(Long id, Answer answer) {
        return findById(id).map(quiz -> {
            var answers = Objects.requireNonNullElse(quiz.getAnswer(), new Integer[]{});
            if (Set.of(answers).equals(answer.getOptions())) {
                return Result.SUCCESS;
            }

            return Result.FAIL;
        });
    }*/

}
