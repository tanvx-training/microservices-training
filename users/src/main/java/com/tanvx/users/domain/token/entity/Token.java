package com.tanvx.users.domain.token.entity;

import com.tanvx.users.domain.user.entity.User;
import com.tanvx.users.infrastructure.constant.TokenType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Table(name = "_token")
public class Token {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  public String token;
  @Enumerated(EnumType.STRING)
  public TokenType tokenType = TokenType.BEARER;
  public boolean revoked;
  public boolean expired;
  private LocalDateTime createdAt;
  private String createdBy;
  private LocalDateTime updatedAt;
  private String updatedBy;
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  public User user;
}
