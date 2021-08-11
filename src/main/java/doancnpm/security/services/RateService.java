package doancnpm.security.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import doancnpm.models.Rate;
import doancnpm.models.Student;
import doancnpm.models.Tutor;
import doancnpm.models.User;
import doancnpm.repository.RateRepository;
import doancnpm.repository.StudentRepository;
import doancnpm.repository.TutorRepository;
import doancnpm.repository.UserRepository;
import doancnpm.security.IRateService;

@Service
public class RateService implements IRateService{
	@Autowired
	UserRepository userRepository;
	@Autowired
	private TutorRepository tutorRepository;
	@Autowired
	private StudentRepository studentRepository;
	@Autowired
	private RateRepository rateRepository;
	
	@Override
	public void save(String username, Long idTutor, int star) {
		User user = userRepository.findOneByusername(username);
		
		Student student = studentRepository.findByuser_id(user.getId())
				.orElseThrow(() -> new UsernameNotFoundException("Student not found"));
			
		Tutor tutor = tutorRepository.findOne(idTutor);
		
		Rate rate = new Rate();
		rate.setStudent(student);
		rate.setTutor(tutor);
		rate.setStar(star);
		rate = rateRepository.save(rate);
		
	}

	@Override
	public float getStar(Long idTutor) {
		List<Rate> rates = rateRepository.findAll();
		float star;
		long sum=0;
		int k=0;
		for(int i=0;i<rates.size();i++)
			if(rates.get(i).getTutor().getId() == idTutor) {
				sum=sum+rates.get(i).getStar();
				k=k+1;
			}
		if(k!=0)
			star = (float) (sum*1.0/k);
		else 
			star = 0; 
		return star;
		
	}
	
}
