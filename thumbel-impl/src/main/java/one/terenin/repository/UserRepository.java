package one.terenin.repository;

import one.terenin.entity.FileEntity;
import one.terenin.entity.UserEntity;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserRepository  extends JpaRepository<UserEntity, UUID> {

    UserEntity findUserEntityByLoginAndHashPassword(String login, String hashPassword);
    UserEntity findUserEntityByLogin(String login);

}
