package doancnpm.security.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import doancnpm.models.Student;
import doancnpm.models.User;
import doancnpm.payload.request.StudentRequest;
import doancnpm.repository.StudentRepository;
import doancnpm.repository.UserRepository;
import doancnpm.security.IStudentService;

@Service
public class StudentService implements IStudentService{
	
	@Autowired
	private StudentRepository studentRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	PasswordEncoder encoder;
	
	@Override
	public List<Student> all() {
		List<Student> students = studentRepository.findAll();
		return students;
	}

	@Override
	public Student getStudentById(long id) {
		Student student = studentRepository.findOne(id);
		return student;
	}

	@Override
	public void save(String username, StudentRequest studentRequest) {
		
		User user = userRepository.findOneByusername(username);
		
		user.setEmail(studentRequest.getEmail());
		user.setPhonenumber(studentRequest.getPhonenumber());
		user.setName(studentRequest.getName());
		user.setAge(studentRequest.getAge());
		user.setGender(studentRequest.getGender());
		userRepository.save(user);
		
//		Student student = new Student();
//		
//		Student oldStudent = studentRepository.findByuser_id(user.getId())
//			.orElseThrow(() -> new UsernameNotFoundException("Student Not Found"));
//		
//		student.setUser(oldStudent.getUser());
//		if(addUser.getId() != null) {
//			Student oldStudent = studentRepository.findOne(addUser.getId());
//			user = userConverter.toUser(addUser, oldUser);
//			student =
//		}else
//		{
//			user = userConverter.toUser(addUser);
//		}
//		
//		user = userRepository.save(user);
//		student = studentRepository.save(student);
		
	}

	
	@Override
	public void delete(long[] ids) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Student findStudent(String username) {
		User user = userRepository.findOneByusername(username);
		Student student = studentRepository.findByuser_id(user.getId())
				.orElseThrow(() -> new UsernameNotFoundException("Student Not Found"));
		return student;
	}
	
}
