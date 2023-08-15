package com.example.model.repository;


import com.example.model.veri.Identity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IdentityRepository extends JpaRepository < Identity,Long>{

}

