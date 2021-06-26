package engine.repositiry;

import engine.model.Completion;
import engine.model.Quiz;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizAuthenticationRepository extends PagingAndSortingRepository<Completion,Integer> {

    @Query("SELECT c FROM Completion c where c.user.email = :email order by c.completedAt desc")
    Page<Completion> findAllByUserOrderByCompletedAtDesc(@Param("email") String email, Pageable pageable);
}
