package com.vung.restful.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vung.restful.domain.Entity.User;
@Repository
public interface UserRepository extends JpaRepository<User, Long>{

    public User save(User user);
    public User findByEmail(String Email);
    public List<User> findAll();
    boolean existsByEmail(String email);
}
