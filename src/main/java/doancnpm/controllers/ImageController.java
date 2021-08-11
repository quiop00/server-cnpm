package doancnpm.controllers;

import javax.servlet.http.HttpServletRequest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import doancnpm.models.Tutor;
import doancnpm.models.User;
import doancnpm.payload.response.UploadFileResponse;
import doancnpm.payload.response.UploadImageResponse;
import doancnpm.repository.TutorRepository;
import doancnpm.repository.UserRepository;
import doancnpm.security.IImageService;
import doancnpm.security.jwt.JwtUtils;
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class ImageController {
	@Autowired
	private JwtUtils jwtUtils;
	@Autowired
    IImageService imageService;
	@Autowired
    private TutorRepository tutorRepository;
	@Autowired
	private UserRepository userRepository;
	
	@PostMapping("/uploadImage")
	@PreAuthorize("hasRole('ADMIN') or hasRole('TUTOR')")
    public UploadImageResponse saveAvatar(HttpServletRequest request, @RequestParam("file") MultipartFile file) {
		String jwt = parseJwt(request);
    	String username ="";
		if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
			username = jwtUtils.getUserNameFromJwtToken(jwt);
		}
		User user = userRepository.findByUsername(username)
					.orElseThrow(() -> new UsernameNotFoundException("User Not Found with username"));
	    Tutor tutor = tutorRepository.findByuser_id(user.getId())
					.orElseThrow(() -> new UsernameNotFoundException("Tutor Not Found"));
	    UploadImageResponse imageUpload = new UploadImageResponse();
        try {

            String fileName = imageService.save(file);
            String imageUrl = imageService.getImageUrl(fileName);
            System.out.println(imageUrl);
            // do whatever you want with that
            tutor.setAvatar(imageUrl);
            tutorRepository.save(tutor);
            imageUpload.setPath(imageUrl);
        } catch (Exception e) {
        //  throw internal error;
        }
      
        
        
        return imageUpload;
    }
	  private String parseJwt(HttpServletRequest request) {
			String headerAuth = request.getHeader("Authorization");

//			if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
//				return headerAuth.substring(7, headerAuth.length());
//			}
			if (StringUtils.hasLength(headerAuth) && headerAuth.startsWith("Bearer ")) {
	        	return headerAuth.replace("Bearer ","");
	        }
			return null;
		}
	
}
