package lk.esoft.EM6125_SDP_CW1API.repository;

import lk.esoft.EM6125_SDP_CW1API.entity.FuelStation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.HashMap;


public interface FuelStationRepository extends JpaRepository<FuelStation,Integer> {


}
