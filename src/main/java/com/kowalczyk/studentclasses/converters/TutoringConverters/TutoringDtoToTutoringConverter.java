package com.kowalczyk.studentclasses.converters.TutoringConverters;

import com.kowalczyk.studentclasses.converters.Converter;
import com.kowalczyk.studentclasses.dto.TutoringDto;
import com.kowalczyk.studentclasses.entity.Tutoring;

public enum TutoringDtoToTutoringConverter implements Converter<TutoringDto, Tutoring> {

    INSTANCE;

    @Override
    public Tutoring convert(TutoringDto tutoringDto) {
        return Tutoring.builder()
                .price(tutoringDto.getPrice())
                .id(tutoringDto.getId())
                .student(tutoringDto.getStudent())
                .term(tutoringDto.getTerm())
                .subject(tutoringDto.getSubject())
                .build();
    }
}
