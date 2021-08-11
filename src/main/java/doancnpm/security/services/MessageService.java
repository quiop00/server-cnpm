//package doancnpm.security.services;
//
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import doancnpm.converter.MessageConverter;
//import doancnpm.models.Comment;
//import doancnpm.models.Tutor;
//import doancnpm.models.User;
//import doancnpm.payload.request.MessageRequest;
//import doancnpm.repository.MessageRepository;
//import doancnpm.repository.TutorRepository;
//import doancnpm.repository.UserRepository;
//import doancnpm.security.IMessageService;
//@Service
//public class MessageService implements IMessageService{
//
//	@Autowired
//	private MessageConverter messageConverter;
//	@Autowired
//	private UserRepository userRepository;
//	@Autowired
//	private TutorRepository tutorRepository;
//	@Autowired
//	private MessageRepository messageRepository;
//	
//	@Override
//	public void save(MessageRequest messageRequest) {
//		Comment message = new Comment();
//		message = messageConverter.toMessage(messageRequest);
//		
//		User user_Student = userRepository.findOneByusername(messageRequest.getStudentName());
//		
//		User user_Tutor = userRepository.findOneByusername(messageRequest.getTutorName());
//		
//		Tutor tutor = tutorRepository.findOne(user_Tutor.getId());
//		message.setUser(user_Student);
//		message.setTutor(tutor);
//		
//		message = messageRepository.save(message);
//	}
//
//	@Override
//	public List<Comment> all() {
//		List<Comment> messages = messageRepository.findAll();
//		return messages;
//	}
//
//	@Override
//	public Comment getMessageById(long id) {
//		Comment message = messageRepository.findOne(id);
//		return message;
//	}
//	
//}
