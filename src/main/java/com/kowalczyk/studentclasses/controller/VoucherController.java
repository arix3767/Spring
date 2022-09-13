package com.kowalczyk.studentclasses.controller;

import com.kowalczyk.studentclasses.dto.VoucherDto;
import com.kowalczyk.studentclasses.service.VoucherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/voucher")
public class VoucherController {

    private final VoucherService voucherservice;

    public ResponseEntity<List<VoucherDto>> getAll() {
        List<VoucherDto> vouchers = voucherservice.getAll();
        return ResponseEntity.ok(vouchers);
    }

    public ResponseEntity<String> addVoucher(@RequestBody VoucherDto voucherDto) {
        voucherservice.addVoucher(voucherDto);
        return ResponseEntity.ok("Voucher added");

    }

}
