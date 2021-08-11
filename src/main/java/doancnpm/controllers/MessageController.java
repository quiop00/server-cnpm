//package doancnpm.controllers;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import doancnpm.models.Comment;
//import doancnpm.models.User;
//import doancnpm.payload.request.MessageRequest;
//import doancnpm.security.IMessageService;
//
//
//@CrossOrigin(origins = "*", maxAge = 3600)
//@RestController
//@RequestMapping("/api")
//public class MessageController {
//	@Autowired
//	IMessageService messageService;
//	
//	
//	@GetMapping(value = "/message")
//	public Map<String, List<Comment>> all(){
//		List<Comment> messages = messageService.all();
//		Map<String,List<Comment>> response = new HashMap<String, List<Comment>>();
//		response.put("messages", messages);
//		return response;
//	}
//	
//	@GetMapping("/message/{id}")
//	 public ResponseEntity<Comment> getMessageById(@PathVariable("id") long id) {
//	    //User userData = userService.getUserById(id);
//	    Comment message = messageService.getMessageById(id);
//
//	    if (message != null) {
//	      return new ResponseEntity<>(message, HttpStatus.OK);
//	    } else {
//	      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//	    }
//	  }
//	
//	@PostMapping(value = "/message")
//	public void createNew(@RequestBody MessageRequest model) {
//		messageService.save(model);
//	}
//}
