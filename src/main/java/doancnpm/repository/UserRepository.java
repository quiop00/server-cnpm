package doancnpm.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;

import doancnpm.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	
	
	Optional<User> findByUsername(String username);

	Boolean existsByUsername(String username);

	Boolean existsByEmail(String email);
	User findOneByusername(String username);
	
	
//	@Modifying
//	@Transactional
	
//	@Query("select id from users where username = :username")
//	public int findByusername(@Param("username") String username);
	//User findUserById(Long id);
}