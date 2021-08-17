package doancnpm.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import doancnpm.models.Candidate;

public interface CandidateRepository extends JpaRepository<Candidate, Long>{

	Candidate findOneByTutorIdAndPostId(Long tutorId, Long postId);
}
