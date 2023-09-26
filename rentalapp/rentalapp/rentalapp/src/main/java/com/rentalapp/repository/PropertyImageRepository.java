package com.rentalapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rentalapp.entity.PropertyImage;

public interface PropertyImageRepository extends JpaRepository<PropertyImage, Long> {

}
