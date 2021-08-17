package doancnpm.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import doancnpm.enums.PostStatus;
import doancnpm.models.Post;
import doancnpm.models.Subject;

public interface PostRepository extends JpaRepository<Post, Long> {

	List<Post> findByGrade_id(long grade_id);

	List<Post> findByAddress(String address);
	
	@Modifying
	@Query("SELECT p FROM Post p INNER JOIN p.subjects s WHERE s IN (:subjects)")
	List<Post> findBySubjects(@Param("subjects") List<Subject> subjects);
	
	List<Post> findBySubjectsInAndAddressIn(List<Subject> subjects, String address);
	
	List<Post> findBySubjectsInAndGrade_idIn(List<Subject> subjects, int grade_id);
	
	List<Post> findBySubjectsInAndGrade_idInAndAddressIn(List<Subject> subjects, int grade_id, String address);

	List<Post> findByGrade_idInAndAddressIn(int grade_id, String address);

	List<Post> findByStudent_id(long student_id);

	Optional<Post> findByStudentId(long Id);

	List<Post> findById(Long postId);
	
	List<Post> findByVerify(Boolean verify);

	List<Post> findByStatus(PostStatus choosing);
}
