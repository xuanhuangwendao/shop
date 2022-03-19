package com.xing.shop.repository;

import com.xing.shop.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Account getAccountByUsernameEquals(String username);

}