package com.example.buku_saku.POJO;

public class FileData {

    private String fileName;
    private String soal;
    private String idSoal;

    public String getIdSoal() {
        return idSoal;
    }

    public void setIdSoal(String idSoal) {
        this.idSoal = idSoal;
    }

    public String getSoal() {
        return soal;
    }

    public void setSoal(String soal) {
        this.soal = soal;
    }

    public FileData(String fileName, String soal, String idSoal) {
        this.fileName = fileName;
        this.soal = soal;
        this.idSoal=idSoal;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}


