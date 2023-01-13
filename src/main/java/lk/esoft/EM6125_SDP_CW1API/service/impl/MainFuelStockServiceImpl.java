package lk.esoft.EM6125_SDP_CW1API.service.impl;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class MainFuelStockServiceImpl implements MainFuelStockService {

    @Autowired
    private MainFuelStockRepository mainFuelStockRepository;

    @Autowired
    private AuditServiceImpl auditService;
    @Autowired
    private ModelMapper modelMapper;

    public int saveMainFuelStock(MainFuelStockDTO mainFuelStockDTO) {
        if (mainFuelStockRepository.existsById(mainFuelStockDTO.getMfs_id())) {
            auditService.saveAudit("saveMainFuelStock","PASS:Save Main Fuel Stock "+mainFuelStockDTO.getMain_stock());
            return VarList.Not_Found;
        } else {
            mainFuelStockRepository.save(modelMapper.map(mainFuelStockDTO, MainFuelStock.class));
            auditService.saveAudit("saveMainFuelStock","FAIL:Save Main Fuel Stock "+mainFuelStockDTO.getMain_stock());
            return VarList.Created;
        }
    }
    public MainFuelStockDTO getMainFuelStock() {
        MainFuelStock mainFuelStock=mainFuelStockRepository.findById(1).orElse(null);
        return modelMapper.map(mainFuelStock,MainFuelStockDTO.class);
    }


    public List<MainFuelStockDTO> getMainStockDetails() {
        List<MainFuelStock> fuelTokenDTOList = mainFuelStockRepository.findAll();
        auditService.saveAudit("getMainStockDetails","PASS:Get All Stock details For admin panel");
        return modelMapper.map(fuelTokenDTOList, new TypeToken<ArrayList<MainFuelStockDTO>>() {
        }.getType());
    }
}
