package doancnpm.security;

import java.util.List;

import org.springframework.data.domain.Pageable;

import doancnpm.models.Tutor;
import doancnpm.payload.request.AddTutorRequest;

public interface ITutorService {
	List<Tutor> findAll();
	List<Tutor> getTutorsByVerify(Boolean verify);
	List<Tutor> findAllPage(Pageable pageable);
	Boolean approvalTutor(Boolean approval,Long id);
	int totalItem();
	void save(String username, AddTutorRequest addTutorRequest);
	Tutor findTutorById(Long id);
	Tutor findTutor(String username);
	void delete(long[] ids);
}
