package ml.market.cors.repository.mail;

import ml.market.cors.domain.mail.entity.EmailStateDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository("emailStateRepo")
public interface EmailStateRepository extends JpaRepository<EmailStateDAO, String>{
    @Override
    Optional<EmailStateDAO> findById(String email);

}
