/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import Entities.Event;
import Utils.CnxRequest;
import Utils.Statics;
import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.MultipartRequest;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.io.rest.Response;
import com.codename1.io.rest.Rest;
import com.codename1.ui.List;
import com.codename1.ui.events.ActionListener;
import com.codename1.util.Base64;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

/**
 *
 * @author Mehdi
 */
public class ServiceEvent {
   private ConnectionRequest req;
    private ArrayList<Event> events;
    
    public ServiceEvent() {
        req=CnxRequest.getInstance().getConnectionRequest();
    }
    
    public ArrayList<Event> getEvent() {
        String url=Statics.BASE_URL+"/all";
        req.setUrl(url);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent a) {
                events=parseEvent(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return events;
    }
    
    private ArrayList<Event> parseEvent(String json) {
        try {
            events=new ArrayList<>();
            JSONParser parser=new JSONParser();
            Map<String, Object> eventsJson=parser.parseJSON(new CharArrayReader(json.toCharArray()));
            ArrayList<Map<String, Object>> liste=(ArrayList<Map<String, Object>>)eventsJson.get("root");
            for (Map<String, Object> obj : liste) {
                Event e = new Event();
                e.setId((int)Float.parseFloat(obj.get("id").toString()));
                e.setNom(obj.get("nom").toString());
                e.setPhoto(obj.get("photo").toString());
                e.setDateDeb(obj.get("dateDeb").toString());
                e.setDateFin(obj.get("dateFin").toString());
                e.setEmplacement(obj.get("emplacement").toString());
                e.setDescription(obj.get("description").toString());
                e.setCat((int)Float.parseFloat(((Map<String, Object>)obj.get("cat")).get("id").toString()));
                events.add(e);
            }
        } catch (IOException ex) {
            System.out.println(ex);
        }
        return events;
    }
    
             public void Emprunter(int idE,int idU) {
        MultipartRequest con = new MultipartRequest();// crÃ©ation d'une nouvelle demande de connexion
        String Url = "http://localhost/baskeltt/web/app_dev.php/all/res/event="+idE+"&user="+idU;
        con.setUrl(Url);// Insertion de l'URL de notre demande de connexion

        con.addResponseListener((ee) -> {
            String str = new String(con.getResponseData());//RÃ©cupÃ©ration de la rÃ©ponse du serveur
            System.out.println(str);//Affichage de la rÃ©ponse serveur sur la console

        });
        NetworkManager.getInstance().addToQueueAndWait(con);// Ajout de notre demande de connexion Ã  la file d'attente du NetworkManager
    }
}
