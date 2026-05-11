package com.example.core.constans.enums

sealed class InputTransactionEnum{

    //pengeluaran
    enum class KategoriPengeluaran(val label: String) {
        MAKANAN_MINUMAN("Makanan & Minuman"),
        BELANJA_DAPUR("Belanja Dapur"),
        TRANSPORTASI("Transportasi"),
        TAGIHAN_UTILITAS("Tagihan & Utilitas"),
        KESEHATAN("Kesehatan"),
        HIBURAN("Hiburan"),
        FASHION_PERAWATAN("Pakaian & Perawatan"),
        PENDIDIKAN("Pendidikan"),
        LAIN_LAIN("Lain-lain")
    }

    // Pemasukkan
    enum class KategoriPemasukan(val label: String) {
        GAJI("Gaji"),
        BONUS("Bonus / THR"),
        HASIL_USAHA("Hasil Usaha"),
        INVESTASI("Investasi"),
        PEMBERIAN("Pemberian"),
        LAIN_LAIN("Lain-lain")
    }
}