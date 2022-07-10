package com.cbr.ilk.camundapostgreskotlin.cruid.insert

import java.sql.DriverManager
import java.sql.SQLException
import java.time.LocalDateTime
import java.util.*

object RowInsertMember {
    @JvmStatic
    fun main(args: Array<String>) {
        // auto close connection and statement
        try {
            DriverManager.getConnection(
                "jdbc:postgresql://127.0.0.1:5432/test", "postgres", "admin"
            ).use { conn ->
                conn.createStatement().use { statement ->
                    for(i in 1..100) {
                    val row =
                        statement.executeUpdate(
                            generateInsert(
                                UUID.fromString(UUID.randomUUID().toString()),
                                UUID.fromString(UUID.randomUUID().toString()),
                                "User_${generateRandomIntValue()}",
                                0
                        )
                        )
                    // rows affected
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


    private fun generateInsert(member_id: UUID, origin: UUID, name: String, role_id: Int): String {
        return "INSERT INTO member (member_id, origin, name, role_id) " +
                "VALUES ('" + member_id + "','" + origin + "','" + name + "','" + role_id + "')"
    }
}
