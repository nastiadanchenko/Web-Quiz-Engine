package engine.controller;

import engine.model.*;
import engine.repositiry.QuizRepository;
import engine.serviece.QuizAuthenticationService;
import engine.serviece.QuizService;
import engine.serviece.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.security.Principal;
import java.util.*;


@RestController
@Validated
@RequestMapping("/api/quizzes")
public class QuizController {

    private final QuizService quizService;
    private final UserService userService;

    private final QuizAuthenticationService quizAuthenticationService;

    @Autowired
    public QuizController(QuizService quizService, UserService userService,
                          QuizAuthenticationService quizAuthenticationService) {
        this.quizService = quizService;
        this.userService = userService;
        this.quizAuthenticationService = quizAuthenticationService;

    }


    @GetMapping(path = "/{id}")
    @NotNull
    public Quiz getQuizById(@PathVariable @Min(0) Integer id) {
        Optional<Quiz> quiz = quizService.findQuiz(id);
        if (quiz.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No such quiz");
        return quiz.get();
    }


    @GetMapping("")
    @ResponseBody
    public ResponseEntity<Page<Quiz>> getQuizzes(@RequestParam(value = "page", defaultValue = "0") @Min(0) int page) {
        return new ResponseEntity<>(quizService.getAllQuizzes(page), HttpStatus.OK);
    }

    @GetMapping("/completed")
    public ResponseEntity<Page<Completion>> getCompleted(
            @RequestParam(value = "page", defaultValue = "0") @Min(0) int page,
            Principal principal) {
        return new ResponseEntity<>(quizService.getCompletionsByUser(page, principal), HttpStatus.OK);
    }

  /*  @GetMapping(path = "")
    public Iterable<Quiz> getAllQuizzes() {
        return quizService.getAll();
    }
*/

    @PostMapping(path = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    Quiz createQuiz(@Valid @RequestBody @NotNull Quiz q,  /*@AuthenticationPrincipal*/ Authentication authentication) {
        /*Optional<User> optionalUser = userService.findUser(user.getEmail());
        if (optionalUser.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "user not found");
        }

        q.setUser(optionalUser.get());*/
        return quizService.save(q,authentication);
    }

    @PostMapping(path = "/{id}/solve", consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    QuizResult getSolveQuiz(@Valid @RequestBody Answer answer,
                            @PathVariable Integer id,   Authentication authentication) {
        return quizService.evaluateQuiz(id, answer, authentication);
//        Optional<Quiz> quiz = quizService.findQuiz(id);
//
//        if (quiz.isEmpty()) {
//            throw new ResponseStatusException(
//                    HttpStatus.NOT_FOUND, "page not found");
//        }
//
//        if (answer.getAnswer() == null & quiz.get().getAnswer() == null)
//            return new QuizResult(true, "Congratulations, you're right!");
//
//        else if (answer.getAnswer() != null && answer.getAnswer().isEmpty() & quiz.get().getAnswer() == null)
//            return new QuizResult(true, "Congratulations, you're right!");
//
//        else if (answer.getAnswer() != null & quiz.get().getAnswer() != null && answer.getAnswer().isEmpty() & quiz.get().getAnswer().isEmpty())
//            return new QuizResult(true, "Congratulations, you're right!");
//
//        else if (quiz.get().getAnswer() != null && answer.getAnswer() == null & quiz.get().getAnswer().isEmpty())
//            return new QuizResult(true, "Congratulations, you're right!");
//
//        else if (answer.getAnswer() != null && answer.getAnswer().equals(quiz.get().getAnswer()))
//            return new QuizResult(true, "Congratulations, you're right!");
//
//        return new QuizResult(false, "Wrong answer! Please, try again.");
    }

    @DeleteMapping(value = "/{id}")
    public void deleteQuiz(@PathVariable Integer id, @AuthenticationPrincipal User user) {
        Optional<Quiz> quiz = quizService.findQuiz(id);

        Optional<User> optionalUser = userService.findUser(user.getEmail());
        if (optionalUser.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "user not found");
        }

        if (quiz.isEmpty())
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "page not found");

        if (!quizService.userOwnsQuiz(optionalUser.get(), quiz.get())) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN, "you are not allowed to delete this quiz"
            );
        }

        quizService.delete(quiz.get());
        throw new ResponseStatusException(
                HttpStatus.NO_CONTENT);
    }
}
