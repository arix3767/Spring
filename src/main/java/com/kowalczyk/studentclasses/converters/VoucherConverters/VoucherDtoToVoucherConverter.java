package com.kowalczyk.studentclasses.converters.VoucherConverters;

import com.kowalczyk.studentclasses.converters.Converter;
import com.kowalczyk.studentclasses.dto.VoucherDto;
import com.kowalczyk.studentclasses.entity.Voucher;

public enum VoucherDtoToVoucherConverter implements Converter<VoucherDto, Voucher> {

    INSTANCE;

    @Override
    public Voucher convert(VoucherDto voucherDto) {
        return Voucher.builder()
                .id(voucherDto.getId())
                .price(voucherDto.getPrice())
                .student(voucherDto.getStudent())
                .build();
    }
}
