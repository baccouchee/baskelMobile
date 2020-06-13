/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import Entities.CategorieE;
import Entities.Reservation;
import Utils.CnxRequest;
import Utils.Statics;
import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.List;
import com.codename1.ui.events.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;


/**
 *
 * @author bhk
 */
public class ServiceReservation {

    ConnectionRequest req;
    ArrayList<Reservation> reservation;
    
    public ServiceReservation() {
        req=CnxRequest.getInstance().getConnectionRequest();
    }
    
    public ArrayList<Reservation> getReservation(int idU) {
        String url=Statics.BASE_URL+"/all/res/user="+idU;
        req.setUrl(url);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent a) {
                reservation=parseReservation(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return reservation;
    }
    
    private ArrayList<Reservation> parseReservation(String json) {
        try {
            reservation =new ArrayList<>();
            JSONParser parser=new JSONParser();
            Map<String, Object> catJson=parser.parseJSON(new CharArrayReader(json.toCharArray()));
            ArrayList<Map<String, Object>> liste=(ArrayList<Map<String, Object>>)catJson.get("root");
            for (Map<String, Object> obj : liste) {
                Reservation c = new Reservation();
                c.setId((int)Float.parseFloat(obj.get("id").toString()));
                c.setIdU((int)Float.parseFloat(((Map<String, Object>)obj.get("idU")).get("id").toString()));
                c.setIdE((int)Float.parseFloat(((Map<String, Object>)obj.get("idE")).get("id").toString()));
                c.setQrCode(obj.get("qrcode").toString());
                reservation.add(c);
            }
        } catch (IOException ex) {
            System.out.println(ex);
        }
        return reservation;
    }
    
        public ArrayList<Reservation> delReservation(int id) {
        String url=Statics.BASE_URL+"/all/del/"+id;
        req.setUrl(url);

        NetworkManager.getInstance().addToQueueAndWait(req);
        return reservation;
    }
}
