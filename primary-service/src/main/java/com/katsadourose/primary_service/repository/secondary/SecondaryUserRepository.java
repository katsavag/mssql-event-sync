package com.katsadourose.primary_service.repository.secondary;

import com.katsadourose.primary_service.entity.SecondaryUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional(readOnly = true)
public interface SecondaryUserRepository extends JpaRepository<SecondaryUserEntity, Integer> {

    @Query("SELECT u FROM SecondaryUserEntity u ORDER BY u.id DESC")
    List<SecondaryUserEntity> findAllOrderedByNewestFirst();
}
