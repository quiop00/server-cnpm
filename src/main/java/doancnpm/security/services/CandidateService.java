package doancnpm.security.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import doancnpm.enums.CandidateStatus;
import doancnpm.enums.ClassStatus;
import doancnpm.enums.NotifyType;
import doancnpm.enums.PostStatus;
import doancnpm.models.Candidate;
import doancnpm.models.Post;
import doancnpm.models.TakenClass;
import doancnpm.models.Tutor;
import doancnpm.repository.PostRepository;
import doancnpm.repository.TutorRepository;
import doancnpm.security.ICandidateService;

@Service
public class CandidateService implements ICandidateService{
	@Autowired
	PostRepository postRepository;
	@Autowired
	TutorRepository tutorRepository;
	@Autowired
	NotificationService notificationService;
	
	@Override
	public Candidate findByIdTutor(Set<Candidate> candidates, Long idTutor) {
		for(Candidate candidate: candidates) {
			if(candidate.getTutor().getId()==idTutor)
				return candidate;
		}
		return null;
	}

	@Override
	public Candidate findByStatus(Set<Candidate> candidates, String status) {
		for(Candidate candidate: candidates) {
			if(candidate.getStatus().name().equals(status))
				return candidate;
		}
		return null;
	}

	@Override
	public void openClass(Long idPost, Boolean approval) {
		Post post = postRepository.findOne(idPost);
		Set<Candidate> candidates= post.getCandidates();
		Set<Candidate> newCandidates = new HashSet<Candidate>();
		for(Candidate candidate: candidates) {
			if(candidate.getStatus()==CandidateStatus.WAITING)
				{
					if(approval) {
						Tutor tutor = candidate.getTutor();
						List<TakenClass> takenClasses = tutor.getClasses();
						if(takenClasses == null)
							takenClasses = new ArrayList<TakenClass>();
						candidate.setStatus(CandidateStatus.ACCEPT);
						TakenClass takenClass = new TakenClass();
						takenClass.setSchedule(post.getSchedule());
						takenClass.setStatus(ClassStatus.TEACHING);
						takenClass.setStudent(post.getStudent());
						takenClass.setTutor(candidate.getTutor());
						takenClass.setGrade(post.getGrade());
						takenClass.setAddress(post.getAddress());
						takenClass.setSubjects(post.getSubjects());
						System.out.println("kkkkkkkkkkkkkkkkkk" + post.getSubjects());
						takenClasses.add(takenClass);
						tutor.setClasses(takenClasses);
						tutorRepository.save(tutor);
						
						//notify to student & tutor
						notificationService.pushNotification(post, tutor.getUser(),NotifyType.ACCEPT_OPEN_CLASS, (long) -1);
						notificationService.pushNotification(post, post.getStudent().getUser(),NotifyType.ACCEPT_OPEN_CLASS,(long) -1);
						
					}
					else {
							Tutor tutor = candidate.getTutor();
							candidate.setStatus(CandidateStatus.DENY);
							
							//notify to student & tutor
							notificationService.pushNotification(post, tutor.getUser(),NotifyType.REJECT_OPEN_CLASS, (long) -1);
							notificationService.pushNotification(post, post.getStudent().getUser(),NotifyType.REJECT_OPEN_CLASS,(long) -1);
						}
				}else {
					if(approval)
						candidate.setStatus(CandidateStatus.DENY);
				}
			newCandidates.add(candidate);

		}
		post.setCandidates(newCandidates);
		if(approval||post.getIsExpire())
			post.setStatus(PostStatus.CLOSE);
		else
			post.setStatus(PostStatus.OPEN);
		
		postRepository.save(post);
		
	}

}
