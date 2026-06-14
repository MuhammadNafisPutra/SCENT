# ScentApp - E-Commerce Parfum (Android)

ScentApp adalah aplikasi *e-commerce* berbasis Android yang didesain khusus untuk komunitas pecinta parfum. Aplikasi ini menyediakan fitur katalog, pencarian spesifik berdasarkan *notes* parfum, keranjang belanja, manajemen pesanan (pembeli & penjual), dan pelacakan pengiriman secara langsung.

## 📸 Screenshot Aplikasi

*(Letakkan screenshot aplikasi Anda di dalam folder `docs/` lalu ubah link di bawah ini)*

| Home | Detail Produk | Keranjang | Profil |
|:---:|:---:|:---:|:---:|
| ![Home](docs/home.jpg) | ![Detail](docs/detail.jpg) | ![Cart](docs/cart.jpg) | ![Profile](docs/profile.jpg) |

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
