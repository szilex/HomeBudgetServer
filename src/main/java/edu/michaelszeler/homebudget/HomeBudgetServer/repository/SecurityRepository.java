package edu.michaelszeler.homebudget.HomeBudgetServer.repository;

import edu.michaelszeler.homebudget.HomeBudgetServer.entity.Security;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SecurityRepository extends JpaRepository<Security, Void>, JpaSpecificationExecutor<Security> {

}