package com.kowalczyk.studentclasses.service;

import com.kowalczyk.studentclasses.converters.VoucherConverters.VoucherDtoToVoucherConverter;
import com.kowalczyk.studentclasses.converters.VoucherConverters.VoucherToVoucherDtoConverter;
import com.kowalczyk.studentclasses.dto.VoucherDto;
import com.kowalczyk.studentclasses.entity.Voucher;
import com.kowalczyk.studentclasses.repository.VoucherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class VoucherService {

    private final VoucherRepository voucherRepository;

    public List<VoucherDto> getAll() {
        return voucherRepository.findAll().stream()
                .map(VoucherToVoucherDtoConverter.INSTANCE::convert)
                .toList();
    }

    public void addVoucher(VoucherDto voucherDto) {
        Voucher voucher = VoucherDtoToVoucherConverter.INSTANCE.convert(voucherDto);
        voucherRepository.save(voucher);
    }
}
