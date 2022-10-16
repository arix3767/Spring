package com.kowalczyk.studentclasses.dto;

import com.kowalczyk.studentclasses.entity.Student;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VoucherDto {

    private Long id;
    private BigDecimal price;
    private Student student;
}