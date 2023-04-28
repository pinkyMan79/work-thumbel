package one.terenin.repository;

import one.terenin.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository  extends JpaRepository<UserEntity, UUID> {
}
