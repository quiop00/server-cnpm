package doancnpm.security;

import doancnpm.models.Post;
import doancnpm.models.Tutor;
import doancnpm.payload.request.PaymentRequest;

public interface IPaymentService {
	Boolean createBill(Tutor tutor,Post post,String cost);
}
