package com.NaggroTask.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.NaggroTask.entity.Account;

public interface AccountRepository extends JpaRepository<Account, Integer> {

}
