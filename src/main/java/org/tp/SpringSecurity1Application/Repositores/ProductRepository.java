package org.tp.SpringSecurity1Application.Repositores;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.tp.SpringSecurity1Application.Entites.Product;
@Repository
public interface ProductRepository extends JpaRepository<Product,Integer> {
}
