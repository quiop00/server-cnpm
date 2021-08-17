package doancnpm.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import doancnpm.models.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Long>{
	
}
