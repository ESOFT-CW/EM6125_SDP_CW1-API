package lk.esoft.EM6125_SDP_CW1API.service;


public interface FuelTokenService {
    int changeTokenStatus(int tid, String status);

    int generateToken(FuelTokenDTO fuelTokenDTO);

    int generateTokenInFirstTime(FuelTokenDTO fuelTokenDTO);


}
