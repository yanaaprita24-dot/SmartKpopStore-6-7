package com.mycompany.kpopstore;

public class Lightstick extends ItemKpop implements DapatDipreorder {
    private String versi;

    public Lightstick(String nama, String grup, int tahunRilis, int harga, String versi) throws DataTidakValidException {
        super(nama, grup, tahunRilis, harga);
        setVersi(versi);
        catatObjekBaru();
    }

    public String getVersi() { return this.versi; }

    public final void setVersi(String versi) throws DataTidakValidException {
        if (versi == null || versi.trim().isEmpty()) {
            throw new DataTidakValidException("Versi lightstick tidak boleh kosong!");
        }
        this.versi = versi.trim();
    }

    @Override
    public void tampilkanInfo() {
        System.out.printf("[Lightstick] Nama: %-18s | Grup: %-12s | Tahun: %d | Harga: Rp%d | Versi: %s%n",
                getNama(), getGrup(), getTahunRilis(), getHarga(), this.versi);
    }

    @Override
    public void caraBeli() {
        System.out.println("   -> Info Beli: Lightstick wajib pre-order & aktivasi Bluetooth di aplikasi resmi.");
    }

    @Override
    public void hitungOngkosKirim() {
        System.out.println("   -> [ONGKIR LIGHTSTICK] Ongkos kirim Rp25.000 (barang rapuh, packing ekstra).");
    }

    @Override
    public void prosesPreorder() {
        System.out.println("   -> [PREORDER LIGHTSTICK] Lightstick dipesan, dikirim saat stok batch berikutnya tiba.");
    }
}