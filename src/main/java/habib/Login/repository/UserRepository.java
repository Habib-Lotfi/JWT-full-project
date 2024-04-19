package habib.Login.repository;

import habib.Login.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
    public User findByEmail(String email);

}
