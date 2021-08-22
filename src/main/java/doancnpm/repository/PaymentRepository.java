package doancnpm.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import doancnpm.models.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long>{
	
}
