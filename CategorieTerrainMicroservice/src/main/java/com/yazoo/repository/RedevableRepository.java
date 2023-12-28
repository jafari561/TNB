package com.yazoo.repository;

import com.yazoo.model.Redevable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RedevableRepository extends JpaRepository<Redevable, String> {
    Redevable findByCin(String cin);
}
