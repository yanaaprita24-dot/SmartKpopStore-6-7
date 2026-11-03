package com.mycompany.kpopstore;

public class AlbumKpop extends ItemKpop implements DapatDipreorder, DapatDinilai {
    private int jumlahLagu;

    public AlbumKpop(String nama, String grup, int tahunRilis, int harga, int jumlahLagu) throws DataTidakValidException {
        super(nama, grup, tahunRilis, harga);
        setJumlahLagu(jumlahLagu);
        catatObjekBaru();
    }

    public int getJumlahLagu() { return this.jumlahLagu; }

    public final void setJumlahLagu(int jumlahLagu) throws DataTidakValidException {
        if (jumlahLagu <= 0) {
            throw new DataTidakValidException("Jumlah lagu harus lebih dari 0!");
        }
        this.jumlahLagu = jumlahLagu;
    }

    @Override
    public void tampilkanInfo() {
        System.out.printf("[Album]      Nama: %-18s | Grup: %-12s | Tahun: %d | Harga: Rp%d | Lagu: %d%n",
                getNama(), getGrup(), getTahunRilis(), getHarga(), this.jumlahLagu);
    }

    @Override
    public void caraBeli() {
        System.out.println("   -> Info Beli: Album dikirim via ekspedisi, sudah termasuk photocard acak.");
    }

    @Override
    public void hitungOngkosKirim() {
        System.out.println("   -> [ONGKIR ALBUM] Ongkos kirim Rp15.000 (reguler, dibungkus bubble wrap).");
    }

    @Override
    public void prosesPreorder() {
        System.out.println("   -> [PREORDER ALBUM] Album masuk antrean preorder, bonus photocard dipilih acak.");
    }

    @Override
    public void beriRating(int bintang) {
        System.out.println("   -> [ULASAN ALBUM] Album ini mendapat rating " + bintang + "/5 bintang.");
    }
}