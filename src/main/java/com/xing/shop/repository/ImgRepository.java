package com.xing.shop.repository;

import com.xing.shop.domain.model.Img;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImgRepository extends JpaRepository<Img, String> {
}