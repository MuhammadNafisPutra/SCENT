package com.contoh.scentapp.`data`.local

import androidx.room.InvalidationTracker
import androidx.room.RoomOpenDelegate
import androidx.room.migration.AutoMigrationSpec
import androidx.room.migration.Migration
import androidx.room.util.TableInfo
import androidx.room.util.TableInfo.Companion.read
import androidx.room.util.dropFtsSyncTriggers
import androidx.sqlite.SQLiteConnection
import androidx.sqlite.execSQL
import com.contoh.scentapp.`data`.local.dao.CartDao
import com.contoh.scentapp.`data`.local.dao.CartDao_Impl
import com.contoh.scentapp.`data`.local.dao.SearchHistoryDao
import com.contoh.scentapp.`data`.local.dao.SearchHistoryDao_Impl
import javax.`annotation`.processing.Generated
import kotlin.Lazy
import kotlin.String
import kotlin.Suppress
import kotlin.collections.List
import kotlin.collections.Map
import kotlin.collections.MutableList
import kotlin.collections.MutableMap
import kotlin.collections.MutableSet
import kotlin.collections.Set
import kotlin.collections.mutableListOf
import kotlin.collections.mutableMapOf
import kotlin.collections.mutableSetOf
import kotlin.reflect.KClass

@Generated(value = ["androidx.room.RoomProcessor"])
@Suppress(names = ["UNCHECKED_CAST", "DEPRECATION", "REDUNDANT_PROJECTION", "REMOVAL"])
public class AppDatabase_Impl : AppDatabase() {
  private val _cartDao: Lazy<CartDao> = lazy {
    CartDao_Impl(this)
  }

  private val _searchHistoryDao: Lazy<SearchHistoryDao> = lazy {
    SearchHistoryDao_Impl(this)
  }

  protected override fun createOpenDelegate(): RoomOpenDelegate {
    val _openDelegate: RoomOpenDelegate = object : RoomOpenDelegate(1, "2c4b331c4d10efbc947d1a6fcec50f97", "d17b689e2fdf8f695fc975cc1d571cde") {
      public override fun createAllTables(connection: SQLiteConnection) {
        connection.execSQL("CREATE TABLE IF NOT EXISTS `cart_items` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `productId` INTEGER NOT NULL, `firestoreId` TEXT NOT NULL, `sellerId` TEXT NOT NULL, `name` TEXT NOT NULL, `brand` TEXT NOT NULL, `aromaProfile` TEXT NOT NULL, `imageUrl` TEXT NOT NULL, `volume` TEXT NOT NULL, `isDecant` INTEGER NOT NULL, `pricePerItem` INTEGER NOT NULL, `quantity` INTEGER NOT NULL, `cardColor` INTEGER NOT NULL, `accentColor` INTEGER NOT NULL)")
        connection.execSQL("CREATE TABLE IF NOT EXISTS `search_history` (`query` TEXT NOT NULL, `timestamp` INTEGER NOT NULL, PRIMARY KEY(`query`))")
        connection.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)")
        connection.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '2c4b331c4d10efbc947d1a6fcec50f97')")
      }

      public override fun dropAllTables(connection: SQLiteConnection) {
        connection.execSQL("DROP TABLE IF EXISTS `cart_items`")
        connection.execSQL("DROP TABLE IF EXISTS `search_history`")
      }

      public override fun onCreate(connection: SQLiteConnection) {
      }

      public override fun onOpen(connection: SQLiteConnection) {
        internalInitInvalidationTracker(connection)
      }

      public override fun onPreMigrate(connection: SQLiteConnection) {
        dropFtsSyncTriggers(connection)
      }

      public override fun onPostMigrate(connection: SQLiteConnection) {
      }

      public override fun onValidateSchema(connection: SQLiteConnection): RoomOpenDelegate.ValidationResult {
        val _columnsCartItems: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsCartItems.put("id", TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsCartItems.put("productId", TableInfo.Column("productId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsCartItems.put("firestoreId", TableInfo.Column("firestoreId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsCartItems.put("sellerId", TableInfo.Column("sellerId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsCartItems.put("name", TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsCartItems.put("brand", TableInfo.Column("brand", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsCartItems.put("aromaProfile", TableInfo.Column("aromaProfile", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsCartItems.put("imageUrl", TableInfo.Column("imageUrl", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsCartItems.put("volume", TableInfo.Column("volume", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsCartItems.put("isDecant", TableInfo.Column("isDecant", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsCartItems.put("pricePerItem", TableInfo.Column("pricePerItem", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsCartItems.put("quantity", TableInfo.Column("quantity", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsCartItems.put("cardColor", TableInfo.Column("cardColor", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsCartItems.put("accentColor", TableInfo.Column("accentColor", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysCartItems: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        val _indicesCartItems: MutableSet<TableInfo.Index> = mutableSetOf()
        val _infoCartItems: TableInfo = TableInfo("cart_items", _columnsCartItems, _foreignKeysCartItems, _indicesCartItems)
        val _existingCartItems: TableInfo = read(connection, "cart_items")
        if (!_infoCartItems.equals(_existingCartItems)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |cart_items(com.contoh.scentapp.data.local.entity.CartItemEntity).
              | Expected:
              |""".trimMargin() + _infoCartItems + """
              |
              | Found:
              |""".trimMargin() + _existingCartItems)
        }
        val _columnsSearchHistory: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsSearchHistory.put("query", TableInfo.Column("query", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsSearchHistory.put("timestamp", TableInfo.Column("timestamp", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysSearchHistory: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        val _indicesSearchHistory: MutableSet<TableInfo.Index> = mutableSetOf()
        val _infoSearchHistory: TableInfo = TableInfo("search_history", _columnsSearchHistory, _foreignKeysSearchHistory, _indicesSearchHistory)
        val _existingSearchHistory: TableInfo = read(connection, "search_history")
        if (!_infoSearchHistory.equals(_existingSearchHistory)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |search_history(com.contoh.scentapp.data.local.entity.SearchHistoryEntity).
              | Expected:
              |""".trimMargin() + _infoSearchHistory + """
              |
              | Found:
              |""".trimMargin() + _existingSearchHistory)
        }
        return RoomOpenDelegate.ValidationResult(true, null)
      }
    }
    return _openDelegate
  }

  protected override fun createInvalidationTracker(): InvalidationTracker {
    val _shadowTablesMap: MutableMap<String, String> = mutableMapOf()
    val _viewTables: MutableMap<String, Set<String>> = mutableMapOf()
    return InvalidationTracker(this, _shadowTablesMap, _viewTables, "cart_items", "search_history")
  }

  public override fun clearAllTables() {
    super.performClear(false, "cart_items", "search_history")
  }

  protected override fun getRequiredTypeConverterClasses(): Map<KClass<*>, List<KClass<*>>> {
    val _typeConvertersMap: MutableMap<KClass<*>, List<KClass<*>>> = mutableMapOf()
    _typeConvertersMap.put(CartDao::class, CartDao_Impl.getRequiredConverters())
    _typeConvertersMap.put(SearchHistoryDao::class, SearchHistoryDao_Impl.getRequiredConverters())
    return _typeConvertersMap
  }

  public override fun getRequiredAutoMigrationSpecClasses(): Set<KClass<out AutoMigrationSpec>> {
    val _autoMigrationSpecsSet: MutableSet<KClass<out AutoMigrationSpec>> = mutableSetOf()
    return _autoMigrationSpecsSet
  }

  public override fun createAutoMigrations(autoMigrationSpecs: Map<KClass<out AutoMigrationSpec>, AutoMigrationSpec>): List<Migration> {
    val _autoMigrations: MutableList<Migration> = mutableListOf()
    return _autoMigrations
  }

  public override fun cartDao(): CartDao = _cartDao.value

  public override fun searchHistoryDao(): SearchHistoryDao = _searchHistoryDao.value
}
