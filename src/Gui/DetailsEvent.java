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
import Services.ServiceEvent;
import Services.ServiceReservation;
import Services.ServiceUser;
import Utils.Statics;
import com.codename1.components.ImageViewer;
import com.codename1.components.ShareButton;
import com.codename1.components.SpanLabel;
import com.codename1.io.FileSystemStorage;
import com.codename1.io.Log;
import com.codename1.l10n.ParseException;
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.messaging.Message;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.URLImage;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.ImageIO;
import com.codename1.ui.util.Resources;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author Omar
 */
public class DetailsEvent extends Form {
    Image imgs;
    ImageViewer imgv;
    EncodedImage enc;
    int idE;
    private ArrayList<Reservation> reservation;
 
        public DetailsEvent(Form previous, Event a, CategorieE c, String username) throws ParseException {
                Home t = new Home(username);
        t.addMenu(this, username);
                        try {
            enc=EncodedImage.create("/loading.png");
        } catch (IOException ex) {
            System.out.println(ex);
        }
        setTitle("Détails Event");
        setLayout(BoxLayout.y());
        String img=a.getPhoto();
        String url="http://localhost/baskeltt/web/app_dev.php/images/"+img;
        System.out.println(url);
        imgs = URLImage.createToStorage(enc, url, url ,URLImage.RESIZE_SCALE_TO_FILL);
        imgv = new ImageViewer(imgs);
        Button reserver=new Button("Inscription");
                  reserver.addActionListener(e -> {
         Reservation em= new Reservation();
              ServiceEvent sb= new ServiceEvent();
                      boolean test=false;
                      User u = ServiceUser.getInstance().getUser(username);
                      ServiceReservation rs=new ServiceReservation();
                      reservation=rs.getReservation(u.getId());
                      sb.Emprunter(a.getId(),u.getId());
                      for (Reservation r : reservation){
             idE = r.getIdE();
                         if (a.getId()==idE)
                             test=true;
                         
                          }  

                      if(test){
                      Dialog.show("ERROR", "Vous êtes déjà inscrit à cette événement", new Command("OK"));
                      }
                      else{
                  
                       Message m = new Message("Merci pour votre inscription à notre événement "+ a.getNom());
                 Display.getInstance().sendMessage(new String[] {u.getEmail()}, "Baskel: Inscription événement", m);
                       Dialog.show("Succes", "Merci pour votre inscription", new Command("OK"));
                      }                    
                     
        });
                  
        ShareButton sb = new ShareButton();
sb.setText("Share Screenshot");
this.add(sb);

Image screenshot = Image.createImage(this.getWidth(), this.getHeight());
this.revalidate();
this.setVisible(true);
this.paintComponent(screenshot.getGraphics(), true);

String imageFile = FileSystemStorage.getInstance().getAppHomePath() + "screenshot.png";
try(OutputStream os = FileSystemStorage.getInstance().openOutputStream(imageFile)) {
    ImageIO.getImageIO().save(screenshot, os, ImageIO.FORMAT_PNG, 1);
} catch(IOException err) {
    Log.e(err);
}
sb.setImageToShare(imageFile, "image/png");
        Container C2= new Container(new BoxLayout(BoxLayout.Y_AXIS));
        addAll(C2.add(imgv),new Label("Nom: "+a.getNom()),new Label("Catégorie : "+c.getNom()), new SpanLabel("Description : "+a.getDescription()), new SpanLabel("Date: "+a.getDateDeb()+" / "+a.getDateFin()), new SpanLabel("Emplacement: "+a.getEmplacement()),reserver);
        getToolbar().addMaterialCommandToRightBar("Retour", FontImage.MATERIAL_ARROW_BACK, e->previous.showBack());
    }
    
}
