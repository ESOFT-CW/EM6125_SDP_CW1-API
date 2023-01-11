package lk.esoft.EM6125_SDP_CW1API.service.impl;

import lk.esoft.EM6125_SDP_CW1API.repository.FuelStationRepository;
import lk.esoft.EM6125_SDP_CW1API.service.FuelStationService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class FuelStationServiceImpl implements FuelStationService {

    @Autowired
    private AuditServiceImpl auditService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private FuelStationRepository fuelStationRepository;


    @Override
    public int requestFuel(int quota, String username) {
        //get fuel station pK
        int fid = fuelStationRepository.getFidByUserName(username);

        if (fuelStationRepository.existsById(fid)) {
            FuelStation fuelStation=fuelStationRepository.findAllByUsername(username);
            if (fuelStation.getMax_limit()<=quota){
                fuelStationRepository.requestFuel(fid,quota);

                auditService.saveAudit("requestFuel","PASS:Fuel Station:("+""+fid+") requested Fuel Quota:"+quota);
                return VarList.Accepted;
            }else {
                auditService.saveAudit("requestFuel","Fail:Fuel Station:("+""+fid+") requested Fuel Quota:"+quota +"Is Exceed MaxLimit");
                return VarList.Not_Acceptable;
            }


        } else {
            auditService.saveAudit("requestFuel","FAIL:Fuel Station:("+""+fid+") requested Fuel Quota:"+quota);
            return VarList.Not_Found;
        }
    }

    @Override
    public int requestFuelStatusChange(String status, String username) {

        int fid = fuelStationRepository.getFidByUserName(username);

        if (fuelStationRepository.existsById(fid)) {
            fuelStationRepository.requestFuelStatusChange(status,fid);
            auditService.saveAudit("requestFuelStatusChange","PASS:Fuel Station:("+""+fid+") requested Fuel Status Change:"+status);
            return VarList.Accepted;
        } else {
            auditService.saveAudit("requestFuelStatusChange","FAIL:Fuel Station:("+""+fid+") requested Fuel  Status Change:"+status);
            return VarList.Not_Found;
        }
    }

    @Override
    public List<FuelStationDTO> getAllFuelStationDetails() {
        List<FuelStation> fuelStations = fuelStationRepository.findAll();

        auditService.saveAudit("getAllFuelStationDetails","PASS:Get All Fuel Stations Details");
        return modelMapper.map(fuelStations, new TypeToken<ArrayList<FuelStationDTO>>() {
        }.getType());
    }

    @Override
    public FuelStationDTO getAllFuelStationDetailsByUsername(String username) {
        FuelStation fuelStations = fuelStationRepository.findAllByUsername(username);
        auditService.saveAudit("getAllFuelStationDetailsByUsername","PASS:Get All Fuel Stations Details Using Username :"+username);
        return modelMapper.map(fuelStations, FuelStationDTO.class);
    }
}
