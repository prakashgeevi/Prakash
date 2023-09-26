package com.securecart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.securecart.entity.Replies;

@Repository
public interface RepliesRepository extends JpaRepository<Replies, Long>{

}
