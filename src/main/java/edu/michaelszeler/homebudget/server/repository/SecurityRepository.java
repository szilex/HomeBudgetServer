package edu.michaelszeler.homebudget.server.repository;

import edu.michaelszeler.homebudget.server.entity.Security;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SecurityRepository extends JpaRepository<Security, Void>, JpaSpecificationExecutor<Security> {

}