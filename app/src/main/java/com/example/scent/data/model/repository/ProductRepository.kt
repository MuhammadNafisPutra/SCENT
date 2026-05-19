package com.example.scent.data.repository

import com.example.scent.data.model.AromaFilter
import com.example.scent.data.model.HeroBanner
import com.example.scent.data.model.Product
import com.example.scent.data.model.Review
import com.example.scent.data.model.SizeOption
import com.example.scent.data.model.UsageFilter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ProductRepository {

    val heroBanner = HeroBanner(
        tag           = "RILIS TERBATAS",
        title         = "NOIR\nABSOLU",
        description   = "Perpaduan etereal dari kayu oud asap, amber beludru, dan melati tengah malam.",
        gradientStart = 0xFF2A2A2A,
        gradientEnd   = 0xFF0A0A0A
    )

    private val _products = MutableStateFlow(
        listOf(
            Product(
                id           = 1,
                brand        = "ATELIER V",
                name         = "Santal Blanc",
                price        = "Rp195.000",
                volume       = "50ml",
                cardColor    = 0xFFBFA882,
                accentColor  = 0xFFD4A853,
                collection   = "KOLEKSI BLANC",
                fullBrand    = "Oleh Atelier V",
                description  = "Sebuah perjalanan sensoris ke jantung hutan cendana.",
                aromaProfile = listOf("WOODY", "ORIENTAL"),
                usage        = "MALAM",
                rating       = 4.7f,
                reviewCount  = 98
            ),
            Product(
                id           = 2,
                brand        = "L'ESSENCE",
                name         = "Oud Immemorial",
                price        = "Rp240.000",
                volume       = "100ml",
                cardColor    = 0xFF1A2535,
                accentColor  = 0xFFD4A853,
                collection   = "KOLEKSI NOIR",
                fullBrand    = "Oleh Maison de L'Essence",
                description  = "Sebuah penjelajahan mendalam atas elemen bumi yang paling murni.",
                aromaProfile = listOf("WOODY", "ORIENTAL"),
                usage        = "MALAM",
                rating       = 4.8f,
                reviewCount  = 142
            ),
            Product(
                id           = 3,
                brand        = "FLORA",
                name         = "Amber Rose",
                price        = "Rp165.000",
                volume       = "50ml",
                cardColor    = 0xFF6B4C12,
                accentColor  = 0xFFD4A853,
                collection   = "KOLEKSI FLORAL",
                fullBrand    = "Oleh Flora Studio",
                description  = "Bunga mawar terbaik dari lembah Anatolia bertemu amber hangat.",
                aromaProfile = listOf("FLORAL", "ORIENTAL"),
                usage        = "SIANG",
                rating       = 4.5f,
                reviewCount  = 76
            ),
            Product(
                id           = 4,
                brand        = "ELEMENT",
                name         = "Citrus Sea",
                price        = "Rp130.000",
                volume       = "75ml",
                cardColor    = 0xFF2A2E35,
                accentColor  = 0xFF8BA0B0,
                collection   = "KOLEKSI AQUA",
                fullBrand    = "Oleh Element Co.",
                description  = "Semburan angin laut segar bertemu bergamot Italia yang tajam.",
                aromaProfile = listOf("CITRUS"),
                usage        = "SIANG",
                rating       = 4.3f,
                reviewCount  = 54
            ),
            Product(
                id           = 5,
                brand        = "MAISON R",
                name         = "Rose Noire",
                price        = "Rp280.000",
                volume       = "100ml",
                cardColor    = 0xFF3A1520,
                accentColor  = 0xFFD4A853,
                collection   = "KOLEKSI NOIR",
                fullBrand    = "Oleh Maison Rouge",
                description  = "Rose Noire adalah antitesis dari bunga mawar konvensional.",
                aromaProfile = listOf("FLORAL", "WOODY"),
                usage        = "MALAM",
                rating       = 4.9f,
                reviewCount  = 201
            ),
            Product(
                id           = 6,
                brand        = "VETIVER",
                name         = "Forest Mist",
                price        = "Rp175.000",
                volume       = "50ml",
                cardColor    = 0xFF1A2A1A,
                accentColor  = 0xFF7A9A6A,
                collection   = "KOLEKSI VERDE",
                fullBrand    = "Oleh Vetiver Lab",
                description  = "Kabut pagi di hutan tropis yang lebat.",
                aromaProfile = listOf("WOODY", "GOURMAND"),
                usage        = "SIANG",
                rating       = 4.6f,
                reviewCount  = 83
            )
        )
    )

    val products: Flow<List<Product>> = _products.asStateFlow()

    fun getProductById(id: Int): Product? =
        _products.value.find { it.id == id }

    fun toggleFavorite(productId: Int) {
        _products.update { list ->
            list.map { product ->
                if (product.id == productId)
                    product.copy(isFavorite = !product.isFavorite)
                else product
            }
        }
    }

    fun getSizeOptions(productId: Int): List<SizeOption> = listOf(
        SizeOption(id = "full",   label = "UKURAN PENUH", size = "100ML", price = "/ Rp240.000"),
        SizeOption(id = "decant", label = "DECANT",       size = "10ML",  price = "/ Rp35.000")
    )

    fun getReviews(productId: Int): List<Review> = listOf(
        Review(
            id          = 1,
            initials    = "E.H.",
            name        = "Elias H.",
            badge       = "PEMBELI TERVERIFIKASI",
            date        = "14 OKT. 2023",
            text        = "Vetiver paling realistis yang pernah saya temui. Ketahanannya luar biasa.",
            avatarColor = 0xFF1A2535,
            rating      = 5f,
            imageCount  = 2
        ),
        Review(
            id          = 2,
            initials    = "A.P.",
            name        = "Andi P.",
            badge       = "PEMBELI TERVERIFIKASI",
            date        = "2 NOV. 2023",
            text        = "Luar biasa. Aroma yang sangat unik dan tahan lama.",
            avatarColor = 0xFF2A1535,
            rating      = 4f,
            imageCount  = 0
        )
    )

    val aromaFilters: List<AromaFilter> = listOf(
        AromaFilter(id = "FLORAL",   label = "FLORAL"),
        AromaFilter(id = "WOODY",    label = "WOODY"),
        AromaFilter(id = "GOURMAND", label = "GOURMAND"),
        AromaFilter(id = "CITRUS",   label = "CITRUS"),
        AromaFilter(id = "ORIENTAL", label = "ORIENTAL")
    )

    val usageFilters: List<UsageFilter> = listOf(
        UsageFilter(id = "SIANG", label = "SIANG"),
        UsageFilter(id = "MALAM", label = "MALAM")
    )

    fun searchProducts(
        query        : String,
        aromaFilters : Set<String>,
        usageFilter  : String?
    ): List<Product> = _products.value.filter { p ->
        val matchQuery = query.isBlank() ||
                p.name.contains(query, ignoreCase = true) ||
                p.brand.contains(query, ignoreCase = true)
        val matchAroma = aromaFilters.isEmpty() ||
                p.aromaProfile.any { it in aromaFilters }
        val matchUsage = usageFilter == null ||
                p.usage.equals(usageFilter, ignoreCase = true)
        matchQuery && matchAroma && matchUsage
    }
}
