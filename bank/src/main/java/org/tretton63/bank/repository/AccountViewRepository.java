package org.tretton63.bank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tretton63.bank.entity.AccountView;

public interface AccountViewRepository extends JpaRepository<AccountView, String> {
}
