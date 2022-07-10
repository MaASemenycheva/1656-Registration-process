package com.cbr.ilk.camundapostgreskotlin.cruid.insert

import java.sql.DriverManager
import java.sql.SQLException
import java.util.*

object RowInsertWalletRecord {
    @JvmStatic
    fun main(args: Array<String>) {
        try {
            DriverManager.getConnection(
                "jdbc:postgresql://127.0.0.1:5432/test", "postgres", "admin"
            ).use { conn ->
                conn.createStatement().use { statement ->
                    for(i in 1..5000) {
                    val row =
                        statement.executeUpdate(
                            generateInsert(
                                UUID.fromString(UUID.randomUUID().toString()),
                                UUID.fromString(UUID.randomUUID().toString()),
                                "Wallet_${generateRandomIntValue()}".toString(),
                                UUID.fromString(UUID.randomUUID().toString()),
                                0,
                                0,
                                55
                        )
                        )
                    println(row)
                    }
                }
            }
        } catch (e: SQLException) {
            System.err.format("SQL State: %s\n%s", e.sqlState, e.message)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun generateRandomIntValue(): String {
        return (0 until 100000).random().toString()
    }

    private fun generateInsert(wallet_id: UUID, origin: UUID, name: String, member_id: UUID, type_id: Int, status_id: Int, balance: Int): String {
        return "INSERT INTO wallet (wallet_id, origin, name, member_id, type_id, status_id, balance) " +
                "VALUES ('" + wallet_id + "','" + origin + "','" + name + "','" + member_id + "','" + type_id + "','" + status_id + "','" +balance + "')"
    }
}
