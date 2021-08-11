package doancnpm.security.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import doancnpm.models.Post;
import doancnpm.models.Student;
import doancnpm.models.Suggestion;
import doancnpm.models.Tutor;
import doancnpm.models.User;
import doancnpm.repository.PostRepository;
import doancnpm.repository.StudentRepository;
import doancnpm.repository.SuggestionRepository;
import doancnpm.repository.TutorRepository;
import doancnpm.repository.UserRepository;
import doancnpm.security.iSuggestionService;

@Service
public class SuggestionService implements iSuggestionService {

	@Autowired
	private SuggestionRepository suggestionRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private StudentRepository studentRepository;

	@Autowired
	private TutorRepository tutorRepository;

	@Autowired
	private PostRepository postRepository;

	@Override
	public void save(String username, Long idPost, Long idStudent) {
		User user = userRepository.findOneByusername(username);

		Tutor tutor = tutorRepository.findByuser_id(user.getId())
				.orElseThrow(() -> new UsernameNotFoundException("Tutor not found"));

		Post post = postRepository.findOne(idPost);
		Student student=studentRepository.findOne(idStudent);
		Suggestion suggestion = new Suggestion();
		suggestion.setPost(post);
		suggestion.setTutor(tutor);
		suggestion.setStudent(student);
		suggestion.setStatus(0);
		suggestion = suggestionRepository.save(suggestion);

	}

	@Override
	public void accept(String username, long idPost, long idTutor) {
		User user = userRepository.findOneByusername(username);

		Student student = studentRepository.findByuser_id(user.getId())
				.orElseThrow(() -> new UsernameNotFoundException("Student not found"));
		Tutor tutor = tutorRepository.findOneById(idTutor);
		Post post = postRepository.findOne(idPost);
		Suggestion oldSuggestion = suggestionRepository.findByTutor_idAndPost_idAndStudent_id(tutor.getId(), post.getId(), student.getId());
		oldSuggestion.setStatus(1);
		Suggestion suggestion = oldSuggestion;
		suggestionRepository.save(suggestion);
	}

	@Override
	public void reject(String username, long idPost, long idTutor) {
		User user = userRepository.findOneByusername(username);

		Student student = studentRepository.findByuser_id(user.getId())
				.orElseThrow(() -> new UsernameNotFoundException("Student not found"));
		Tutor tutor = tutorRepository.findOne(idTutor);
		Post post = postRepository.findOne(idPost);
		Suggestion oldSuggestion = suggestionRepository.findByTutor_idAndPost_idAndStudent_id(tutor.getId(), post.getId(), student.getId());
		oldSuggestion.setStatus(2);
		Suggestion suggestion = oldSuggestion;
		suggestionRepository.save(suggestion);

	}

	@Override
	public List<Suggestion> findByIdStudent(long idStudent) {
		return  suggestionRepository.findBystudent_id(idStudent);
	}

	@Override
	public void delete(String username, long id) {
		User user = userRepository.findOneByusername(username);
		Student student = studentRepository.findByuser_id(user.getId())
				.orElseThrow(() -> new UsernameNotFoundException("Student Not Found"));
		suggestionRepository.delete(id);
	}

	@Override
	public List<Suggestion> findAll() {
		// TODO Auto-generated method stub
		return suggestionRepository.findAll();
	}

}
