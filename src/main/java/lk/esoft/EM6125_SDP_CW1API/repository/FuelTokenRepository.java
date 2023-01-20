package lk.esoft.EM6125_SDP_CW1API.repository;

import lk.esoft.fulemanagementsystem.entity.FuelToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface FuelTokenRepository extends JpaRepository<FuelToken,Integer> {


    @Modifying
    @Query(value = "UPDATE fuel_token SET status = ?2 WHERE tid = ?1",nativeQuery = true)
    void changeTokenStatus(int tid, String status);

    @Query(value = "select * from fuel_token where username_fk=?1 ORDER BY tid DESC Limit 1",nativeQuery = true)
    FuelToken getQRGeneratingToken(String username);

}
