# Nama : Defrizal Yahdiyan Risyad
# NIM : 2206131
# Kelas : Ilmu Komputer C1 2022


# Janji
Saya Defrizal Yahdiyan Risyad dengan NIM 2206131 mengerjakan Tugas Praktikum 2
dalam mata kuliah Desain Pemograman Berorientasi Objek untuk keberkahanNya 
maka saya tidak melakukan kecurangan seperti yang telah dispesifikasikan. Aamiin.

---

# Aplikasi Manajemen Mahasiswa

## Deskripsi
Aplikasi Manajemen Mahasiswa adalah sebuah program sederhana yang digunakan untuk melakukan CRUD (Create, Read, Update, Delete) data mahasiswa. Program ini dibangun menggunakan Java dan menggunakan database MySQL sebagai penyimpanan data.

## Fitur
- Menambahkan data mahasiswa baru.
- Mengupdate data mahasiswa.
- Menghapus data mahasiswa.
- Menampilkan daftar mahasiswa yang tersimpan di database.
- Menampilkan dialog/prompt error jika masih ada input yang kosong saat insert/update.
- Menampilkan dialog/prompt error jika sudah ada NIM yang sama saat insert.


## Alur Program
1. Pengguna membuka aplikasi.
2. Aplikasi menampilkan daftar mahasiswa yang tersimpan di database.
3. Pengguna dapat melakukan operasi CRUD melalui antarmuka grafis yang disediakan.
4. Jika pengguna memilih untuk menambahkan data baru, aplikasi akan menampilkan form input.
5. Pengguna mengisi data mahasiswa pada form input dan mengklik tombol "Add".
6. Aplikasi melakukan validasi input, jika ada input yang kosong atau NIM yang sudah ada, akan ditampilkan pesan kesalahan.
7. Jika input valid, data mahasiswa baru akan ditambahkan ke database dan daftar mahasiswa akan diperbarui.
8. Pengguna dapat mengubah atau menghapus data mahasiswa dengan memilih baris yang sesuai dari tabel dan menggunakan tombol "Update" atau "Delete".
9. Setelah selesai, pengguna dapat menutup aplikasi.

## Desain Program
- Program menggunakan Java Swing untuk antarmuka grafis.
- Terdapat kelas `Menu` yang merupakan frame utama aplikasi.
- Kelas `Database` digunakan untuk melakukan koneksi dan eksekusi query ke database MySQL.
- Terdapat kelas `Mahasiswa` untuk merepresentasikan data mahasiswa.
- Program menggunakan JDBC untuk interaksi dengan database.

## Konfigurasi Database
Pastikan telah mengonfigurasi koneksi database MySQL dengan mengubah informasi koneksi yang terdapat dalam kelas `Database` sesuai dengan pengaturan database yang digunakan.

## Cara Menjalankan Program
1. Pastikan JDK telah terinstal di komputer Anda.
2. Buka proyek dalam IDE Java (seperti IntelliJ IDEA, Eclipse, atau NetBeans).
3. Konfigurasikan koneksi database sesuai dengan pengaturan MySQL Anda.
4. Jalankan kelas `Menu` untuk memulai aplikasi.
