package com.tanvx.measurements.domain.measurement.entity;

import com.tanvx.measurements.domain.city.entity.City;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "_measurement")
public class Measurement {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private Double temperature;
  private LocalDateTime measurementTime;
  private Boolean deleteFlg;
  private LocalDateTime createdAt;
  private String createdBy;
  private LocalDateTime updatedAt;
  private String updatedBy;
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "city_id")
  private City city;
}
