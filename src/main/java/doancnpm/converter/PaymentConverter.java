package doancnpm.converter;

import doancnpm.models.Payment;
import doancnpm.payload.response.PaymentResponse;

public class PaymentConverter {
	public static PaymentResponse modelToResponse(Payment payment) {
		PaymentResponse bill = new PaymentResponse();
		
		bill.setBillId(payment.getId());
		bill.setCost(payment.getAmount());
		bill.setDescription(payment.getDescription());
		bill.setStatus(payment.getStatus().name());
		bill.setCreatedDate(payment.getCreatedDate().toString());
		
		return bill;
	}
}
