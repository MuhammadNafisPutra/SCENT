package com.contoh.scentapp.`data`.local.dao

import androidx.room.EntityDeleteOrUpdateAdapter
import androidx.room.EntityInsertAdapter
import androidx.room.RoomDatabase
import androidx.room.coroutines.createFlow
import androidx.room.util.getColumnIndexOrThrow
import androidx.room.util.performSuspending
import androidx.sqlite.SQLiteStatement
import com.contoh.scentapp.`data`.local.entity.CartItemEntity
import javax.`annotation`.processing.Generated
import kotlin.Boolean
import kotlin.Int
import kotlin.Long
import kotlin.String
import kotlin.Suppress
import kotlin.Unit
import kotlin.collections.List
import kotlin.collections.MutableList
import kotlin.collections.mutableListOf
import kotlin.reflect.KClass
import kotlinx.coroutines.flow.Flow

@Generated(value = ["androidx.room.RoomProcessor"])
@Suppress(names = ["UNCHECKED_CAST", "DEPRECATION", "REDUNDANT_PROJECTION", "REMOVAL"])
public class CartDao_Impl(
  __db: RoomDatabase,
) : CartDao {
  private val __db: RoomDatabase

  private val __insertAdapterOfCartItemEntity: EntityInsertAdapter<CartItemEntity>

  private val __updateAdapterOfCartItemEntity: EntityDeleteOrUpdateAdapter<CartItemEntity>
  init {
    this.__db = __db
    this.__insertAdapterOfCartItemEntity = object : EntityInsertAdapter<CartItemEntity>() {
      protected override fun createQuery(): String = "INSERT OR REPLACE INTO `cart_items` (`id`,`productId`,`firestoreId`,`sellerId`,`name`,`brand`,`aromaProfile`,`imageUrl`,`volume`,`isDecant`,`pricePerItem`,`quantity`,`cardColor`,`accentColor`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?,?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: CartItemEntity) {
        statement.bindLong(1, entity.id.toLong())
        statement.bindLong(2, entity.productId.toLong())
        statement.bindText(3, entity.firestoreId)
        statement.bindText(4, entity.sellerId)
        statement.bindText(5, entity.name)
        statement.bindText(6, entity.brand)
        statement.bindText(7, entity.aromaProfile)
        statement.bindText(8, entity.imageUrl)
        statement.bindText(9, entity.volume)
        val _tmp: Int = if (entity.isDecant) 1 else 0
        statement.bindLong(10, _tmp.toLong())
        statement.bindLong(11, entity.pricePerItem.toLong())
        statement.bindLong(12, entity.quantity.toLong())
        statement.bindLong(13, entity.cardColor)
        statement.bindLong(14, entity.accentColor)
      }
    }
    this.__updateAdapterOfCartItemEntity = object : EntityDeleteOrUpdateAdapter<CartItemEntity>() {
      protected override fun createQuery(): String = "UPDATE OR ABORT `cart_items` SET `id` = ?,`productId` = ?,`firestoreId` = ?,`sellerId` = ?,`name` = ?,`brand` = ?,`aromaProfile` = ?,`imageUrl` = ?,`volume` = ?,`isDecant` = ?,`pricePerItem` = ?,`quantity` = ?,`cardColor` = ?,`accentColor` = ? WHERE `id` = ?"

      protected override fun bind(statement: SQLiteStatement, entity: CartItemEntity) {
        statement.bindLong(1, entity.id.toLong())
        statement.bindLong(2, entity.productId.toLong())
        statement.bindText(3, entity.firestoreId)
        statement.bindText(4, entity.sellerId)
        statement.bindText(5, entity.name)
        statement.bindText(6, entity.brand)
        statement.bindText(7, entity.aromaProfile)
        statement.bindText(8, entity.imageUrl)
        statement.bindText(9, entity.volume)
        val _tmp: Int = if (entity.isDecant) 1 else 0
        statement.bindLong(10, _tmp.toLong())
        statement.bindLong(11, entity.pricePerItem.toLong())
        statement.bindLong(12, entity.quantity.toLong())
        statement.bindLong(13, entity.cardColor)
        statement.bindLong(14, entity.accentColor)
        statement.bindLong(15, entity.id.toLong())
      }
    }
  }

  public override suspend fun insertCartItem(item: CartItemEntity): Unit = performSuspending(__db, false, true) { _connection ->
    __insertAdapterOfCartItemEntity.insert(_connection, item)
  }

  public override suspend fun updateCartItem(item: CartItemEntity): Unit = performSuspending(__db, false, true) { _connection ->
    __updateAdapterOfCartItemEntity.handle(_connection, item)
  }

  public override fun getAllCartItems(): Flow<List<CartItemEntity>> {
    val _sql: String = "SELECT * FROM cart_items"
    return createFlow(__db, false, arrayOf("cart_items")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfProductId: Int = getColumnIndexOrThrow(_stmt, "productId")
        val _columnIndexOfFirestoreId: Int = getColumnIndexOrThrow(_stmt, "firestoreId")
        val _columnIndexOfSellerId: Int = getColumnIndexOrThrow(_stmt, "sellerId")
        val _columnIndexOfName: Int = getColumnIndexOrThrow(_stmt, "name")
        val _columnIndexOfBrand: Int = getColumnIndexOrThrow(_stmt, "brand")
        val _columnIndexOfAromaProfile: Int = getColumnIndexOrThrow(_stmt, "aromaProfile")
        val _columnIndexOfImageUrl: Int = getColumnIndexOrThrow(_stmt, "imageUrl")
        val _columnIndexOfVolume: Int = getColumnIndexOrThrow(_stmt, "volume")
        val _columnIndexOfIsDecant: Int = getColumnIndexOrThrow(_stmt, "isDecant")
        val _columnIndexOfPricePerItem: Int = getColumnIndexOrThrow(_stmt, "pricePerItem")
        val _columnIndexOfQuantity: Int = getColumnIndexOrThrow(_stmt, "quantity")
        val _columnIndexOfCardColor: Int = getColumnIndexOrThrow(_stmt, "cardColor")
        val _columnIndexOfAccentColor: Int = getColumnIndexOrThrow(_stmt, "accentColor")
        val _result: MutableList<CartItemEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: CartItemEntity
          val _tmpId: Int
          _tmpId = _stmt.getLong(_columnIndexOfId).toInt()
          val _tmpProductId: Int
          _tmpProductId = _stmt.getLong(_columnIndexOfProductId).toInt()
          val _tmpFirestoreId: String
          _tmpFirestoreId = _stmt.getText(_columnIndexOfFirestoreId)
          val _tmpSellerId: String
          _tmpSellerId = _stmt.getText(_columnIndexOfSellerId)
          val _tmpName: String
          _tmpName = _stmt.getText(_columnIndexOfName)
          val _tmpBrand: String
          _tmpBrand = _stmt.getText(_columnIndexOfBrand)
          val _tmpAromaProfile: String
          _tmpAromaProfile = _stmt.getText(_columnIndexOfAromaProfile)
          val _tmpImageUrl: String
          _tmpImageUrl = _stmt.getText(_columnIndexOfImageUrl)
          val _tmpVolume: String
          _tmpVolume = _stmt.getText(_columnIndexOfVolume)
          val _tmpIsDecant: Boolean
          val _tmp: Int
          _tmp = _stmt.getLong(_columnIndexOfIsDecant).toInt()
          _tmpIsDecant = _tmp != 0
          val _tmpPricePerItem: Int
          _tmpPricePerItem = _stmt.getLong(_columnIndexOfPricePerItem).toInt()
          val _tmpQuantity: Int
          _tmpQuantity = _stmt.getLong(_columnIndexOfQuantity).toInt()
          val _tmpCardColor: Long
          _tmpCardColor = _stmt.getLong(_columnIndexOfCardColor)
          val _tmpAccentColor: Long
          _tmpAccentColor = _stmt.getLong(_columnIndexOfAccentColor)
          _item = CartItemEntity(_tmpId,_tmpProductId,_tmpFirestoreId,_tmpSellerId,_tmpName,_tmpBrand,_tmpAromaProfile,_tmpImageUrl,_tmpVolume,_tmpIsDecant,_tmpPricePerItem,_tmpQuantity,_tmpCardColor,_tmpAccentColor)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getCartItemByProductId(productId: Int): CartItemEntity? {
    val _sql: String = "SELECT * FROM cart_items WHERE productId = ? LIMIT 1"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, productId.toLong())
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfProductId: Int = getColumnIndexOrThrow(_stmt, "productId")
        val _columnIndexOfFirestoreId: Int = getColumnIndexOrThrow(_stmt, "firestoreId")
        val _columnIndexOfSellerId: Int = getColumnIndexOrThrow(_stmt, "sellerId")
        val _columnIndexOfName: Int = getColumnIndexOrThrow(_stmt, "name")
        val _columnIndexOfBrand: Int = getColumnIndexOrThrow(_stmt, "brand")
        val _columnIndexOfAromaProfile: Int = getColumnIndexOrThrow(_stmt, "aromaProfile")
        val _columnIndexOfImageUrl: Int = getColumnIndexOrThrow(_stmt, "imageUrl")
        val _columnIndexOfVolume: Int = getColumnIndexOrThrow(_stmt, "volume")
        val _columnIndexOfIsDecant: Int = getColumnIndexOrThrow(_stmt, "isDecant")
        val _columnIndexOfPricePerItem: Int = getColumnIndexOrThrow(_stmt, "pricePerItem")
        val _columnIndexOfQuantity: Int = getColumnIndexOrThrow(_stmt, "quantity")
        val _columnIndexOfCardColor: Int = getColumnIndexOrThrow(_stmt, "cardColor")
        val _columnIndexOfAccentColor: Int = getColumnIndexOrThrow(_stmt, "accentColor")
        val _result: CartItemEntity?
        if (_stmt.step()) {
          val _tmpId: Int
          _tmpId = _stmt.getLong(_columnIndexOfId).toInt()
          val _tmpProductId: Int
          _tmpProductId = _stmt.getLong(_columnIndexOfProductId).toInt()
          val _tmpFirestoreId: String
          _tmpFirestoreId = _stmt.getText(_columnIndexOfFirestoreId)
          val _tmpSellerId: String
          _tmpSellerId = _stmt.getText(_columnIndexOfSellerId)
          val _tmpName: String
          _tmpName = _stmt.getText(_columnIndexOfName)
          val _tmpBrand: String
          _tmpBrand = _stmt.getText(_columnIndexOfBrand)
          val _tmpAromaProfile: String
          _tmpAromaProfile = _stmt.getText(_columnIndexOfAromaProfile)
          val _tmpImageUrl: String
          _tmpImageUrl = _stmt.getText(_columnIndexOfImageUrl)
          val _tmpVolume: String
          _tmpVolume = _stmt.getText(_columnIndexOfVolume)
          val _tmpIsDecant: Boolean
          val _tmp: Int
          _tmp = _stmt.getLong(_columnIndexOfIsDecant).toInt()
          _tmpIsDecant = _tmp != 0
          val _tmpPricePerItem: Int
          _tmpPricePerItem = _stmt.getLong(_columnIndexOfPricePerItem).toInt()
          val _tmpQuantity: Int
          _tmpQuantity = _stmt.getLong(_columnIndexOfQuantity).toInt()
          val _tmpCardColor: Long
          _tmpCardColor = _stmt.getLong(_columnIndexOfCardColor)
          val _tmpAccentColor: Long
          _tmpAccentColor = _stmt.getLong(_columnIndexOfAccentColor)
          _result = CartItemEntity(_tmpId,_tmpProductId,_tmpFirestoreId,_tmpSellerId,_tmpName,_tmpBrand,_tmpAromaProfile,_tmpImageUrl,_tmpVolume,_tmpIsDecant,_tmpPricePerItem,_tmpQuantity,_tmpCardColor,_tmpAccentColor)
        } else {
          _result = null
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun deleteCartItemByProductId(productId: Int) {
    val _sql: String = "DELETE FROM cart_items WHERE productId = ?"
    return performSuspending(__db, false, true) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, productId.toLong())
        _stmt.step()
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun clearCart() {
    val _sql: String = "DELETE FROM cart_items"
    return performSuspending(__db, false, true) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        _stmt.step()
      } finally {
        _stmt.close()
      }
    }
  }

  public companion object {
    public fun getRequiredConverters(): List<KClass<*>> = emptyList()
  }
}
