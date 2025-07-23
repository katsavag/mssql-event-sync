package com.katsadourose.primary_service.repository.primary;

import com.katsadourose.primary_service.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional(readOnly = true)
public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    @Query("SELECT u FROM UserEntity u ORDER BY u.id DESC")
    List<UserEntity> findAllOrderedByNewestFirst();
}
