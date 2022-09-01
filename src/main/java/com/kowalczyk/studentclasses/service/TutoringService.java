package com.kowalczyk.studentclasses.service;

import com.kowalczyk.studentclasses.converters.TutoringConverters.TutoringDtoToTutoringConverter;
import com.kowalczyk.studentclasses.converters.TutoringConverters.TutoringToTutoringDtoConverter;
import com.kowalczyk.studentclasses.dto.TutoringDto;
import com.kowalczyk.studentclasses.repository.TutoringRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TutoringService {

    private final TutoringRepository tutoringRepository;

    public List<TutoringDto> getAll() {
        return tutoringRepository.findAll().stream()
                .map(TutoringToTutoringDtoConverter.INSTANCE::convert)
                .toList();
    }

    public void addTutoring(TutoringDto tutoringDto) {
        tutoringRepository.save(TutoringDtoToTutoringConverter.INSTANCE.convert(tutoringDto));
    }
}
