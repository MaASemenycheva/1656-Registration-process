package com.cbr.ilk.camundapostgreskotlin.cruid.select

import com.cbr.ilk.camundapostgreskotlin.model.Wallet
import java.sql.*
import java.util.*


object RowSelectWallet {
    @JvmStatic
    fun main(args: Array<String>) {
//        val sql = "SELECT * FROM wallet"
        val sql = "SELECT * FROM wallet WHERE wallet_id = '27ba6003-8b5b-43ec-a230-91e02c5b7e3b'"
        try {
            DriverManager.getConnection(
                "jdbc:postgresql://127.0.0.1:5432/test", "postgres", "admin"
            ).use { conn ->
                conn.createStatement().use { statement ->
                    val resultSet = statement.executeQuery(sql)
                    while (resultSet.next()) {
                        val wallet_id = resultSet.getString("wallet_id")
                        val origin = resultSet.getString("origin")
                        val name = resultSet.getString("name")
                        val member_id = resultSet.getString("member_id")
                        val type_id = resultSet.getInt("type_id")
                        val status_id = resultSet.getInt("status_id")
                        val balance = resultSet.getBigDecimal("balance")
                        val obj = Wallet(UUID.fromString(wallet_id), UUID.fromString(origin), name, UUID.fromString(member_id), type_id, status_id, balance)
                        System.out.println(obj)
                    }
                }
            }
        } catch (e: SQLException) {
            System.err.format("SQL State: %s\n%s", e.sqlState, e.message)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}