package doancnpm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import doancnpm.models.Rate;

public interface RateRepository extends  JpaRepository<Rate, Long>{

}
