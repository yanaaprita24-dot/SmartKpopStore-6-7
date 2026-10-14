package com.mycompany.kpopstore;

public class PhotoCard extends ItemKpop implements DapatDinilai {
    private String namaMember;

    public PhotoCard(String nama, String grup, int tahunRilis, int harga, String namaMember) {
        super(nama, grup, tahunRilis, harga);
        setNamaMember(namaMember);
        catatObjekBaru();
    }

    public String getNamaMember() { return this.namaMember; }

    public final void setNamaMember(String namaMember) {
        if (namaMember == null || namaMember.trim().isEmpty()) {
            throw new IllegalArgumentException("Nama member tidak boleh kosong!");
        }
        this.namaMember = namaMember.trim();
    }

    @Override
    public void tampilkanInfo() {
        System.out.printf("[PhotoCard]  Nama: %-18s | Grup: %-12s | Tahun: %d | Harga: Rp%d | Member: %s%n",
                getNama(), getGrup(), getTahunRilis(), getHarga(), this.namaMember);
    }

    @Override
    public void caraBeli() {
        System.out.println("   -> Info Beli: PhotoCard dikirim dalam sleeve khusus anti lecek.");
    }

    @Override
    public void hitungOngkosKirim() {
        System.out.println("   -> [ONGKIR PHOTOCARD] Ongkos kirim Rp8.000 (ringan, cukup amplop).");
    }

    @Override
    public void beriRating(int bintang) {
        System.out.println("   -> [ULASAN PHOTOCARD] Kualitas cetak kartu ini mendapat rating " + bintang + "/5 bintang.");
    }
}