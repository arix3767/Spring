package com.kowalczyk.studentclasses.converters.VoucherConverters;

import com.kowalczyk.studentclasses.converters.Converter;
import com.kowalczyk.studentclasses.dto.VoucherDto;
import com.kowalczyk.studentclasses.entity.Voucher;

public enum VoucherToVoucherDtoConverter implements Converter<Voucher, VoucherDto> {

    INSTANCE;

    @Override
    public VoucherDto convert(Voucher voucher) {
        return VoucherDto.builder()
                .id(voucher.getId())
                .price(voucher.getPrice())
                .student(voucher.getStudent())
                .build();
    }
}
