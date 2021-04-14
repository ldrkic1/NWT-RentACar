package ba.unsa.etf.Repository;

import ba.unsa.etf.demo.Models.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GrpcRepository extends JpaRepository<Event, Long> {
}
