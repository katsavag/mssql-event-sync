package com.katsadourose.primary_service.repository.secondary;

import com.katsadourose.primary_service.entity.UserMessageOutbox;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional(readOnly = true)
public interface UserMessageOutboxRepository extends JpaRepository<UserMessageOutbox, Long>
{

    @Query("SELECT u FROM UserMessageOutbox u where u.isPublished=false ORDER BY u.createdAt DESC")
    List<UserMessageOutbox> findAllUnpublishedOrderedByNewestFirst();
}
