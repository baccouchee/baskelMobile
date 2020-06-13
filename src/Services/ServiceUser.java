/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.Dialog;
import com.codename1.ui.events.ActionListener;
import Entities.User;
import Utils.Statics;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.mindrot.jbcrypt.BCrypt;
/**
 *
 * @author Aziz Sayeb
 */
public class ServiceUser {
     public ArrayList<User> users;
    
    public static ServiceUser instance=null;
    public boolean resultOK;
    private ConnectionRequest req;
        String message;
        public User ti=new User();


    private ServiceUser() {
         req = new ConnectionRequest();
    }

    public static ServiceUser getInstance() {
        if (instance == null) {
            instance = new ServiceUser();
        }
        return instance;
    }
    
    


    public boolean addUser(User t) {
        String url = Statics.BASE_URL + "/users/new?username=" + t.getUsername() + "&password=" + BCrypt.hashpw(t.getPassword(), BCrypt.gensalt()) + "&email=" + t.getEmail();
        req.setUrl(url);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200; //Code HTTP 200 OK
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
    }
    
    public ArrayList<User> parseTasks(String jsonText){
        try {
            users=new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String,Object> tasksListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            
            List<Map<String,Object>> list = (List<Map<String,Object>>)tasksListJson.get("root");
            for(Map<String,Object> obj : list){
                User t = new User();
                float id = Float.parseFloat(obj.get("id").toString());
                t.setId((int)id);
                t.setUsername((obj.get("username").toString()));
                t.setPassword(obj.get("password").toString());
                t.setEmail(obj.get("email").toString());                
                users.add(t);
            }
            
            
        } catch (IOException ex) {
            
        }
        return users;
    }
    

    public ArrayList<User> getAllTasks(){
        String url = Statics.BASE_URL+"/users/";
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                users = parseTasks(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return users;
    }

      public User parseUser(String jsonText){
       
                User t = new User();
              try {
            users=new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String,Object> tasksListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
          //  System.out.println(tasksListJson.get(0));
           
                float id = Float.parseFloat(tasksListJson.get("id").toString());
                t.setId((int)id);
                
                t.setUsername(tasksListJson.get("username").toString());
                t.setEmail(tasksListJson.get("email").toString());
                t.setPassword(tasksListJson.get("password").toString());
                
                users.add(t);
            
            
            
        } catch (IOException ex) {
            
        }
        return t;
    }
     
     public User getUser(String username){
        String url ="http://localhost/baskeltt/web/app_dev.php/find/"+username;
       System.out.println(url);
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
               
                ti = parseUser(new String(req.getResponseData()));
                 //System.out.println("chnia mochkol "+tasks);
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return ti;
    }
     

   
}
