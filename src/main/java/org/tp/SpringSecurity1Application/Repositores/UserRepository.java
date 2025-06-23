package org.tp.SpringSecurity1Application.Repositores;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.tp.SpringSecurity1Application.Entites.User;
@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
    User findByEmailId(String username);
}
