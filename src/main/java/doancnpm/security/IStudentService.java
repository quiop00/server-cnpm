package doancnpm.security;

import java.util.List;
import doancnpm.models.Student;
import doancnpm.payload.request.StudentRequest;

public interface IStudentService {
	 List<Student> all();
	 Student getStudentById(long id);
	 void save(String username, StudentRequest studentRequest);
	 void delete(long[] ids);
	 Student findStudent(String username);
}
