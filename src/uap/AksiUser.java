package UAP;


import java.util.Scanner;
import java.util.HashMap;
import java.util.Map;

public class AksiUser extends Aksi {
    @Override
    public void tampilanAksi() {
        System.out.println("Aksi User:");
        System.out.println("1. Pesan Film");
        System.out.println("2. Lihat Saldo");
        System.out.println("3. Lihat List Film");
        System.out.println("4. Lihat Pesanan");
        System.out.println("5. Logout");
        System.out.println("6. Tutup Aplikasi");
    }

    @Override
    public void keluar() {
        Akun.logout();
        System.out.println("Anda telah logout.");
    }

    @Override
    public void tutupAplikasi() {
        System.out.println("Aplikasi ditutup.");
        System.exit(0);
    }

    @Override
    public void lihatListFilm() {
        for (Film film : Film.getFilms().values()) {
            System.out.println("Film: " + film.getName() + " - Deskripsi: " + film.getDescription() + " - Harga: " + film.getPrice() + " - Stok: " + film.getStock());
        }
    }

    public void lihatSaldo() {
        User currentUser = Akun.getCurrentUser();
        System.out.println("Saldo anda: " + currentUser.getSaldo());
    }

    public void pesanFilm() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Nama Film yang ingin dipesan: ");
        String name = scanner.nextLine();
        Film film = Film.getFilms().get(name);

        if (film == null) {
            System.out.println("Film yang dicari tidak ditemukan.");
            return;
        }

        System.out.print("Jumlah tiket yang ingin dipesan: ");
        int kuantitas = scanner.nextInt();
        double totalHarga = film.getPrice() * kuantitas;

        if (film.getStock() < kuantitas) {
            System.out.println("Stok tiket tidak mencukupi.");
            return;
        }

        User currentUser = Akun.getCurrentUser();

        if (currentUser.getSaldo() < totalHarga) {
        System.out.println("Harga satuan tiket " + film.getPrice());
        System.out.println("Total harga " + totalHarga);
            System.out.println("Saldo tidak mencukupi, saldo yang dimiliki " + currentUser.getSaldo());
            return;
        }

        film.setStock(film.getStock() - kuantitas);
        currentUser.setSaldo(currentUser.getSaldo() - totalHarga);
        currentUser.addPesanan(film, kuantitas);
        
        System.out.println("Harga satuan tiket " + film.getPrice());
        System.out.println("Total harga " + totalHarga);
        System.out.println("Tiket berhasil dipesan.");
    }

    public void lihatPesanan() {
        User currentUser = Akun.getCurrentUser();
        Map<String, Pesanan> pesanan = currentUser.getPesanan();

        if (pesanan.isEmpty()) {
            System.out.println("Kamu belum pernah melakukan pemesanan.");
            return;
        }

        for (Pesanan order : pesanan.values()) {
            System.out.println("Film: " + order.getFilm().getName() + " - Jumlah: " + order.getKuantitas() + " - Total Harga: " + order.getKuantitas() * order.getFilm().getPrice());
        }
    }
}
