package com.mycompany.kpopstore;

public class Member {
    private String id;
    private String nama;

    public Member(String id, String nama) throws DataTidakValidException {
        setId(id);
        setNama(nama);
    }

    public String getId() { return this.id; }
    public String getNama() { return this.nama; }

    public final void setId(String id) throws DataTidakValidException {
        if (id == null || id.trim().isEmpty()) {
            throw new DataTidakValidException("ID member tidak boleh kosong!");
        }
        this.id = id.trim().toUpperCase();
    }

    public final void setNama(String nama) throws DataTidakValidException {
        if (nama == null || nama.trim().isEmpty()) {
            throw new DataTidakValidException("Nama member tidak boleh kosong!");
        }
        this.nama = nama.trim();
    }

    public void tampilkanInfo() {
        System.out.printf("ID: %-8s | Nama: %s%n", this.id, this.nama);
    }
}