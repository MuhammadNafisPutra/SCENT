package com.example.scent.ui.favorit

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.scent.data.model.Product
import com.example.scent.ui.theme.SCENTTheme
import com.example.scent.ui.theme.ScentBlack
import com.example.scent.ui.theme.ScentGold
import com.example.scent.ui.theme.ScentTextMuted

private val NavBg = Color(0xFF111111)

@Composable
fun FavoriteScreen(
    viewModel: FavoriteViewModel,
    onProductClick: (Int) -> Unit = {},
    onNavigateHome: () -> Unit = {},
    onNavigateCart: () -> Unit = {},
    onNavigateProfile: () -> Unit = {},
    onBack: () -> Unit = {}
) {
    val favorites by viewModel.favoriteProducts.collectAsStateWithLifecycle()

    FavoriteScreenContent(
        favorites = favorites,
        onProductClick = onProductClick,
        onRemove = { viewModel.toggleFavorite(it) },
        onBack = onBack,
        onNavigateHome = onNavigateHome,
        onNavigateCart = onNavigateCart,
        onNavigateProfile = onNavigateProfile
    )
}

@Composable
private fun FavoriteScreenContent(
    favorites: List<Product>,
    onProductClick: (Int) -> Unit,
    onRemove: (Int) -> Unit,
    onBack: () -> Unit,
    onNavigateHome: () -> Unit,
    onNavigateCart: () -> Unit,
    onNavigateProfile: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(ScentBlack)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {

            FavoriteTopBar(onBack = onBack)

            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                contentPadding = PaddingValues(bottom = 16.dp)
            ) {

                item {
                    FavoriteHeader(count = favorites.size)
                }

                if (favorites.isEmpty()) {
                    item {
                        EmptyFavoriteState(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(400.dp)
                        )
                    }
                } else {
                    items(
                        items = favorites,
                        key = { it.id }
                    ) { product ->

                        FavoriteProductCard(
                            product = product,
                            onCardClick = { onProductClick(product.id) },
                            onRemove = { onRemove(product.id) }
                        )
                    }
                }
            }

            ScentBottomNav(
                currentRoute = "favorit",
                onNavigateHome = onNavigateHome,
                onNavigateFavorit = {},
                onNavigateCart = onNavigateCart,
                onNavigateProfile = onNavigateProfile
            )
        }
    }
}

@Composable
private fun FavoriteTopBar(onBack: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .padding(horizontal = 8.dp, vertical = 12.dp)
    ) {
        IconButton(
            onClick = onBack,
            modifier = Modifier.align(Alignment.CenterStart)
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Kembali",
                tint = Color.White,
                modifier = Modifier.size(20.dp)
            )
        }

        Text(
            text = "S C E N T",
            color = Color.White,
            fontSize = 16.sp,
            fontWeight = FontWeight.Light,
            letterSpacing = 6.sp,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
private fun FavoriteHeader(count: Int) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 24.dp)
    ) {

        Text(
            text = "Kurasi Anda.",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            fontStyle = FontStyle.Italic,
            color = Color.White,
            lineHeight = 36.sp
        )

        Spacer(Modifier.height(8.dp))

        Text(
            text = "Koleksi wewangian terpilih yang mencerminkan\nidentitas dan karakter personal Anda.",
            fontSize = 13.sp,
            color = ScentTextMuted,
            lineHeight = 18.sp
        )
    }
}

@Composable
private fun EmptyFavoriteState(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {

        Column(horizontalAlignment = Alignment.CenterHorizontally) {

            Icon(
                imageVector = Icons.Outlined.FavoriteBorder,
                contentDescription = null,
                tint = ScentGold,
                modifier = Modifier.size(56.dp)
            )

            Spacer(Modifier.height(20.dp))

            Text(
                text = "Koleksi Favorit Kosong",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.White
            )

            Spacer(Modifier.height(8.dp))

            Text(
                text = "Tekan ikon pada parfum\nuntuk menyimpannya di sini",
                fontSize = 13.sp,
                color = ScentTextMuted,
                textAlign = TextAlign.Center,
                lineHeight = 20.sp
            )
        }
    }
}

@Composable
private fun FavoriteProductCard(
    product: Product,
    onCardClick: () -> Unit,
    onRemove: () -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onCardClick() }
            .padding(horizontal = 20.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Box(
            modifier = Modifier
                .size(72.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Color(0xFF2A2A2A)),
            contentAlignment = Alignment.Center
        ) {

            Icon(
                imageVector = Icons.Outlined.FavoriteBorder,
                contentDescription = null,
                tint = Color(0xFF444444),
                modifier = Modifier.size(28.dp)
            )
        }

        Spacer(Modifier.width(16.dp))

        Column(modifier = Modifier.weight(1f)) {

            Text(
                text = product.collection.ifEmpty { product.brand },
                fontSize = 10.sp,
                color = ScentTextMuted,
                fontWeight = FontWeight.Medium,
                letterSpacing = 1.5.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(Modifier.height(4.dp))

            Text(
                text = product.name.uppercase(),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                letterSpacing = 1.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(Modifier.height(6.dp))

            Text(
                text = product.price,
                fontSize = 13.sp,
                color = ScentTextMuted
            )
        }

        IconButton(
            onClick = onRemove,
            modifier = Modifier.size(32.dp)
        ) {

            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Hapus dari favorit",
                tint = Color(0xFF888888),
                modifier = Modifier.size(18.dp)
            )
        }
    }
}

@Composable
fun ScentBottomNav(
    currentRoute: String,
    onNavigateHome: () -> Unit,
    onNavigateFavorit: () -> Unit,
    onNavigateCart: () -> Unit,
    onNavigateProfile: () -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(NavBg)
            .padding(vertical = 10.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {

        BottomNavItem(
            icon = Icons.Default.Home,
            label = "BERANDA",
            selected = currentRoute == "home",
            onClick = onNavigateHome
        )

        BottomNavItem(
            icon = Icons.Default.Favorite,
            label = "FAVORIT",
            selected = currentRoute == "favorit",
            onClick = onNavigateFavorit
        )

        BottomNavItem(
            icon = Icons.Default.ShoppingCart,
            label = "KERANJANG",
            selected = currentRoute == "cart",
            onClick = onNavigateCart
        )

        BottomNavItem(
            icon = Icons.Default.Person,
            label = "PROFIL",
            selected = currentRoute == "profile",
            onClick = onNavigateProfile
        )
    }
}

@Composable
private fun BottomNavItem(
    icon: ImageVector,
    label: String,
    selected: Boolean,
    onClick: () -> Unit
) {

    val tintColor = if (selected) Color.White else Color(0xFF666666)

    Column(
        modifier = Modifier
            .clickable { onClick() }
            .padding(horizontal = 12.dp, vertical = 4.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = tintColor,
            modifier = Modifier.size(22.dp)
        )

        Spacer(Modifier.height(4.dp))

        Text(
            text = label,
            fontSize = 9.sp,
            color = tintColor,
            fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal,
            letterSpacing = 1.sp
        )
    }
}

@Preview(
    showBackground = true,
    backgroundColor = 0xFF0A0A0A,
    showSystemUi = true,
    name = "Favorite - Empty"
)
@Composable
private fun FavoriteEmptyPreview() {

    SCENTTheme {

        FavoriteScreenContent(
            favorites = emptyList(),
            onProductClick = {},
            onRemove = {},
            onBack = {},
            onNavigateHome = {},
            onNavigateCart = {},
            onNavigateProfile = {}
        )
    }
}

@Preview(
    showBackground = true,
    backgroundColor = 0xFF0A0A0A,
    showSystemUi = true,
    name = "Favorite - With Items"
)
@Composable
private fun FavoriteWithItemsPreview() {

    val dummyFavorites = listOf(

        Product(
            id = 1,
            brand = "ATELIER V",
            name = "Noir Obscur",
            collection = "BOUTIQUE SERIES",
            price = "Rp 1.850.000",
            volume = "50ml",
            cardColor = 0xFF1A1A1A,
            isFavorite = true
        ),

        Product(
            id = 2,
            brand = "MAISON ALCHEMY",
            name = "Santal Eclipse",
            collection = "MAISON ALCHEMY",
            price = "Rp 2.100.000",
            volume = "100ml",
            cardColor = 0xFF1A1A1A,
            isFavorite = true
        ),

        Product(
            id = 3,
            brand = "L'ESSENCE",
            name = "Velvet Oud",
            collection = "L'ESSENCE",
            price = "Rp 3.450.000",
            volume = "100ml",
            cardColor = 0xFF1A1A1A,
            isFavorite = true
        )
    )

    SCENTTheme {

        FavoriteScreenContent(
            favorites = dummyFavorites,
            onProductClick = {},
            onRemove = {},
            onBack = {},
            onNavigateHome = {},
            onNavigateCart = {},
            onNavigateProfile = {}
        )
    }
}