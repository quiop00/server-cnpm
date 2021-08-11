package doancnpm.security.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import doancnpm.models.Invitation;
import doancnpm.models.Student;
import doancnpm.models.Tutor;
import doancnpm.models.User;
import doancnpm.payload.request.InvitationRequest;
import doancnpm.repository.InvitationRepository;
import doancnpm.repository.StudentRepository;
import doancnpm.repository.TutorRepository;
import doancnpm.repository.UserRepository;
import doancnpm.security.IInvitationService;

@Service
public class InvitationService implements IInvitationService {
	
	@Autowired
	InvitationRepository invitationRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	private TutorRepository tutorRepository;
	@Autowired
	private StudentRepository studentRepository;
	
	
	@Override
	public void save(String username, Long idTutor) {
	
		User user = userRepository.findOneByusername(username);
				
		Student student = studentRepository.findByuser_id(user.getId())
				.orElseThrow(() -> new UsernameNotFoundException("Student not found"));
		List<Invitation> invitations = invitationRepository.findAll();
			
		Tutor tutor = tutorRepository.findOne(idTutor);
		
		Invitation invitation = new Invitation();
		invitation.setStudent(student);
		invitation.setTutor(tutor);
		invitation.setStatus(0);
		
		invitation = invitationRepository.save(invitation);
	
		
	}


	@Override
	public void accept(String username, long idStudent) {
		User user = userRepository.findOneByusername(username);
		Tutor tutor = tutorRepository.findByuser_id(user.getId())
				.orElseThrow(() -> new UsernameNotFoundException("Tutor not found"));
		Student student = studentRepository.findOne(idStudent);
		
		Invitation oldInvitation = invitationRepository.findByTutor_idAndStudent_id(tutor.getId(),student.getId());
		oldInvitation.setStatus(1);
		Invitation invitation = oldInvitation;
		
		invitationRepository.save(invitation);
		
	}


	@Override
	public void reject(String username, long idStudent) {
		User user = userRepository.findOneByusername(username);
		Tutor tutor = tutorRepository.findByuser_id(user.getId())
				.orElseThrow(() -> new UsernameNotFoundException("Tutor not found"));
		Student student = studentRepository.findOne(idStudent);
		
		Invitation oldInvitation = invitationRepository.findByTutor_idAndStudent_id(tutor.getId(),student.getId());
		oldInvitation.setStatus(2);
		Invitation invitation = oldInvitation;
		
		invitationRepository.save(invitation);
		
	}


	@Override
	public List<Invitation> findByIdTutor(long idTutor) {
		return  invitationRepository.findBytutor_id(idTutor);
	}


	@Override
	public List<Invitation> findAll() {
		
		return invitationRepository.findAll();
	}


	
	
	
}
