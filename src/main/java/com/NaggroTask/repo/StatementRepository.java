package com.NaggroTask.repo;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.NaggroTask.entity.Statement;

public interface StatementRepository extends JpaRepository<Statement, Integer> {

	List<Statement> findByAccountId(Integer accountId);
}
