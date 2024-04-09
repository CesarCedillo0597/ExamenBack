package com.ExamenBack.workersmicroservice.repository;

import com.ExamenBack.workersmicroservice.model.Gender;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GenderRepository extends JpaRepository<Gender, Long> {
    // Aquí puedes definir métodos de repositorio personalizados si los necesitas
}