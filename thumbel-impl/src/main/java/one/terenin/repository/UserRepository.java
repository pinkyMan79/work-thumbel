package one.terenin.repository;

import one.terenin.entity.FileEntity;
import one.terenin.entity.UserEntity;
import org.hibernate.annotations.Cache;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserRepository  extends JpaRepository<UserEntity, UUID> {


    UserEntity findUserEntityByLoginAndHashPassword(String login, String hashPassword);
    //@Cacheable(value="user_cache")
    UserEntity findUserEntityByLogin(String login);
    boolean existsByLogin(String login);
    //@CacheEvict(value = "user_cache", condition = "login!=null")
    void deleteByLogin(String login);

    @Query("select e.friends from UserEntity e")
    List<UserEntity> findAllSubscribersByLogin(String login);
}
