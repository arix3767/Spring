package com.kowalczyk.studentclasses.repository;

import com.kowalczyk.studentclasses.entity.Voucher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoucherRepository extends JpaRepository<Voucher,Long> {
}
