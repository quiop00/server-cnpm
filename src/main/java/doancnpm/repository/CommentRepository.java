package doancnpm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import doancnpm.models.Comment;


public interface CommentRepository extends JpaRepository<Comment, Long>{
	List<Comment> findBytutor_id(long idTutor);
}
