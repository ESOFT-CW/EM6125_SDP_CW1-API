package lk.esoft.EM6125_SDP_CW1API.service.impl;


import com.google.zxing.WriterException;
import lk.esoft.EM6125_SDP_CW1API.dto.FuelTokenDTO;
import lk.esoft.EM6125_SDP_CW1API.dto.FuelTokenResponseDTO;
import lk.esoft.EM6125_SDP_CW1API.service.FuelTokenService;
import lk.esoft.EM6125_SDP_CW1API.util.QRCodeGenerator;

import java.io.IOException;

public class FuelTokenServiceImpl implements FuelTokenService {
    @Override
    public FuelTokenResponseDTO getAllQRandDetails(String username) {
        FuelTokenResponseDTO fuelTokenResponseDTO = new FuelTokenResponseDTO();
        FuelTokenDTO fuelTokenDTO = modelMapper.map(fuelTokenRepository.getQRGeneratingToken(username), FuelTokenDTO.class);
        String qr = generateQRString(fuelTokenDTO);
        try {
            byte[] qrimage = QRCodeGenerator.getQRCodeImage(qr, 200, 200);
            fuelTokenResponseDTO.setQrString(qrimage);

            // TODO: 12/5/2022 SET AVAILABLE QUOTA
            //fuelTokenResponseDTO.getAvailableQuota();
        } catch (WriterException e) {
            e.printStackTrace();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }


        return fuelTokenResponseDTO;
    }

    private String generateQRString(FuelTokenDTO fuelTokenDTO) {
        String s = "" + fuelTokenDTO.getTid().toString() + "" + fuelTokenDTO.getFillingTimeAndDate().toString() + "" +
                fuelTokenDTO.getRequestQuota().toString() + "" +
                fuelTokenDTO.getStatus() + "" +
                fuelTokenDTO.getTokenExpDate().toString() + "" +
                fuelTokenDTO.getVehicleRegNo().toString() + "" +
                fuelTokenDTO.getFuelStationFk().getFid().toString() + "" +
                fuelTokenDTO.getPidFk().getPid().toString() + "" + fuelTokenDTO.getUsernameFk().getUsername() + "";
        auditService.saveAudit("generateQRString","PASS:Generate QR String"+fuelTokenDTO.getVehicleRegNo());
        return s;
    }

}
