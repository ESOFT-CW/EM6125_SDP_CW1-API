package lk.esoft.EM6125_SDP_CW1API.repository;

import lk.esoft.fulemanagementsystem.entity.FuelToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface FuelTokenRepository extends JpaRepository<FuelToken,Integer> {




}
