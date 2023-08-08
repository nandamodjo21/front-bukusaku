package com.example.buku_saku.POJO;

public class Materi {



    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String materi;
    private String fileMateri;

    public String getMateri() {
        return materi;
    }

    public void setMateri(String materi) {
        this.materi = materi;
    }

    public String getFileMateri() {
        return fileMateri;
    }

    public void setFileMateri(String fileMateri) {
        this.fileMateri = fileMateri;
    }

    public Materi(String id,String materi, String fileMateri) {
        this.id=id;
        this.materi = materi;
        this.fileMateri = fileMateri;
    }
}
