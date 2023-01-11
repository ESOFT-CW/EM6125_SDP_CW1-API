package lk.esoft.EM6125_SDP_CW1API.repository;

import lk.esoft.fulemanagementsystem.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface VehicleRepository extends JpaRepository<Vehicle,Integer> {


}
