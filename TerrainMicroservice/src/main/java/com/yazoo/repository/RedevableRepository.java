package com.yazoo.repository;

import com.yazoo.model.Redevable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RedevableRepository extends JpaRepository<Redevable, String> {
    Redevable findByCin(String cin);
}
