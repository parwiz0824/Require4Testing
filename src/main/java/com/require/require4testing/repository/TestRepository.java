package com.require.require4testing.repository;


import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.require.require4testing.model.Test;

@Repository
public interface TestRepository extends JpaRepository<Test, Long> {
    List<Test> findByStatus(Boolean status); 
    List<Test> findByUserUsername(String username);

    @Query("SELECT t FROM Test t WHERE t.user.role = :role")
    List<Test> findByUserRole(@Param("role") String role);
}
