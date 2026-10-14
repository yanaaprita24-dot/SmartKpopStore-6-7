package com.mycompany.kpopstore;

import java.util.Scanner;

public class SmartKpopStore {

    public static void cariItem(String kataKunci, ItemKpop[] daftar, int jumlah) {
        System.out.println("Mencari item dengan nama/grup: " + kataKunci);
        boolean ditemukan = false;
        for (int i = 0; i < jumlah; i++) {
            if (daftar[i].getNama().toLowerCase().contains(kataKunci.toLowerCase())
                    || daftar[i].getGrup().toLowerCase().contains(kataKunci.toLowerCase())) {
                System.out.print("- Ditemukan: ");
                daftar[i].tampilkanInfo();
                ditemukan = true;
            }
        }
        if (!ditemukan) System.out.println("Item tidak ditemukan.");
    }

    public static void cariItem(int tahun, ItemKpop[] daftar, int jumlah) {
        System.out.println("Mencari item dengan tahun rilis: " + tahun);
        boolean ditemukan = false;
        for (int i = 0; i < jumlah; i++) {
            if (daftar[i].getTahunRilis() == tahun) {
                System.out.print("- Ditemukan: ");
                daftar[i].tampilkanInfo();
                ditemukan = true;
            }
        }
        if (!ditemukan) System.out.println("Item tidak ditemukan.");
    }

    public static void cariItem(String grup, int tahun, ItemKpop[] daftar, int jumlah) {
        System.out.println("Mencari item grup " + grup + " tahun " + tahun);
        boolean ditemukan = false;
        for (int i = 0; i < jumlah; i++) {
            if (daftar[i].getGrup().equalsIgnoreCase(grup) && daftar[i].getTahunRilis() == tahun) {
                System.out.print("- Ditemukan: ");
                daftar[i].tampilkanInfo();
                ditemukan = true;
            }
        }
        if (!ditemukan) System.out.println("Item tidak ditemukan.");
    }

    public static void simulasikanTransaksi(ItemKpop item) {
        System.out.println("\n=== SIMULASI TRANSAKSI ===");
        item.tampilkanInfo();  
        item.caraBeli();      
        item.hitungOngkosKirim();   
        System.out.println("Transaksi untuk 1 unit \"" + item.getNama() + "\" berhasil disimulasikan.");
    }

    public static void simulasikanTransaksi(ItemKpop item, int jumlah) {
        System.out.println("\n=== SIMULASI TRANSAKSI (Jumlah: " + jumlah + ") ===");
        item.tampilkanInfo();
        item.caraBeli();
        item.hitungOngkosKirim(); 
        int total = item.getHarga() * jumlah;
        System.out.printf("Total untuk %d unit \"%s\" = Rp%d%n", jumlah, item.getNama(), total);
    }

    private static int bacaAngka(Scanner scanner, String pesan) {
        while (true) {
            System.out.print(pesan);
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Input harus berupa angka!");
            }
        }
    }

    public static void aksiKhusus(Scanner scanner, ItemKpop[] daftar, int jumlah) {
        if (jumlah == 0) {
            System.out.println("Belum ada item.");
            return;
        }
        System.out.println("\n-- Pilih Item untuk Aksi Khusus --");
        for (int i = 0; i < jumlah; i++) {
            System.out.print((i + 1) + ". ");
            daftar[i].tampilkanInfo();
        }
        int nomor = bacaAngka(scanner, "Nomor item: ");
        if (nomor < 1 || nomor > jumlah) {
            System.out.println("Nomor tidak valid!");
            return;
        }
        ItemKpop item = daftar[nomor - 1];

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
                    System.out.println("Rating harus 1-5!");
                } else {
                    DapatDinilai bisaDinilai = (DapatDinilai) item;
                    bisaDinilai.beriRating(bintang);
                }
            } else {
                System.out.println("   -> Maaf, item ini TIDAK bisa diberi rating.");
            }
        } else if (aksi == 3) {
            item.hitungOngkosKirim();
        } else {
            System.out.println("Pilihan tidak valid.");
        }
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            ItemKpop[] daftarItem = new ItemKpop[15];
            int jumlahItem = 0;
            boolean isRunning = true;

            daftarItem[jumlahItem++] = new AlbumKpop("Get Up", "NewJeans", 2023, 250000, 6);
            daftarItem[jumlahItem++] = new AlbumKpop("MAXIDENT", "Stray Kids", 2022, 280000, 7);
            daftarItem[jumlahItem++] = new Lightstick("Carat Bong", "SEVENTEEN", 2023, 450000, "Ver.3");
            daftarItem[jumlahItem++] = new Lightstick("Hammer Bong", "BLACKPINK", 2020, 500000, "Ver.2");
            daftarItem[jumlahItem++] = new PhotoCard("PC Jennie Born Pink", "BLACKPINK", 2022, 35000, "Jennie");
            daftarItem[jumlahItem++] = new PhotoCard("PC Felix Rock-Star", "Stray Kids", 2024, 30000, "Felix");

            System.out.println("=========================================");
            System.out.println("     SELAMAT DATANG DI K-POP STORE!      ");
            System.out.println("=========================================");

            while (isRunning) {
                System.out.println("\nMenu Utama:");
                System.out.println("1. Tambah Item K-Pop");
                System.out.println("2. Lihat Daftar Item");
                System.out.println("3. Cari Item (Fitur Overloading)");
                System.out.println("4. Simulasi Transaksi (Dynamic Binding Demo)");
                System.out.println("5. Aksi Khusus Item (Interface & instanceof)");
                System.out.println("6. Keluar");
                int pilihan = bacaAngka(scanner, "Pilih Menu (1-6): ");

                switch (pilihan) {
                    case 1 -> {
                        if (jumlahItem < daftarItem.length) {
                            System.out.println("\n-- Pilih Jenis Item --");
                            System.out.println("1. Album");
                            System.out.println("2. Lightstick");
                            System.out.println("3. PhotoCard");
                            int jenis = bacaAngka(scanner, "Pilihan (1-3): ");

                            if (jenis < 1 || jenis > 3) {
                                System.out.println("Jenis tidak valid!");
                            } else {
                                System.out.print("Masukkan Nama Item: ");
                                String nama = scanner.nextLine();
                                System.out.print("Masukkan Grup/Artis: ");
                                String grup = scanner.nextLine();
                                int tahun = bacaAngka(scanner, "Masukkan Tahun Rilis: ");
                                int harga = bacaAngka(scanner, "Masukkan Harga (Rp): ");

                                try {
                                    if (jenis == 1) {
                                        int lagu = bacaAngka(scanner, "Masukkan Jumlah Lagu: ");
                                        daftarItem[jumlahItem] = new AlbumKpop(nama, grup, tahun, harga, lagu);
                                    } else if (jenis == 2) {
                                        System.out.print("Masukkan Versi Lightstick: ");
                                        String versi = scanner.nextLine();
                                        daftarItem[jumlahItem] = new Lightstick(nama, grup, tahun, harga, versi);
                                    } else {
                                        System.out.print("Masukkan Nama Member: ");
                                        String member = scanner.nextLine();
                                        daftarItem[jumlahItem] = new PhotoCard(nama, grup, tahun, harga, member);
                                    }
                                    jumlahItem++;
                                    System.out.println("Sukses! Item berhasil ditambahkan.");
                                } catch (IllegalArgumentException e) {
                                    System.out.println("Gagal: " + e.getMessage());
                                }
                            }
                        } else {
                            System.out.println("Maaf, kapasitas toko penuh!");
                        }
                    }
                    case 2 -> {
                        System.out.println("\n--- Daftar Item di K-Pop Store ---");
                        if (jumlahItem == 0) {
                            System.out.println("Belum ada item yang tersimpan.");
                        } else {
                            for (int i = 0; i < jumlahItem; i++) {
                                System.out.print((i + 1) + ". ");
                                daftarItem[i].tampilkanInfo();
                                daftarItem[i].caraBeli();

                                daftarItem[i].hitungOngkosKirim();

                                if (daftarItem[i] instanceof DapatDipreorder) {
                                    DapatDipreorder itemPreorder = (DapatDipreorder) daftarItem[i];
                                    itemPreorder.prosesPreorder();
                                }
                                if (daftarItem[i] instanceof DapatDinilai) {
                                    DapatDinilai itemBisaDinilai = (DapatDinilai) daftarItem[i];
                                    itemBisaDinilai.beriRating(5);
                                }
                                System.out.println();
                            }
                            System.out.println("* Total Item Berhasil Dibuat: "
                                    + ItemKpop.getTotalItemBerhasilDibuat());
                        }
                        System.out.print("Tekan Enter untuk melanjutkan...");
                        scanner.nextLine();
                    }
                    case 3 -> {
                        System.out.println("\n-- Fitur Cari Item --");
                        System.out.println("1. Cari berdasarkan Nama/Grup (String)");
                        System.out.println("2. Cari berdasarkan Tahun (Integer)");
                        System.out.println("3. Cari berdasarkan Grup + Tahun");
                        int mode = bacaAngka(scanner, "Pilih (1-3): ");

                        if (mode == 1) {
                            System.out.print("Masukkan Nama/Grup: ");
                            cariItem(scanner.nextLine(), daftarItem, jumlahItem);
                        } else if (mode == 2) {
                            int tahun = bacaAngka(scanner, "Masukkan Tahun: ");
                            cariItem(tahun, daftarItem, jumlahItem);
                        } else if (mode == 3) {
                            System.out.print("Masukkan Grup: ");
                            String grup = scanner.nextLine();
                            int tahun = bacaAngka(scanner, "Masukkan Tahun: ");
                            cariItem(grup, tahun, daftarItem, jumlahItem);
                        } else {
                            System.out.println("Pilihan tidak valid.");
                        }
                        System.out.print("Tekan Enter untuk melanjutkan...");
                        scanner.nextLine();
                    }
                    case 4 -> {
                        if (jumlahItem == 0) {
                            System.out.println("Belum ada item untuk disimulasikan.");
                        } else {
                            System.out.println("\n-- Pilih Item untuk Simulasi Transaksi --");
                            for (int i = 0; i < jumlahItem; i++) {
                                System.out.print((i + 1) + ". ");
                                daftarItem[i].tampilkanInfo();
                            }
                            int nomor = bacaAngka(scanner, "Nomor item: ");
                            if (nomor < 1 || nomor > jumlahItem) {
                                System.out.println("Nomor tidak valid!");
                            } else {
                                System.out.println("Mau simulasikan dengan jumlah tertentu? (y/n)");
                                String jawab = scanner.nextLine().trim().toLowerCase();
                                ItemKpop itemTerpilih = daftarItem[nomor - 1]; 
                                if (jawab.equals("y")) {
                                    int jml = bacaAngka(scanner, "Jumlah: ");
                                    simulasikanTransaksi(itemTerpilih, jml); 
                                } else {
                                    simulasikanTransaksi(itemTerpilih); 
                                }
                            }
                        }
                        System.out.print("Tekan Enter untuk melanjutkan...");
                        scanner.nextLine();
                    }
                    case 5 -> {
                        aksiKhusus(scanner, daftarItem, jumlahItem);
                        System.out.print("Tekan Enter untuk melanjutkan...");
                        scanner.nextLine();
                    }
                    case 6 -> {
                        System.out.println("Terima kasih telah berkunjung ke K-Pop Store!");
                        isRunning = false;
                    }
                    default -> System.out.println("Pilihan tidak valid. Silakan masukkan angka 1-6.");
                }
            }
        }
    }
}