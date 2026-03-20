# My Profile App
**Sigit Kurnia Hartawan — NIM: 123140033 — Tugas 4 PAM**

---

## Deskripsi Aplikasi
My Profile App adalah aplikasi Android yang menampilkan halaman profil pengguna secara interaktif. Aplikasi ini dibuat sebagai pengembangan dari tugas sebelumnya dengan menambahkan fitur edit profil, dark mode, dan pola MVVM untuk pengelolaan data yang lebih terstruktur.

---

## Fitur Utama
- 🌙 **Dark Mode / Light Mode** — Ganti tampilan gelap atau terang sesuai selera
- 👤 **Profil Interaktif** — Foto profil, nama, NIM, dan bio ditampilkan dengan rapi
- 📋 **Informasi Kontak** — Tampilkan atau sembunyikan kontak dengan animasi halus
- ✏️ **Edit Profil & Kontak** — Ubah nama, bio, email, nomor HP, dan lokasi dalam satu form
- ✅ **Notifikasi Sukses** — Muncul notifikasi kecil setelah perubahan berhasil disimpan
- 🔄 **Animasi** — Setiap card dan form muncul/hilang dengan animasi yang smooth

---

## Cara Penggunaan

**Tampilan Awal**  
Saat aplikasi dibuka, layar menampilkan toggle dark mode, foto profil, nama, NIM, bio, dan tombol Tampilkan Kontak.

**Menampilkan Informasi Kontak**  
1. Tekan tombol biru "Tampilkan Kontak".
2. Card kontak muncul dengan animasi dari atas ke bawah.
3. Informasi yang ditampilkan: Email, Phone, dan Location.

**Menyembunyikan Informasi Kontak**  
1. Tekan tombol abu-abu "Sembunyikan Kontak".
2. Card kontak hilang dengan animasi dari bawah ke atas.

**Edit Profil & Kontak**  
1. Tekan tombol "✏️ Edit Profil & Kontak" di bagian bawah layar.
2. Form edit muncul dengan dua bagian: Edit Profil (nama & bio) dan Edit Kontak (email, phone, lokasi).
3. Ubah data yang diinginkan lalu tekan "💾 Simpan Semua Perubahan".
4. Notifikasi hijau kecil akan muncul di bawah layar sebagai konfirmasi.

**Toggle Dark Mode**  
1. Tekan Switch di bagian atas layar.
2. Tampilan berubah antara Light Mode dan Dark Mode secara otomatis.

---

## Preview

| Light Mode | Dark Mode |
|:---:|:---:|
| <img width="300" alt="Tampilan Awal" src="https://github.com/user-attachments/assets/ee690a9b-3537-4204-8f8b-340c0d9bdfac" /> | <img width="300" alt="Tampilan Awal Dark" src="https://github.com/user-attachments/assets/0ac765a5-e95e-4aaf-9c3b-cbee0d81cec3" /> |
| *Tampilan Awal* | *Tampilan Awal (Dark Mode)* |
| <img width="300" alt="Form Edit" src="https://github.com/user-attachments/assets/46902347-c317-4d02-9bed-4b2716917601" /> | <img width="300" alt="Form Edit Dark" src="https://github.com/user-attachments/assets/0a581355-ae7b-4a03-ad67-9d1a92f3b6cc" /> |
| *Form Edit Profil & Kontak* | *Form Edit Profil & Kontak (Dark Mode)* |
| <img width="300" alt="Profil Diupdate" src="https://github.com/user-attachments/assets/18521b48-b135-4cb0-9446-90f42cdc5678" /> | <img width="300" alt="Profil Diupdate Dark" src="https://github.com/user-attachments/assets/aa3c6855-52d6-45a0-bddd-42d23f511caa" /> |
| *Tampilan Profil Diupdate* | *Tampilan Profil Diupdate (Dark Mode)* |

---

## Teknologi yang Digunakan
- **Kotlin Multiplatform** — Supaya kode bisa dipakai di Android maupun platform lain tanpa ditulis ulang
- **Jetpack Compose** — Dipakai untuk membangun tampilan aplikasi, mulai dari tombol, card, hingga animasi
- **ViewModel + StateFlow** — Menyimpan dan mengelola data profil agar tidak hilang saat layar di-rotate atau ada perubahan tampilan
- **State Hoisting** — Teknik pengelolaan input form agar data mengalir dengan rapi dari komponen induk ke komponen anak
- **AnimatedVisibility** — Memberikan efek animasi saat card kontak dan form edit muncul atau menghilang
