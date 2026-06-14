# ScentApp - E-Commerce Parfum (Android)

ScentApp adalah aplikasi *e-commerce* berbasis Android yang didesain khusus untuk komunitas pecinta parfum. Aplikasi ini menyediakan fitur katalog, pencarian spesifik berdasarkan *notes* parfum, keranjang belanja, manajemen pesanan (pembeli & penjual), dan pelacakan pengiriman secara langsung.

## 📸 Screenshot Aplikasi

### Halaman Utama

| Beranda | Beranda Mode Terang | Beranda Horizontal |
|:---:|:---:|:---:|
| ![Beranda](docs/Beranda.png) | ![Beranda Mode Terang](docs/Beranda%20Mode%20Terang.png) | ![Beranda Horizontal](docs/Beranda%20Horizontal.png) |

### Produk

| Detail Produk Mode Terang | Edit Produk | Favorit |
|:---:|:---:|:---:|
| ![Detail Produk](docs/Detail%20Produk%20Mode%20Terang.png) | ![Edit Produk](docs/Edit%20Produk.png) | ![Favorit](docs/Favorit.png) |

### Pencarian & Filter

| Filter Mode Terang | Pencarian Mode Terang |
|:---:|:---:|
| ![Filter](docs/Filter%20Mode%20Terang.png) | ![Pencarian](docs/Pencarian%20Mode%20Terang.png) |

### Transaksi & Keranjang

| Keranjang | Konfirmasi Pembayaran | Konfirmasi Pembayaran Bank |
|:---:|:---:|:---:|
| ![Keranjang](docs/Keranjang.png) | ![Konfirmasi Pembayaran](docs/Konfirmasi%20Pembayaran.png) | ![Konfirmasi Pembayaran Bank](docs/Konfirmasi%20Pembayaran%20Bank.png) |

### Penjual & Pengiriman

| Halaman Penjualan | Halaman Pengiriman | Memberikan Ulasan |
|:---:|:---:|:---:|
| ![Halaman Penjualan](docs/Halaman%20Penjualan.png) | ![Halaman Pengiriman](docs/Halaman%20Pengiriman.png) | ![Memberikan Ulasan](docs/Memberikan%20Ulasan%20Mode%20Terang.png) |

### Profil & Akun

| Profile | Halaman Detail Profile | Login |
|:---:|:---:|:---:|
| ![Profile](docs/Profile.png) | ![Halaman Detail Profile](docs/Halaman%20Detail%20Profile.png) | ![Login](docs/Login.png) |

### Pengaturan

| Halaman Alamat | Halaman Bahasa |
|:---:|:---:|
| ![Halaman Alamat](docs/Halaman%20Alamat.png) | ![Halaman Bahasa](docs/Halaman%20Bahasa.png) |

## ✨ Penjelasan Fitur

### Fitur Wajib (Core Features)
1. **Daftar/Data (BREAD):**
    - *Browse/Read:* Menelusuri katalog parfum (Home) dan melihat detail produk.
    - *Edit/Add/Delete:* Menambah/mengubah/menghapus parfum di keranjang, menambah produk toko (penjual), dan menulis ulasan.
2. **Koneksi API Pihak Ketiga:** Integrasi dengan API eksternal (BinderByte & Cloudinary).
3. **Database Lokal (Room):** Penyimpanan riwayat pencarian (*Search History*) dan keranjang belanja (*Cart*) agar aplikasi tetap cepat.
4. **Clean Architecture:** Pembagian struktur kode menjadi `data`, `domain`, dan `ui` untuk skalabilitas.
5. **MVVM (Model-View-ViewModel):** Pemisahan logika bisnis (ViewModel) dan tampilan antarmuka (Jetpack Compose).

### Fitur Tambahan (Nilai Plus)
1. **Real-time Data Update:** Menggunakan Firebase Firestore dengan *Snapshot Listener* sehingga perubahan stok atau status pesanan langsung berubah di layar tanpa perlu *refresh* halaman.
2. **Local Database Caching Strategy:** Menggunakan *Room Database* untuk menyimpan *cache* pencarian dan data *Cart* sementara untuk efisiensi jaringan.
3. **Manual Dependency Injection:** Implementasi DI manual yang bersih menggunakan `ViewModelFactory` dan `AppContainer` tanpa menggunakan *library* berat seperti Hilt/Dagger.
4. **Third-party File Upload API:** Menggunakan **Cloudinary API** untuk mengunggah dan menyimpan foto bukti transfer dan gambar produk (bukan menyimpan *Base64* string yang memberatkan database).

## 🛠 Informasi API yang Digunakan
1. **Firebase Authentication & Firestore:** Digunakan untuk Autentikasi user (Email/Password) dan sistem *Backend-as-a-Service* (BaaS) *real-time* NoSQL.
2. **BinderByte API:** Digunakan untuk melacak nomor resi logistik secara *real-time* via HTTP request (`Retrofit`).
3. **Cloudinary API:** Digunakan sebagai *image hosting* pihak ketiga. Aplikasi mengirim gambar via *Multipart Request* secara langsung (unsigned) untuk mendapatkan URL gambar (*secure_url*).

## 🏗 Struktur Arsitektur Aplikasi
Aplikasi ini dibangun menggunakan pola **Clean Architecture** yang dikombinasikan dengan pola presentasi **MVVM (Model-View-ViewModel)**. Tampilan antarmuka (UI) dikembangkan sepenuhnya menggunakan **Jetpack Compose** (Declarative UI).

- `data/`: Berisi implementasi repositori, sumber data lokal (Room DAO), dan sumber data jarak jauh (Retrofit Service, Cloudinary Uploader, Firebase).
- `domain/`: Berisi *Use Case*, *model data class* utama, dan antarmuka *Repository*.
- `ui/`: Berisi halaman *Jetpack Compose* (`Screen`), navigasi, dan *ViewModel* yang mengatur *StateFlow*.
- `di/`: Berisi *AppContainer* untuk *Dependency Injection* manual.

## 🚀 Cara Instalasi
1. *Clone* repository ini ke komputer lokal Anda:
   ```bash
   git clone https://github.com/MuhammadNafisPutra/SCENT.git
   ```
2. Buka proyek tersebut menggunakan **Android Studio** (disarankan versi terbaru yang mendukung Jetpack Compose dan Gradle 8+).
3. Tunggu hingga proses *Gradle Sync* selesai.
4. Pastikan Anda memiliki file `google-services.json` dari Firebase Console dan menaruhnya di dalam *directory* `app/`. *(Catatan: File ini sengaja diabaikan di Git untuk keamanan).*

## ▶️ Cara Menjalankan Aplikasi
1. Sambungkan perangkat Android fisik via kabel USB (aktifkan *USB Debugging*) atau jalankan *Android Virtual Device (Emulator)* di Android Studio.
2. Klik tombol **"Run 'app'"** (ikon segitiga hijau) di toolbar atas Android Studio, atau gunakan *shortcut* `Shift + F10`.
3. Aplikasi akan di-*compile* dan langsung terbuka di perangkat/emulator Anda.