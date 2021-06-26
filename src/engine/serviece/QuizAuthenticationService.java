package engine.serviece;

import engine.model.Completion;
import engine.model.Quiz;
import engine.repositiry.QuizAuthenticationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class QuizAuthenticationService {
    private final QuizAuthenticationRepository quizAuthenticationRepository;

    @Autowired
    public QuizAuthenticationService(QuizAuthenticationRepository quizAuthenticationRepository) {
        this.quizAuthenticationRepository = quizAuthenticationRepository;
    }


    public Page<Completion> findAll(Pageable paging) {
        return quizAuthenticationRepository.findAll(paging);
    }

}
