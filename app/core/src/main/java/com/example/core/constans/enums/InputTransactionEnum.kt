package com.example.core.constans.enums

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.Color

sealed class InputTransactionEnum{

    //pengeluaran
    enum class KategoriPengeluaran(val item: DropDownItem<String>) {
        MAKANAN_MINUMAN(DropDownItem("Makanan & Minuman", "MAKANAN_MINUMAN", Icons.Default.ShoppingCart, Color(0xFFE57373))),
        BELANJA_DAPUR(DropDownItem("Belanja Dapur", "BELANJA_DAPUR", Icons.Default.Home, Color(0xFF81C784))),
        TRANSPORTASI(DropDownItem("Transportasi", "TRANSPORTASI", Icons.Default.Place, Color(0xFF64B5F6))),
        TAGIHAN_UTILITAS(DropDownItem("Tagihan & Utilitas", "TAGIHAN_UTILITAS", Icons.Default.List, Color(0xFFFFB74D))),
        KESEHATAN(DropDownItem("Kesehatan", "KESEHATAN", Icons.Default.Favorite, Color(0xFFF06292))),
        HIBURAN(DropDownItem("Hiburan", "HIBURAN", Icons.Default.Star, Color(0xFFBA68C8))),
        FASHION_PERAWATAN(DropDownItem("Pakaian & Perawatan", "FASHION_PERAWATAN", Icons.Default.Face, Color(0xFF4DB6AC))),
        PENDIDIKAN(DropDownItem("Pendidikan", "PENDIDIKAN", Icons.Default.Create, Color(0xFFFFD54F))),
        LAIN_LAIN(DropDownItem("Lain-lain", "LAIN_LAIN", Icons.Default.Info, Color(0xFF90A4AE)))
    }

    // Pemasukkan
    enum class KategoriPemasukan(val item: DropDownItem<String>) {
        GAJI(DropDownItem("Gaji", "GAJI", Icons.Default.AccountBox, Color(0xFF4CAF50))),
        BONUS(DropDownItem("Bonus / THR", "BONUS", Icons.Default.Star, Color(0xFFFFC107))),
        HASIL_USAHA(DropDownItem("Hasil Usaha", "HASIL_USAHA", Icons.Default.Build, Color(0xFF03A9F4))),
        INVESTASI(DropDownItem("Investasi", "INVESTASI", Icons.Default.ThumbUp, Color(0xFF9C27B0))),
        PEMBERIAN(DropDownItem("Pemberian", "PEMBERIAN", Icons.Default.Favorite, Color(0xFFE91E63))),
        LAIN_LAIN(DropDownItem("Lain-lain", "LAIN_LAIN", Icons.Default.Info, Color(0xFF607D8B)))
    }

    //type
    enum class TypeCashFlow(val value:String){
        PENGELUARAN("Pengeluaran"),
        PEMASUKKAN("Pemasukan")
    }
}