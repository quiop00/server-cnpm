package doancnpm.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.google.api.Http;

import doancnpm.converter.PaymentConverter;
import doancnpm.enums.BillStatus;
import doancnpm.enums.NotifyType;
import doancnpm.models.Payment;
import doancnpm.models.Tutor;
import doancnpm.models.User;
import doancnpm.payload.request.PaymentRequest;
import doancnpm.payload.response.PaymentResponse;
import doancnpm.repository.PaymentRepository;
import doancnpm.repository.TutorRepository;
import doancnpm.repository.UserRepository;
import doancnpm.security.ICandidateService;
import doancnpm.security.INotificationService;
import doancnpm.security.IPaymentService;
import doancnpm.security.jwt.JwtUtils;
import doancnpm.security.services.CandidateService;
import doancnpm.security.services.NotificationService;
import doancnpm.security.services.PaymentService;

@RestController
@CrossOrigin
public class PaymentController {
	@Autowired
	PaymentRepository paymentRepository;
	
	@Autowired
	IPaymentService paymentService;
	
	@Autowired
	TutorRepository tutorRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	ICandidateService candidateService;
	
	@Autowired
	INotificationService notificationService;
	
	@Autowired
	JwtUtils jwtUtils;
	
	@PutMapping(name = "/api/pay")
	public Map<String,String> pay(HttpServletRequest request,@RequestBody PaymentRequest infoPayment){
		String jwt = parseJwt(request);
		String username = "";
		if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
			username = jwtUtils.getUserNameFromJwtToken(jwt);
		}
		User user = userRepository.findOneByusername(username);
		Tutor tutor = tutorRepository.findByUser_id(user.getId());
		
		Payment paymentInfo = paymentRepository.findOne(infoPayment.getIdBill());
		
		paymentInfo.setPayer(infoPayment.getPayer());
		paymentInfo.setIncoming(infoPayment.getIncoming());
		
		paymentInfo.setStatus(BillStatus.PAID);
		
		paymentRepository.save(paymentInfo);
		//----------------------Notify---------------------
		
		notificationService.pushNotification(null, tutor.getUser(), NotifyType.PAYMENT_SUCCESS, (long)-1);
		
		//-------------------------------------------------
		
		//OPEN CLASS AUTOMATIC
		candidateService.openClass(paymentInfo.getPostId(), true);
		
		Map<String,String> response =new HashMap<String, String>();
		response.put("message", "Thanh toán thành công");
		
		return response;
	}
	@GetMapping(name = "/api/bill")
	@PreAuthorize("hasRole('TUTOR')")
	public Map<String,List<PaymentResponse>> getBills(HttpServletRequest request){
		String jwt = parseJwt(request);
		String username = "";
		if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
			username = jwtUtils.getUserNameFromJwtToken(jwt);
		}
		User user = userRepository.findOneByusername(username);
		Tutor tutor = tutorRepository.findByUser_id(user.getId());
		
		List<Payment> payments= tutor.getPayments();
		List<PaymentResponse> bills = new ArrayList<PaymentResponse>();
		if(payments!=null) {
			for(Payment payment:payments) {
				PaymentResponse bill = PaymentConverter.modelToResponse(payment);
				bills.add(bill);
			}
		}
		Map<String,List<PaymentResponse>> response = new HashMap<String, List<PaymentResponse>>();
		response.put("bills", bills);
		return response;
	}
	private String parseJwt(HttpServletRequest request) {
		String headerAuth = request.getHeader("Authorization");
		if (StringUtils.hasLength(headerAuth) && headerAuth.startsWith("Bearer ")) {
			return headerAuth.replace("Bearer ", "");
		}
		return null;
	}
}
