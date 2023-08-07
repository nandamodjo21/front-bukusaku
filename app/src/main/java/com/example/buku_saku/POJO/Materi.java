package com.example.buku_saku.POJO;

public class Materi {

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

    public Materi(String materi, String fileMateri) {
        this.materi = materi;
        this.fileMateri = fileMateri;
    }
}
