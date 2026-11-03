package com.mycompany.kpopstore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;

public class SmartKpopStore {

    public static void cariItem(String kataKunci, ArrayList<ItemKpop> daftar) throws DataTidakDitemukanException {
        System.out.println("Mencari item dengan nama/grup: " + kataKunci);
        boolean ditemukan = false;
        for (int i = 0; i < daftar.size(); i++) {
            ItemKpop item = daftar.get(i);
            if (item.getNama().toLowerCase().contains(kataKunci.toLowerCase())
                    || item.getGrup().toLowerCase().contains(kataKunci.toLowerCase())) {
                System.out.print("- Ditemukan: ");
                item.tampilkanInfo();
                ditemukan = true;
            }
        }
        if (!ditemukan) {
            throw new DataTidakDitemukanException("Item dengan kata kunci \"" + kataKunci + "\" tidak ditemukan!");
        }
    }

    public static void cariItem(int tahun, ArrayList<ItemKpop> daftar) throws DataTidakDitemukanException {
        System.out.println("Mencari item dengan tahun rilis: " + tahun);
        boolean ditemukan = false;
        for (int i = 0; i < daftar.size(); i++) {
            ItemKpop item = daftar.get(i);
            if (item.getTahunRilis() == tahun) {
                System.out.print("- Ditemukan: ");
                item.tampilkanInfo();
                ditemukan = true;
            }
        }
        if (!ditemukan) {
            throw new DataTidakDitemukanException("Item dengan tahun rilis " + tahun + " tidak ditemukan!");
        }
    }

    public static void cariItem(String grup, int tahun, ArrayList<ItemKpop> daftar) throws DataTidakDitemukanException {
        System.out.println("Mencari item grup " + grup + " tahun " + tahun);
        boolean ditemukan = false;
        for (int i = 0; i < daftar.size(); i++) {
            ItemKpop item = daftar.get(i);
            if (item.getGrup().equalsIgnoreCase(grup) && item.getTahunRilis() == tahun) {
                System.out.print("- Ditemukan: ");
                item.tampilkanInfo();
                ditemukan = true;
            }
        }
        if (!ditemukan) {
            throw new DataTidakDitemukanException("Item grup " + grup + " tahun " + tahun + " tidak ditemukan!");
        }
    }

    public static void simulasikanTransaksi(ItemKpop item) {
        System.out.println("\n=== SIMULASI TRANSAKSI ===");
        item.tampilkanInfo();
        item.caraBeli();
        item.hitungOngkosKirim();
        System.out.println("Transaksi untuk 1 unit \"" + item.getNama() + "\" berhasil disimulasikan.");
    }

    public static void simulasikanTransaksi(ItemKpop item, int jumlah) throws DataTidakValidException {
        if (jumlah <= 0) {
            throw new DataTidakValidException("Jumlah transaksi harus lebih dari 0!");
        }
        System.out.println("\n=== SIMULASI TRANSAKSI (Jumlah: " + jumlah + ") ===");
        item.tampilkanInfo();
        item.caraBeli();
        item.hitungOngkosKirim();
        int total = item.getHarga() * jumlah;
        System.out.printf("Total untuk %d unit \"%s\" = Rp%d%n", jumlah, item.getNama(), total);
    }

    private static int bacaAngka(Scanner scanner, String pesan) {
        System.out.print(pesan);
        String teks = scanner.nextLine().trim();
        try {
            return Integer.parseInt(teks);
        } catch (NumberFormatException e) {
            throw new InputMismatchException("Input harus berupa angka, bukan \"" + teks + "\"!");
        }
    }

    public static void aksiKhusus(Scanner scanner, ArrayList<ItemKpop> daftar)
            throws DataTidakDitemukanException, DataTidakValidException {
        if (daftar.isEmpty()) {
            throw new DataTidakDitemukanException("Belum ada item di daftar!");
        }
        System.out.println("\n-- Pilih Item untuk Aksi Khusus --");
        for (int i = 0; i < daftar.size(); i++) {
            System.out.print((i + 1) + ". ");
            daftar.get(i).tampilkanInfo();
        }
        int nomor = bacaAngka(scanner, "Nomor item: ");
        if (nomor < 1 || nomor > daftar.size()) {
            throw new DataTidakDitemukanException("Item nomor " + nomor + " tidak ada di daftar!");
        }
        ItemKpop item = daftar.get(nomor - 1);

        System.out.println("\n-- Aksi untuk: " + item.getNama() + " --");
        System.out.println("1. Preorder (interface DapatDipreorder)");
        System.out.println("2. Beri Rating (interface DapatDinilai)");
        System.out.println("3. Hitung Ongkos Kirim (abstract method)");
        int aksi = bacaAngka(scanner, "Pilih aksi (1-3): ");

        if (aksi == 1) {
            if (item instanceof DapatDipreorder) {
                DapatDipreorder bisaPreorder = (DapatDipreorder) item;
                bisaPreorder.prosesPreorder();
            } else {
                System.out.println("   -> Maaf, item ini TIDAK bisa di-preorder.");
            }
        } else if (aksi == 2) {
            if (item instanceof DapatDinilai) {
                int bintang = bacaAngka(scanner, "Rating (1-5): ");
                if (bintang < 1 || bintang > 5) {
                    throw new DataTidakValidException("Rating harus antara 1 sampai 5!");
                }
                DapatDinilai bisaDinilai = (DapatDinilai) item;
                bisaDinilai.beriRating(bintang);
            } else {
                System.out.println("   -> Maaf, item ini TIDAK bisa diberi rating.");
            }
        } else if (aksi == 3) {
            item.hitungOngkosKirim();
        } else {
            throw new DataTidakValidException("Pilihan aksi tidak valid!");
        }
    }

    public static void kelolaMember(Scanner scanner, HashMap<String, Member> dataMember)
            throws DataTidakValidException, DataTidakDitemukanException {
        System.out.println("\n-- Kelola Member (HashMap) --");
        System.out.println("1. Tambah Member Baru");
        System.out.println("2. Cari Member");
        System.out.println("3. Tampilkan Semua Member");
        int opsi = bacaAngka(scanner, "Pilih (1-3): ");

        if (opsi == 1) {
            System.out.print("Masukkan ID Member: ");
            String id = scanner.nextLine();
            System.out.print("Masukkan Nama Member: ");
            String nama = scanner.nextLine();
            Member baru = new Member(id, nama);
            if (dataMember.containsKey(baru.getId())) {
                throw new DataTidakValidException("ID member " + baru.getId() + " sudah terdaftar!");
            }
            dataMember.put(baru.getId(), baru);
            System.out.println("Sukses! Member " + baru.getNama() + " berhasil didaftarkan.");
        } else if (opsi == 2) {
            System.out.print("Masukkan ID Member: ");
            String id = scanner.nextLine().trim().toUpperCase();
            if (!dataMember.containsKey(id)) {
                throw new DataTidakDitemukanException("Member dengan ID \"" + id + "\" tidak ditemukan!");
            }
            System.out.print("Member ditemukan: ");
            dataMember.get(id).tampilkanInfo();
        } else if (opsi == 3) {
            if (dataMember.isEmpty()) {
                System.out.println("Belum ada member terdaftar.");
            } else {
                System.out.println("Jumlah member: " + dataMember.size());
                for (String kunci : dataMember.keySet()) {
                    dataMember.get(kunci).tampilkanInfo();
                }
            }
        } else {
            throw new DataTidakValidException("Pilihan menu member tidak valid!");
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<ItemKpop> daftarItem = new ArrayList<>();
        HashMap<String, Member> dataMember = new HashMap<>();
        boolean isRunning = true;

        try {
            try {
                daftarItem.add(new AlbumKpop("Get Up", "NewJeans", 2023, 250000, 6));
                daftarItem.add(new AlbumKpop("MAXIDENT", "Stray Kids", 2022, 280000, 7));
                daftarItem.add(new Lightstick("Carat Bong", "SEVENTEEN", 2023, 450000, "Ver.3"));
                daftarItem.add(new Lightstick("Hammer Bong", "BLACKPINK", 2020, 500000, "Ver.2"));
                daftarItem.add(new PhotoCard("PC Jennie Born Pink", "BLACKPINK", 2022, 35000, "Jennie"));
                daftarItem.add(new PhotoCard("PC Felix Rock-Star", "Stray Kids", 2024, 30000, "Felix"));

                Member m1 = new Member("KP001", "Rani Pratama");
                Member m2 = new Member("KP002", "Dimas Saputra");
                Member m3 = new Member("KP003", "Nadia Putri");
                dataMember.put(m1.getId(), m1);
                dataMember.put(m2.getId(), m2);
                dataMember.put(m3.getId(), m3);
            } catch (DataTidakValidException e) {
                System.out.println("Gagal memuat data awal: " + e.getMessage());
            }

            System.out.println("=========================================");
            System.out.println("     SELAMAT DATANG DI K-POP STORE!      ");
            System.out.println("=========================================");

            while (isRunning) {
                try {
                    System.out.println("\nMenu Utama:");
                    System.out.println("1. Tambah Item K-Pop");
                    System.out.println("2. Lihat Daftar Item");
                    System.out.println("3. Cari Item (Fitur Overloading)");
                    System.out.println("4. Simulasi Transaksi (Dynamic Binding Demo)");
                    System.out.println("5. Aksi Khusus Item (Interface & instanceof)");
                    System.out.println("6. Kelola Member (HashMap)");
                    System.out.println("7. Keluar");
                    int pilihan = bacaAngka(scanner, "Pilih Menu (1-7): ");

                    switch (pilihan) {
                        case 1 -> {
                            try {
                                System.out.println("\n-- Pilih Jenis Item --");
                                System.out.println("1. Album");
                                System.out.println("2. Lightstick");
                                System.out.println("3. PhotoCard");
                                int jenis = bacaAngka(scanner, "Pilihan (1-3): ");
                                if (jenis < 1 || jenis > 3) {
                                    throw new DataTidakValidException("Jenis item tidak valid!");
                                }
                                System.out.print("Masukkan Nama Item: ");
                                String nama = scanner.nextLine();
                                System.out.print("Masukkan Grup/Artis: ");
                                String grup = scanner.nextLine();
                                int tahun = bacaAngka(scanner, "Masukkan Tahun Rilis: ");
                                int harga = bacaAngka(scanner, "Masukkan Harga (Rp): ");

                                if (jenis == 1) {
                                    int lagu = bacaAngka(scanner, "Masukkan Jumlah Lagu: ");
                                    daftarItem.add(new AlbumKpop(nama, grup, tahun, harga, lagu));
                                } else if (jenis == 2) {
                                    System.out.print("Masukkan Versi Lightstick: ");
                                    String versi = scanner.nextLine();
                                    daftarItem.add(new Lightstick(nama, grup, tahun, harga, versi));
                                } else {
                                    System.out.print("Masukkan Nama Member: ");
                                    String member = scanner.nextLine();
                                    daftarItem.add(new PhotoCard(nama, grup, tahun, harga, member));
                                }
                                System.out.println("Sukses! Item berhasil ditambahkan.");
                            } catch (DataTidakValidException e) {
                                System.out.println("Gagal menambah item: " + e.getMessage());
                            } finally {
                                System.out.println("[FINALLY] Proses tambah item selesai.");
                            }
                        }
                        case 2 -> {
                            System.out.println("\n--- Daftar Item di K-Pop Store ---");
                            if (daftarItem.isEmpty()) {
                                System.out.println("Belum ada item yang tersimpan.");
                            } else {
                                for (int i = 0; i < daftarItem.size(); i++) {
                                    ItemKpop item = daftarItem.get(i);
                                    System.out.print((i + 1) + ". ");
                                    item.tampilkanInfo();
                                    item.caraBeli();
                                    item.hitungOngkosKirim();
                                    if (item instanceof DapatDipreorder) {
                                        DapatDipreorder itemPreorder = (DapatDipreorder) item;
                                        itemPreorder.prosesPreorder();
                                    }
                                    if (item instanceof DapatDinilai) {
                                        DapatDinilai itemBisaDinilai = (DapatDinilai) item;
                                        itemBisaDinilai.beriRating(5);
                                    }
                                    System.out.println();
                                }
                                System.out.println("* Jumlah item di ArrayList: " + daftarItem.size());
                                System.out.println("* Total Item Berhasil Dibuat: "
                                        + ItemKpop.getTotalItemBerhasilDibuat());
                            }
                            System.out.print("Tekan Enter untuk melanjutkan...");
                            scanner.nextLine();
                        }
                        case 3 -> {
                            try {
                                System.out.println("\n-- Fitur Cari Item --");
                                System.out.println("1. Cari berdasarkan Nama/Grup (String)");
                                System.out.println("2. Cari berdasarkan Tahun (Integer)");
                                System.out.println("3. Cari berdasarkan Grup + Tahun");
                                int mode = bacaAngka(scanner, "Pilih (1-3): ");

                                if (mode == 1) {
                                    System.out.print("Masukkan Nama/Grup: ");
                                    cariItem(scanner.nextLine(), daftarItem);
                                } else if (mode == 2) {
                                    int tahun = bacaAngka(scanner, "Masukkan Tahun: ");
                                    cariItem(tahun, daftarItem);
                                } else if (mode == 3) {
                                    System.out.print("Masukkan Grup: ");
                                    String grup = scanner.nextLine();
                                    int tahun = bacaAngka(scanner, "Masukkan Tahun: ");
                                    cariItem(grup, tahun, daftarItem);
                                } else {
                                    System.out.println("Pilihan tidak valid.");
                                }
                            } catch (DataTidakDitemukanException e) {
                                System.out.println("Pencarian gagal: " + e.getMessage());
                            } finally {
                                System.out.println("[FINALLY] Proses pencarian selesai.");
                            }
                        }
                        case 4 -> {
                            if (daftarItem.isEmpty()) {
                                throw new DataTidakDitemukanException("Belum ada item untuk disimulasikan!");
                            }
                            System.out.println("\n-- Pilih Item untuk Simulasi Transaksi --");
                            for (int i = 0; i < daftarItem.size(); i++) {
                                System.out.print((i + 1) + ". ");
                                daftarItem.get(i).tampilkanInfo();
                            }
                            int nomor = bacaAngka(scanner, "Nomor item: ");
                            if (nomor < 1 || nomor > daftarItem.size()) {
                                throw new DataTidakDitemukanException("Item nomor " + nomor + " tidak ada di daftar!");
                            }
                            System.out.println("Mau simulasikan dengan jumlah tertentu? (y/n)");
                            String jawab = scanner.nextLine().trim().toLowerCase();
                            ItemKpop itemTerpilih = daftarItem.get(nomor - 1);
                            if (jawab.equals("y")) {
                                int jml = bacaAngka(scanner, "Jumlah: ");
                                simulasikanTransaksi(itemTerpilih, jml);
                            } else {
                                simulasikanTransaksi(itemTerpilih);
                            }
                            System.out.print("Tekan Enter untuk melanjutkan...");
                            scanner.nextLine();
                        }
                        case 5 -> {
                            aksiKhusus(scanner, daftarItem);
                            System.out.print("Tekan Enter untuk melanjutkan...");
                            scanner.nextLine();
                        }
                        case 6 -> {
                            try {
                                kelolaMember(scanner, dataMember);
                            } catch (DataTidakValidException | DataTidakDitemukanException e) {
                                System.out.println("Kesalahan data member: " + e.getMessage());
                            } finally {
                                System.out.println("[FINALLY] Proses kelola member selesai.");
                            }
                        }
                        case 7 -> {
                            System.out.println("Terima kasih telah berkunjung ke K-Pop Store!");
                            isRunning = false;
                        }
                        default -> System.out.println("Pilihan tidak valid. Silakan masukkan angka 1-7.");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Terjadi kesalahan input: " + e.getMessage());
                } catch (DataTidakValidException e) {
                    System.out.println("Data tidak valid: " + e.getMessage());
                } catch (DataTidakDitemukanException e) {
                    System.out.println("Data tidak ditemukan: " + e.getMessage());
                }
            }
        } finally {
            System.out.println("[FINALLY] Program selesai, scanner ditutup.");
            scanner.close();
        }
    }
}