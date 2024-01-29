package com.consulsoftware.entregas.adapters;

public class ListAdapter {
    private String DocNum;
    private String Nombre;
    private String DocEntry;
    private String NumPedido;
    private String total;
    private String UN;
    public  ListAdapter(String DocNum, String Nombre, String DocEntry, String NumPedido, String Total, String UN){
        this.DocEntry = DocEntry;
        this.DocNum = DocNum;
        this.Nombre = Nombre;
        this.NumPedido = NumPedido;
        this.total = Total;
        this.UN = UN;
    }
    public String getDocNum(){
        return  DocNum;
    }

    public void setDocNum(String docNum) {
        DocNum = docNum;
    }

    public String getDocEntry() {
        return DocEntry;
    }

    public void setDocEntry(String docEntry) {
        DocEntry = docEntry;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getNumPedido() {
        return NumPedido;
    }
    public void setNumPedido(String numPedido) {
        NumPedido = numPedido;
    }
    public String getTotal() {
        return total;
    }
    public void setTotal(String total) {
        this.total = total;
    }
    public String getUN() {
        return UN;
    }
    public void setUN(String UN) {
        this.UN = UN;
    }
}
