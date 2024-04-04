package com.filipangelov.petshop.repository;

import com.filipangelov.petshop.domain.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("""
        select u from User u where u.budget > 0
        """)
    List<User> findAllByBudget();
}
