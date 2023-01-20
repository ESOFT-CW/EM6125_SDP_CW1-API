package lk.esoft.EM6125_SDP_CW1API.service;

public interface FuelStationService {

    int requestFuel(int quota, String username);

    int requestFuelStatusChange(String status, String username);

    List<FuelStationDTO> getAllFuelStationDetails();
    FuelStationDTO getAllFuelStationDetailsByUsername(String username);

}
