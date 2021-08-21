package doancnpm.security.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import doancnpm.models.Payment;
import doancnpm.models.Post;
import doancnpm.models.Tutor;
import doancnpm.repository.PaymentRepository;
import doancnpm.security.IPaymentService;

@Service
public class PaymentService implements IPaymentService{

	@Autowired
	PaymentRepository paymentRepository;
	
	@Override
	public Boolean createBill(Tutor tutor,Post post, String cost) {
		
		Payment payment = new Payment();
		
		payment.setTutor(tutor);
		payment.setAmount(cost);
		payment.setDescription("Hóa đơn thu phí nhận lớp '"+post.getTitle()+"'");
		
		paymentRepository.save(payment);
		
		return true;
	}



}
