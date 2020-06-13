/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gui;

import Entities.CategorieE;
import Entities.Event;
import Entities.Reservation;
import Entities.User;
import com.codename1.components.ImageViewer;
import com.codename1.ui.Button;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Container;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Form;
import com.codename1.ui.URLImage;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import Utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import Gui.Home;
import Services.ServiceCategorie;
import Services.ServiceEvent;
import com.codename1.l10n.ParseException;
import com.codename1.ui.FontImage;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.util.Resources;
import javafx.scene.control.Separator;



/**
 *
 * @author bhk
 */
public  class AllEvent extends Form{
        private AllEvent current;
    private ArrayList<Event> events;
    private ArrayList<User> users;
    private ArrayList<CategorieE> categories;
    private ArrayList<Reservation> rese;
    private EncodedImage enc;
    private Container eventC;
        Image imgs;
    ImageViewer imgv;
        Resources theme;
    
    public AllEvent(String username) {
        current=this;
        Home t = new Home(username);
        t.addMenu(this, username);
                try {
            enc=EncodedImage.create("/loading.png");
        } catch (IOException ex) {
            System.out.println(ex);
        }
        ServiceEvent as=new ServiceEvent();
        ServiceCategorie cas=new ServiceCategorie();
        events=as.getEvent();
        categories=cas.getCategories();
        setTitle("Nos événement");
        setLayout(BoxLayout.y());
        ComboBox listeCat=new ComboBox();
        listeCat.addItem("Tout");
        for (CategorieE c : categories)
            listeCat.addItem(c);
        listeCat.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (listeCat.getSelectedIndex()==0)
                    trier(-1,username);
                else
                    trier(((CategorieE)listeCat.getSelectedItem()).getId(), username);
            }
        });
        add(listeCat);
        eventC=new Container();
        eventC.setLayout(BoxLayout.y());
        for (Event a : events)
            addEvent(a,username);
        add(eventC);

        getToolbar().addMaterialCommandToRightBar("", FontImage.MATERIAL_EVENT_AVAILABLE, e-> {
            try {
                new MyRes(this, username).show();
            } catch (ParseException ex) {
               
            }
        });
       
    }
    
    private void addEvent(Event a, String username) {
        Container produitCA=new Container(BoxLayout.y());
        //URLImage imageUrl=URLImage.createToStorage(enc, Statics.IMAGE_URL+a.getPhoto(), Statics.IMAGE_URL+a.getPhoto(),URLImage.RESIZE_SCALE_TO_FILL);
        //ImageViewer image=new ImageViewer(imageUrl);
        
        Label des = new Label(a.getDescription());
        Button details=new Button(a.getNom());
        Separator se = new Separator();
                String img=a.getPhoto();
        String url="http://localhost/baskeltt/web/images/"+img;
        System.out.println(url);
        imgs = URLImage.createToStorage(enc, url, url ,URLImage.RESIZE_SCALE_TO_FILL);
        imgv = new ImageViewer(imgs);
        details.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CategorieE ca=null;
                for (CategorieE c : categories) {
                    if (c.getId()==a.getCat()) {
                        ca=c;
                        break;
                    }
                }
                try {
                    new DetailsEvent(current, a, ca, username).show();
                  
                    
                } catch (ParseException ex) {
                   System.out.println(ex);
                }
            }
        });
        
        produitCA.addAll(imgv,details);        
        produitCA.setLeadComponent(details);
        eventC.add(produitCA);
        
    }
    
    private void trier(int id, String username) {
        eventC.removeAll();
        if (id==-1) {
            for (Event a : events)
                addEvent(a,username);
        } else {
            for (Event a : events) {
                if (a.getCat()==id)
                    addEvent(a,username);
            }
        }
        refreshTheme();
    }
    
    public void addRese(Reservation r, String username) throws ParseException {
                Event ca=null;
                for (Event c : events) {
                    if (c.getId()==r.getIdE()) {
                        ca=c;
                        break;
                    }
                }
                //new MyRes(this, r, ca, username).show();
            }
   

}
