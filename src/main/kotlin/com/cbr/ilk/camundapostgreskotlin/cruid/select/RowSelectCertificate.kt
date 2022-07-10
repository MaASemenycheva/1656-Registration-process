package com.cbr.ilk.camundapostgreskotlin.cruid.select

import com.cbr.ilk.camundapostgreskotlin.model.Certificate
import java.sql.*
import java.util.*


object RowSelectCertificate {
    @JvmStatic
    fun main(args: Array<String>) {
        val sql = "SELECT * FROM certificate WHERE certificate_id = '80bbec9e-e15d-41ee-8f6a-9fc06a47c152'"
//        val sql = "SELECT * FROM certificate"
        try {
            DriverManager.getConnection(
                "jdbc:postgresql://127.0.0.1:5432/test", "postgres", "admin"
            ).use { conn ->
                conn.createStatement().use { statement ->
                    val resultSet = statement.executeQuery(sql)
                    while (resultSet.next()) {
//                        var certificate_id: UUID,
//                        var blob: String? = null,
                        val certificate_id = resultSet.getString("certificate_id")
                        val wallet_id = resultSet.getString("wallet_id")
                        val blob = resultSet.getString("blob")
                        val obj = Certificate(UUID.fromString(certificate_id), UUID.fromString(wallet_id), blob)
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