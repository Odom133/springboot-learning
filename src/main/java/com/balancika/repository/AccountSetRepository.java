package com.balancika.repository;

import com.balancika.entity.AccountSet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountSetRepository extends JpaRepository<AccountSet, Long> {

    List<AccountSet> findAllByGroupId(String groupId);

}
