package springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import springboot.entity.Wikimedia;

public interface WikimediaDataRepository extends JpaRepository<Wikimedia, Long> {
}
