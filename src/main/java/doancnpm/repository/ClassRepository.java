package doancnpm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import doancnpm.models.TakenClass;

public interface ClassRepository extends JpaRepository<TakenClass, Long>{
	
}
