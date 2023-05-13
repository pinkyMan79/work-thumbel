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
@CacheConfig(cacheNames = "us")
public interface UserRepository  extends JpaRepository<UserEntity, UUID> {


    UserEntity findUserEntityByLoginAndHashPassword(String login, String hashPassword);
    @Cacheable(key = "#login")
    UserEntity findUserEntityByLogin(String login);
    @Cacheable(key = "#login")
    boolean existsByLogin(String login);
    @CacheEvict(key = "#login")
    void deleteByLogin(String login);

    @Query("select e.friends from UserEntity e")
    List<UserEntity> findAllSubscribersByLogin(String login);
}
