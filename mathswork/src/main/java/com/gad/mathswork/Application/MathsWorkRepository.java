package com.gad.mathswork.Application;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gad.mathswork.Domain.MathsWork;
@Repository
public interface MathsWorkRepository extends JpaRepository<MathsWork, Long> {
 //Page<MathsWork> findAllByOrderByidDesc( Pageable pageable);

  

}