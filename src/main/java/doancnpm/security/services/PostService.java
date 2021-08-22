package doancnpm.security.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import doancnpm.converter.PostConverter;
import doancnpm.enums.CandidateStatus;
import doancnpm.enums.PostStatus;
import doancnpm.models.Admin;
import doancnpm.models.Candidate;
import doancnpm.models.Grade;
import doancnpm.models.Post;
import doancnpm.models.Student;
import doancnpm.models.Subject;
import doancnpm.models.Tutor;
import doancnpm.models.User;
import doancnpm.payload.request.PostRequest;
import doancnpm.repository.GradeRepository;
import doancnpm.repository.PostRepository;
import doancnpm.repository.StudentRepository;
import doancnpm.repository.SubjectRepository;
import doancnpm.repository.UserRepository;
import doancnpm.repository.AdminRepository;
import doancnpm.security.iPostService;

@Service
public class PostService implements iPostService {
	

	@Autowired
	private PostRepository postRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private StudentRepository studentRepository;

	@Autowired
	private PostConverter postConverter;
	
	@Autowired
	private GradeRepository gradeRepository;

	@Autowired
	private SubjectRepository subjectRepository;
	
	@Autowired
	private AdminRepository adminRepository;
	
	@Override
	public void saveCreate(String username, PostRequest postDTO) {
		Set<String> strSubject = postDTO.getSubject();
		Set<Subject> subjects = new HashSet<>();
		strSubject.forEach(subject -> {
				Subject sbj = subjectRepository.findBysubjectname(subject);	
				subjects.add(sbj);
		});
//		Set<Subject> subjects = new HashSet<>();
//		strSubject.forEach(subject -> {
//			Subject sbj = subjectRepository.findBysubjectname(subject);
//			if(sbj == null) {
//				sbj = new Subject();
//				sbj.setSubjectname(subject);
//				sbj = subjectRepository.save(sbj);
//			}
//			subjects.add(sbj);
//		});
		User user = userRepository.findOneByusername(username);

		Student oldStudent = studentRepository.findByuser_id(user.getId())
				.orElseThrow(() -> new UsernameNotFoundException("Student Not Found"));

		Map<String, Boolean> schedule = postDTO.getSchedules();
		Post postEntity = new Post();
		postEntity = postConverter.toEntity(postDTO);
		postEntity.setStudent(oldStudent);
		String jsonResp = "";
		ObjectMapper mapperObj = new ObjectMapper();
		try {
			jsonResp = mapperObj.writeValueAsString(schedule);
			System.out.println(jsonResp);
		} catch (IOException e) {
			e.printStackTrace();

		}
		
		Grade grade = gradeRepository.findBygradename(postDTO.getGrade());
//				.orElseThrow(() -> new RuntimeException("Error: Grade is not found."));	
		postEntity.setSchedule(jsonResp);
		postEntity.setGrade(grade);
		postEntity.setSubjects(subjects);
		postEntity.setStatus(postEntity.checkStatus());
		postEntity = postRepository.save(postEntity);
		postDTO = postConverter.toDTO(postEntity);
	}

	@Override
	public void saveUpdate(String username, PostRequest postDTO, long id) {
		Set<String> strSubject = postDTO.getSubject();
		Set<Subject> subjects = new HashSet<>();
		strSubject.forEach(subject -> {
			Subject sbj = subjectRepository.findBysubjectname(subject);
			if(sbj == null) {
				sbj = new Subject();
				sbj.setSubjectname(subject);
				sbj = subjectRepository.save(sbj);
			}
			subjects.add(sbj);
		});
		
		User user = userRepository.findOneByusername(username);
		Student student = studentRepository.findByuser_id(user.getId())
				.orElseThrow(() -> new UsernameNotFoundException("Student Not Found"));

		Map<String, Boolean> schedule = postDTO.getSchedules();

		Post postEntity = new Post();
		
		Post post = postRepository.findOne(id);

		postEntity = postConverter.toEntity(postDTO, post);
		
		Grade grade = gradeRepository.findBygradename(postDTO.getGrade());
//				.orElseThrow(() -> new RuntimeException("Error: Grade is not found."));
		
		postEntity.setGrade(grade);
		postEntity.setSubjects(subjects);
		postEntity.setStudent(student);
		String jsonResp = "";
		ObjectMapper mapperObj = new ObjectMapper();
		try {
			jsonResp = mapperObj.writeValueAsString(schedule);
			System.out.println(jsonResp);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		postEntity.setSchedule(jsonResp);
		postEntity = postRepository.save(postEntity);
		postDTO = postConverter.toDTO(postEntity);
	}

	@Override
	public void delete(String username, long id) {
		User user = userRepository.findOneByusername(username);
		Student student = studentRepository.findByuser_id(user.getId())
				.orElseThrow(() -> new UsernameNotFoundException("Student Not Found"));
		Post post = postRepository.findOne(id);
		if(post.getStatus()==PostStatus.CHOOSING)
			return;
		postRepository.delete(id);
	}

	@Override
	public List<PostRequest> findAll(Pageable pageable) {
		List<PostRequest> results = new ArrayList<>();
		List<Post> entities = postRepository.findAll(pageable).getContent();
		for (Post item : entities) {
			PostRequest postDTO = postConverter.toDTO(item);
			results.add(postDTO);
		}
		return results;
	}
	@Override
	public Boolean approvalPost(Boolean approval,Long id) {
		Post post = postRepository.findOne(id);
		if(post == null)
			return false;
		post.setVerify(approval);
		postRepository.save(post);
		return true;
	}

	@Override
	public int totalItem() {

		return (int) postRepository.count();
	}

	@Override
	public Post findPostById(Long id) {

		return postRepository.findOne(id);
	}

	@Override
	public List<Post> findAll() {
		// TODO Auto-generated method stub
		return postRepository.findAll();
	}

	@Override
	public List<Post> findByIdStudent(long idStudent) {
		return  postRepository.findByStudent_id(idStudent);
	}

	@Override
	public void admin_delete(long id) {
		postRepository.delete(id);
		
	}
	public static Tutor getAcceptTutor(Post post) {
		if(post.getCandidates()!=null)
			for(Candidate candidate:post.getCandidates()) {
				if(candidate.getStatus()==CandidateStatus.WAITING) {
					return candidate.getTutor();
				}
			}
		return null;
	}

}