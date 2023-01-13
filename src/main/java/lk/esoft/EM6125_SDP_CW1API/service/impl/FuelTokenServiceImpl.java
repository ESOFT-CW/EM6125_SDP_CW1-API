package lk.esoft.EM6125_SDP_CW1API.service.impl;


import com.google.zxing.WriterException;
import lk.esoft.EM6125_SDP_CW1API.dto.FuelTokenDTO;
import lk.esoft.EM6125_SDP_CW1API.dto.FuelTokenResponseDTO;
import lk.esoft.EM6125_SDP_CW1API.service.FuelTokenService;
import lk.esoft.EM6125_SDP_CW1API.util.QRCodeGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.Date;

@Service
@Transactional
public class FuelTokenServiceImpl implements FuelTokenService {

    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    private FuelTokenRepository fuelTokenRepository;


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

    @Override
    public int changeTokenStatus(int tid, String status) {
        try {
            if (fuelTokenRepository.existsById(tid)) {
                fuelTokenRepository.changeTokenStatus(tid, status);

                FuelToken fuelToken = fuelTokenRepository.findById(tid).orElse(null);
                System.out.println(fuelToken.getUsernameFk().getEmail());
                sendMail(fuelToken.getUsernameFk().getEmail(), status, fuelToken.getFillingTimeAndDate(), fuelToken.getFuelStationFk().getStationName());

                auditService.saveAudit("changeTokenStatus","PASS:Change Token Status :"+status);
                return VarList.Accepted;
            } else {
                auditService.saveAudit("changeTokenStatus","FAIL:Change Token Status :"+status);
                return VarList.Not_Found;
            }
        } catch (Exception e) {
            auditService.saveAudit("changeTokenStatus","FAIL:Change Token Status :"+status +"Exception");
            return 0;
        }
    }

    public int sendMail(
            String to, String status, Date dateAndTime, String fuelStation) throws MessagingException, IOException {

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("esoftassigmenets@gmail.com");
            message.setTo(to);
            switch (status) {
                case "ACCEPTED":
                    message.setSubject("Your Fuel Token ACCEPTED!");
                    String atext = "<!DOCTYPE html>" +
                            "<html>" +
                            "<head>" +
                            "<!-- HTML Codes by Quackit.com -->" +
                            "<title>" +
                            "</title>" +
                            "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n" +
                            "<style>" +
                            "body {background-color:#ffffff;background-repeat:no-repeat;background-position:top left;background-attachment:fixed;}\n" +
                            "h1{font-family:Arial, sans-serif;color:#000000;background-color:#ffffff;}\n" +
                            "p {font-family:Georgia, serif;font-size:14px;font-style:normal;font-weight:normal;color:#000000;background-color:#ffffff;}\n" +
                            "</style>" +
                            "</head>" +
                            "<body>" +
                            "<h1></h1>" +
                            "<p>Hey " + to + ",</p>" +
                            "<p></p>" +
                            "<p>We are ACCEPTED Your Fuel Token Now!</p>" +
                            "<!-- CSS Code: Place this code in the document's head (between the 'head' tags) -->\n" +
                            "<style>" +
                            "table.GeneratedTable {" +
                            "  width: 100%;" +
                            "  background-color: #ffffff;" +
                            "  border-collapse: collapse;" +
                            "  border-width: 2px;" +
                            "  border-color: #ffcc00;" +
                            "  border-style: solid;" +
                            "  color: #000000;" +
                            "}" +
                            "" +
                            "table.GeneratedTable td, table.GeneratedTable th {" +
                            "  border-width: 2px;" +
                            "  border-color: #ffcc00;" +
                            "  border-style: solid;" +
                            "  padding: 3px;" +
                            "}" +
                            "" +
                            "table.GeneratedTable thead {" +
                            "  background-color: #ffcc00;" +
                            "}" +
                            "</style>" +
                            "" +
                            "<!-- HTML Code: Place this code in the document's body (between the 'body' tags) where the table should appear -->\n" +
                            "<table class=\"GeneratedTable\">" +
                            "  <thead>" +
                            "    <tr>" +
                            "      <th>Date And Time</th>" +
                            "      <th>Fuel Station</th>" +
                            "    </tr>" +
                            "  </thead>" +
                            "  <tbody>" +
                            "    <tr>" +
                            "      <td>"+dateAndTime+"</td>" +
                            "      <td>"+fuelStation+"</td>" +
                            "    </tr>" +
                            "  </tbody>" +
                            "</table>" +
                            "<p></p>" +
                            "<p>Thank You!</p>" +
                            "<p>Power Fuel(pvt) Ltd</p>" +
                            "</body>" +
                            "</html>";
                    message.setText(atext);
                    System.out.println("------------------------------>");
                    emailSender.send(message);
                    System.out.println("------------------------------>");
                    auditService.saveAudit("sendMail","PASS:ACCEPTED Mail Send");
                    return VarList.Created;

                case "DELIVERED":
                    message.setSubject("Your Fuel Quota DELIVERED!");
                    String dtext = "<!DOCTYPE html>" +
                            "<html>" +
                            "<head" +
                            "<!-- HTML Codes by Quackit.com -->" +
                            "<title>" +
                            "</title>" +
                            "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n" +
                            "<style>" +
                            "body {background-color:#ffffff;background-repeat:no-repeat;background-position:top left;background-attachment:fixed;}\n" +
                            "h1{font-family:Arial, sans-serif;color:#000000;background-color:#ffffff;}\n" +
                            "p {font-family:Georgia, serif;font-size:14px;font-style:normal;font-weight:normal;color:#000000;background-color:#ffffff;}\n" +
                            "</style>" +
                            "</head>" +
                            "<body>" +
                            "<h1></h1>" +
                            "<p>Hey " + to + ",</p>" +
                            "<p></p>" +
                            "<p>We are DELIVERED Your Fuel Quota To Your Fuel Station!</p>" +
                            "<!-- CSS Code: Place this code in the document's head (between the 'head' tags) -->\n" +
                            "<style>" +
                            "table.GeneratedTable {" +
                            "  width: 100%;" +
                            "  background-color: #ffffff;" +
                            "  border-collapse: collapse;" +
                            "  border-width: 2px;" +
                            "  border-color: #ffcc00;" +
                            "  border-style: solid;" +
                            "  color: #000000;" +
                            "}" +
                            "" +
                            "table.GeneratedTable td, table.GeneratedTable th {" +
                            "  border-width: 2px;" +
                            "  border-color: #ffcc00;" +
                            "  border-style: solid;" +
                            "  padding: 3px;" +
                            "}" +
                            "" +
                            "table.GeneratedTable thead {" +
                            "  background-color: #ffcc00;" +
                            "}" +
                            "</style>" +
                            "" +
                            "<!-- HTML Code: Place this code in the document's body (between the 'body' tags) where the table should appear -->\n" +
                            "<table class=\"GeneratedTable\">" +
                            "  <thead>" +
                            "    <tr>" +
                            "      <th>Date And Time</th>" +
                            "      <th>Fuel Station</th>" +
                            "    </tr>" +
                            "  </thead>" +
                            "  <tbody>" +
                            "    <tr>" +
                            "      <td>"+dateAndTime+"</td>" +
                            "      <td>"+fuelStation+"</td>" +
                            "    </tr>" +
                            "  </tbody>" +
                            "</table>" +
                            "<p></p>" +
                            "<p>Thank You!</p>" +
                            "<p>Power Fuel(pvt) Ltd</p>" +
                            "</body>" +
                            "</html>";
                    message.setText(dtext);
                    System.out.println("------------------------------>");
                    emailSender.send(message);
                    System.out.println("------------------------------>");
                    auditService.saveAudit("sendMail","PASS:DELIVERED Mail Send");
                    return VarList.Created;
                case "CANCELED":
                    message.setSubject("Your Fuel Token CANCELED!");
                    String ctext = "<!DOCTYPE html>" +
                            "<html>" +
                            "<head>" +
                            "<!-- HTML Codes by Quackit.com -->" +
                            "<title>" +
                            "</title>" +
                            "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">" +
                            "<style>" +
                            "body {background-color:#ffffff;background-repeat:no-repeat;background-position:top left;background-attachment:fixed;}" +
                            "h1{font-family:Arial, sans-serif;color:#000000;background-color:#ffffff;}" +
                            "p {font-family:Georgia, serif;font-size:14px;font-style:normal;font-weight:normal;color:#000000;background-color:#ffffff;}" +
                            "</style>" +
                            "</head>" +
                            "<body>" +
                            "<h1></h1>" +
                            "<p>Hey " + to + ",</p>" +
                            "<p></p>" +
                            "<p>We are CANCELED Your Fuel Token Now!</p>" +
                            "<!-- CSS Code: Place this code in the document's head (between the 'head' tags) -->\n" +
                            "<style>" +
                            "table.GeneratedTable {" +
                            "  width: 100%;" +
                            "  background-color: #ffffff;" +
                            "  border-collapse: collapse;" +
                            "  border-width: 2px;" +
                            "  border-color: #ffcc00;" +
                            "  border-style: solid;" +
                            "  color: #000000;" +
                            "}" +
                            "" +
                            "table.GeneratedTable td, table.GeneratedTable th {" +
                            "  border-width: 2px;" +
                            "  border-color: #ffcc00;" +
                            "  border-style: solid;" +
                            "  padding: 3px;" +
                            "}" +
                            "" +
                            "table.GeneratedTable thead {" +
                            "  background-color: #ffcc00;" +
                            "}" +
                            "</style>" +
                            "" +
                            "<!-- HTML Code: Place this code in the document's body (between the 'body' tags) where the table should appear -->\n" +
                            "<table class=\"GeneratedTable\">" +
                            "  <thead>" +
                            "    <tr>" +
                            "      <th>Date And Time</th>" +
                            "      <th>Fuel Station</th>" +
                            "    </tr>" +
                            "  </thead>" +
                            "  <tbody>" +
                            "    <tr>" +
                            "      <td>"+dateAndTime+"</td>" +
                            "      <td>"+fuelStation+"</td>" +
                            "    </tr>" +
                            "  </tbody>" +
                            "</table>" +
                            "<p></p>" +
                            "<p>Thank You!</p>" +
                            "<p>Power Fuel(pvt) Ltd</p>" +
                            "</body>" +
                            "</html>";
                    message.setText(ctext);
                    System.out.println("------------------------------>");
                    emailSender.send(message);
                    System.out.println("------------------------------>");
                    auditService.saveAudit("sendMail","PASS:CANCELED Mail Send");
                    return VarList.Created;
            }
            return VarList.Conflict;
        } catch (Exception e) {
            System.out.println(e);
            auditService.saveAudit("sendMail","FAIL:Exception");
            return VarList.Conflict;
        }
    }
}
