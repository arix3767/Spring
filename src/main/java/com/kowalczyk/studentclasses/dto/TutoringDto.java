package com.kowalczyk.studentclasses.dto;

import com.kowalczyk.studentclasses.enums.Subject;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TutoringDto {

    private Long id;
    private BigDecimal price;
    private Subject subject;
    private LocalDateTime term;
    private String student;
}
