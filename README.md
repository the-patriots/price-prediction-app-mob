# Price Predictions

Aplikasi Android untuk mencatat cash flow sekaligus mengecek apakah harga barang berada **di bawah rata-rata**, **normal**, atau **di atas rata-rata** berdasarkan prediksi AI.

## Ringkasan

Project ini menggunakan arsitektur **multi-module** dengan pemisahan layer agar codebase lebih rapi, scalable, dan aman untuk dikembangkan.

Fitur utama:
- Login dan Sign Up dengan Supabase Auth
- Input transaksi pemasukan dan pengeluaran
- AI price check melalui Supabase Edge Function `predict_price`
- Simpan transaksi ke tabel `cash_flows`
- Dashboard home dan analytic berdasarkan bulan/tahun

## AI Prediction

Edge function yang dipakai:
- Function: `predict_price`
- Metode: `POST`
- Input: `category`, `price`, `productName`
- Output:
  - `prediction`: status harga
  - `marketPrice`: harga pasar referensi

Kemungkinan hasil prediction:
- `Normal`
- `Di bawah rata-rata`
- `Di atas rata-rata`

Referensi endpoint dan payload ada di file `edge_function.md`.

## Arsitektur Multi-Module

Module yang terdaftar di `settings.gradle.kts`:
- `:app` -> entry point aplikasi, DI, navigation host
- `:app:core` -> shared UI component, theme, enum, validator, reusable animation
- `:app:domain` -> entities, repository contract, use case
- `:app:data` -> datasource, repository implementation, network/local mapping
- `:app:presentations` -> screen UI Compose, state, ViewModel

Alur data utama:
1. UI (`presentations`) memanggil ViewModel
2. ViewModel memanggil UseCase (`domain`)
3. UseCase memanggil Repository (`domain` contract)
4. Repository Impl (`data`) mengambil data dari datasource (Supabase/API)
5. Shared model/component dipakai dari `core`

## Struktur Project

Struktur utama project:

```text
price_predictions/
|- app/
|  |- src/main/java/com/example/price_predictions/  # MainActivity, AppNav, DI
|  |- core/                                         # Shared UI, enums, validators
|  |- domain/                                       # UseCase, Repository contract, entity
|  |- data/                                         # Datasource, repository impl, model
|  |- presentations/                                # Screens, state, viewmodel
|- gradle/
|- edge_function.md
|- settings.gradle.kts
|- build.gradle.kts
```

## Tech Stack

- Kotlin
- Jetpack Compose (Material 3)
- Koin (Dependency Injection)
- Supabase (`gotrue-kt`, `postgrest-kt`, `functions-kt`)
- Ktor client
- Room (lokal data support)
- Navigation 3
- Coil

## Setup Environment

Project membaca credential Supabase dari `local.properties` melalui `BuildConfig`:
- `SUPABASE_URL`
- `SUPABASE_ANON_KEY`

Contoh isi `local.properties`:

```properties
SUPABASE_URL="https://your-project.supabase.co"
SUPABASE_ANON_KEY="your-anon-key"
```

## Menjalankan Project

Gunakan perintah berikut dari root project:

```powershell
.\gradlew.bat :app:assembleDebug
.\gradlew.bat :app:installDebug
```

Atau jalankan langsung dari Android Studio pada module `app`.

## Kontributor

Kontributor project ini:
- **Yasir Khoirul Huda** (`yasirkhoirul`)
- **Alan Sugito** (`AlanSugito`)

---

Jika ingin, README ini bisa saya lanjutkan dengan tambahan:
- section API contract per endpoint
- screenshot per screen
- diagram arsitektur antar module
