package doancnpm.security.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import doancnpm.converter.CommentConverter;
import doancnpm.models.Comment;
import doancnpm.models.Student;
import doancnpm.models.Tutor;
import doancnpm.models.User;
import doancnpm.payload.request.CommentRequest;
import doancnpm.repository.CommentRepository;
import doancnpm.repository.StudentRepository;
import doancnpm.repository.TutorRepository;
import doancnpm.repository.UserRepository;
import doancnpm.security.ICommentService;
@Service
public class CommentService implements ICommentService{

	@Autowired
	private CommentConverter commentConverter;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private TutorRepository tutorRepository;
	@Autowired
	private CommentRepository commentRepository;
	@Autowired
	private StudentRepository studentRepository;
	
	@Override
	public void save(String username, CommentRequest commentRequest) {
		User user = userRepository.findOneByusername(username);
		
		Student student = studentRepository.findByuser_id(user.getId())
				.orElseThrow(() -> new UsernameNotFoundException("Student not found"));
				
		Tutor tutor = tutorRepository.findOne(commentRequest.getIdTutor());
		Comment comment = new Comment();
		
		comment = commentConverter.toComment(commentRequest);
		
		comment.setStudent(student);
		comment.setTutor(tutor);
		
		commentRepository.save(comment);
	
	}

	@Override
	public List<Comment> findByIdTutor(long idTutor) {
		
		return  commentRepository.findBytutor_id(idTutor);
	}

	@Override
	public List<Comment> findAll() {
		
		return commentRepository.findAll();
	}

	
	
}
