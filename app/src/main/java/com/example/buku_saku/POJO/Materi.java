package com.example.buku_saku.POJO;

public class Materi {

    private String id;
    private String materi;
    private FileData fileData;

    private String soal;

    public String getSoal() {
        return soal;
    }

    public void setSoal(String soal) {
        this.soal = soal;
    }

    public Materi(FileData fileData) {
        this.id = id;
        this.materi = materi;
        this.fileData = fileData;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMateri() {
        return materi;
    }

    public void setMateri(String materi) {
        this.materi = materi;
    }

    public FileData getFileData() {
        return fileData;
    }

    public void setFileData(FileData fileData) {
        this.fileData = fileData;
    }
}

