package com.example.core.constans.enums

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*

sealed class InputTransactionEnum{

    //pengeluaran
    enum class KategoriPengeluaran(val item: DropDownItem<String>) {
        MAKANAN_MINUMAN(DropDownItem("Makanan & Minuman", "MAKANAN_MINUMAN", Icons.Default.ShoppingCart)),
        BELANJA_DAPUR(DropDownItem("Belanja Dapur", "BELANJA_DAPUR", Icons.Default.Home)),
        TRANSPORTASI(DropDownItem("Transportasi", "TRANSPORTASI", Icons.Default.Place)),
        TAGIHAN_UTILITAS(DropDownItem("Tagihan & Utilitas", "TAGIHAN_UTILITAS", Icons.Default.List)),
        KESEHATAN(DropDownItem("Kesehatan", "KESEHATAN", Icons.Default.Favorite)),
        HIBURAN(DropDownItem("Hiburan", "HIBURAN", Icons.Default.Star)),
        FASHION_PERAWATAN(DropDownItem("Pakaian & Perawatan", "FASHION_PERAWATAN", Icons.Default.Face)),
        PENDIDIKAN(DropDownItem("Pendidikan", "PENDIDIKAN", Icons.Default.Create)),
        LAIN_LAIN(DropDownItem("Lain-lain", "LAIN_LAIN", Icons.Default.Info))
    }

    // Pemasukkan
    enum class KategoriPemasukan(val item: DropDownItem<String>) {
        GAJI(DropDownItem("Gaji", "GAJI", Icons.Default.AccountBox)),
        BONUS(DropDownItem("Bonus / THR", "BONUS", Icons.Default.Star)),
        HASIL_USAHA(DropDownItem("Hasil Usaha", "HASIL_USAHA", Icons.Default.Build)),
        INVESTASI(DropDownItem("Investasi", "INVESTASI", Icons.Default.ThumbUp)),
        PEMBERIAN(DropDownItem("Pemberian", "PEMBERIAN", Icons.Default.Favorite)),
        LAIN_LAIN(DropDownItem("Lain-lain", "LAIN_LAIN", Icons.Default.Info))
    }

    //type
    enum class TypeCashFlow(val value:String){
        PENGELUARAN("Pengeluaran"),
        PEMASUKKAN("Pemasukan")
    }
}