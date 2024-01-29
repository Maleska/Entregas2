package com.consulsoftware.entregas.listas;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.animation.LinearInterpolator;

public class Licencia implements Parcelable {
   private Boolean Activo;
   private String Inicio;
   private String Vence;

   public  Licencia(Boolean Activo,String Inicio, String Vence){
       this.Activo = Activo;
       this.Inicio = Inicio;
       this.Vence = Vence;
   }

   public  Licencia(){

   }
   protected Licencia(Parcel in){
       if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
           Activo = in.readBoolean();
       }
       Inicio = in.readString();
       Vence = in.readString();
   }

   public static final Creator<Licencia> CREATOR = new Creator<Licencia>() {
       @Override
       public Licencia createFromParcel(Parcel source) {
           return null;
       }

       @Override
       public Licencia[] newArray(int size) {
           return new Licencia[0];
       }
   };

    public Boolean getActivo() {
        return Activo;
    }

    public String getInicio() {
        return Inicio;
    }

    public String getVence() {
        return Vence;
    }

    public void setActivo(Boolean activo) {
        this.Activo = activo;
    }

    public void setInicio(String inicio) {
        Inicio = inicio;
    }

    public void setVence(String vence) {
        Vence = vence;
    }

    @Override
    public String toString(){
        return  "Licencia{" +
                "Activo=" + Activo + '\'' +
                "Inicio=" + Inicio + + '\'' +
                "Vence=" + Vence+ '\'' +
                "}";
    }
    @Override
    public int describeContents(){
        return 0;
    }
    @Override
    public  void writeToParcel(Parcel dest, int flags){
        dest.writeString(Inicio);
        dest.writeString(Vence);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            dest.writeBoolean(Activo);
        }
    }
}
