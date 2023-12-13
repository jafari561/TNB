package com.yazoo.redevablemicroservice.repository;

import com.yazoo.redevablemicroservice.model.Redevable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface RedevableRepository extends JpaRepository<Redevable, String> {
    List<Redevable> findByCin(String cin);
}
