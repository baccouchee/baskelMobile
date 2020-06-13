/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import Entities.CategorieE;
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
public class ServiceCategorie {

    ConnectionRequest req;
    ArrayList<CategorieE> categories;
    
    public ServiceCategorie() {
        req=CnxRequest.getInstance().getConnectionRequest();
    }
    
    public ArrayList<CategorieE> getCategories() {
        String url=Statics.BASE_URL+"/all/cat";
        req.setUrl(url);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent a) {
                categories=parseCategories(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return categories;
    }
    
    private ArrayList<CategorieE> parseCategories(String json) {
        try {
            categories=new ArrayList<>();
            JSONParser parser=new JSONParser();
            Map<String, Object> catJson=parser.parseJSON(new CharArrayReader(json.toCharArray()));
            ArrayList<Map<String, Object>> liste=(ArrayList<Map<String, Object>>)catJson.get("root");
            for (Map<String, Object> cat : liste) {
                CategorieE c = new CategorieE();
                c.setId((int)Float.parseFloat(cat.get("id").toString()));
                c.setNom(cat.get("nomC").toString());
                categories.add(c);
            }
        } catch (IOException ex) {
            System.out.println(ex);
        }
        return categories;
    }
}
