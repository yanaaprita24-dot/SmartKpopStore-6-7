package com.mycompany.kpopstore;

public abstract class ItemKpop {
    private String nama;
    private String grup;
    private int tahunRilis;
    private int harga;

    private static int totalItemBerhasilDibuat = 0;

    public ItemKpop(String nama, String grup, int tahunRilis, int harga) throws DataTidakValidException {
        setNama(nama);
        setGrup(grup);
        setTahunRilis(tahunRilis);
        setHarga(harga);
    }

    protected static void catatObjekBaru() {
        totalItemBerhasilDibuat++;
    }

    public static int getTotalItemBerhasilDibuat() {
        return totalItemBerhasilDibuat;
    }

    public String getNama() { return this.nama; }
    public String getGrup() { return this.grup; }
    public int getTahunRilis() { return this.tahunRilis; }
    public int getHarga() { return this.harga; }

    public final void setNama(String nama) throws DataTidakValidException {
        if (nama == null || nama.trim().isEmpty()) {
            throw new DataTidakValidException("Nama item tidak boleh kosong!");
        }
        this.nama = nama.trim();
    }

    public final void setGrup(String grup) throws DataTidakValidException {
        if (grup == null || grup.trim().isEmpty()) {
            throw new DataTidakValidException("Nama grup/artis tidak boleh kosong!");
        }
        this.grup = grup.trim();
    }

    public final void setTahunRilis(int tahunRilis) throws DataTidakValidException {
        if (tahunRilis < 1990 || tahunRilis > 2030) {
            throw new DataTidakValidException("Tahun rilis tidak valid (1990-2030)!");
        }
        this.tahunRilis = tahunRilis;
    }

    public final void setHarga(int harga) throws DataTidakValidException {
        if (harga <= 0) {
            throw new DataTidakValidException("Harga harus lebih dari 0!");
        }
        this.harga = harga;
    }

    public void tampilkanInfo() {
        System.out.printf("Nama: %-22s | Grup: %-14s | Tahun: %d | Harga: Rp%d%n",
                this.nama, this.grup, this.tahunRilis, this.harga);
    }

    public void caraBeli() {
        System.out.println("   -> Info Beli: Item dibeli langsung di toko K-Pop.");
    }

    public abstract void hitungOngkosKirim();
}