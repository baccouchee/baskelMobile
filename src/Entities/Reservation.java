/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

/**
 *
 * @author Omar
 */
public class Reservation {
    
    int id,idU,idE;
    String qrCode;

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    public Reservation(int id, int idU, int idE, String qrCode) {
        this.id = id;
        this.idU = idU;
        this.idE = idE;
        this.qrCode = qrCode;
    }
    public Reservation() {
    }

    public Reservation(int id, int idU, int idE) {
        this.id = id;
        this.idU = idU;
        this.idE = idE;
    }

    public Reservation(int idU, int idE) {
        this.idU = idU;
        this.idE = idE;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdU() {
        return idU;
    }

    public void setIdU(int idU) {
        this.idU = idU;
    }

    public int getIdE() {
        return idE;
    }

    public void setIdE(int idE) {
        this.idE = idE;
    }

    @Override
    public String toString() {
        return "Reservation{" + "id=" + id + ", idU=" + idU + ", idE=" + idE + ", qrCode=" + qrCode + '}';
    }


    
    
}
