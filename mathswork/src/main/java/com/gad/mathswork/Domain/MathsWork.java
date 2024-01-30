package com.gad.mathswork.Domain;

import lombok.*;


import jakarta.persistence.*;

@Entity
@Table(name = "mathswork")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MathsWork {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NonNull
  private int data;
}
