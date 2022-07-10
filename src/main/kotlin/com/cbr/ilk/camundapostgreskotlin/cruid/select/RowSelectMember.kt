package com.cbr.ilk.camundapostgreskotlin.cruid.select

import com.cbr.ilk.camundapostgreskotlin.model.Member
import java.sql.*
import java.util.*


object RowSelectMember {

    fun isMemberExist(member_id: String):Boolean {
        val sql = "SELECT * FROM member WHERE member_id = '$member_id'"
        var countOfObj = 0
        try {
            DriverManager.getConnection(
                "jdbc:postgresql://127.0.0.1:5432/test", "postgres", "admin"
            ).use { conn ->
                        conn.createStatement().use { statement ->

                            val resultSet = statement.executeQuery(sql)

                            while (resultSet.next()) {
                                val member_id = resultSet.getString("member_id")
                                val origin = resultSet.getString("origin")
                                val name = resultSet.getString("name")
                                val role_id = resultSet.getInt("role_id")
                                val obj = Member(UUID.fromString(member_id), UUID.fromString(origin), name, role_id)
                                System.out.println(obj)
                                countOfObj = resultSet.getRow();
                            }
                            System.out.println("size = " + countOfObj)
                        }
            }
        } catch (e: SQLException) {
            System.err.format("SQL State: %s\n%s", e.sqlState, e.message)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return countOfObj != 0

    }


    @JvmStatic
    fun main(args: Array<String>) {
        print("sdslmlmsd " + isMemberExist("3dd5ebf7-fc5b-448a-8e6b-4a7230fad9d4"))
    }
}
//        val sql = "SELECT * FROM member"
//        val sql = "SELECT * FROM member WHERE member_id = '3dd5ebf7-fc5b-448a-8e6b-4a7230fad9d4'"
//        try {
//            DriverManager.getConnection(
//                "jdbc:postgresql://127.0.0.1:5432/test", "postgres", "admin"
//            ).use { conn ->
//                conn.createStatement().use { statement ->
//                    var countOfObj = 0
//                    val resultSet = statement.executeQuery(sql)
//
//                    while (resultSet.next()) {
//                        val member_id = resultSet.getString("member_id")
//                        val origin = resultSet.getString("origin")
//                        val name = resultSet.getString("name")
//                        val role_id = resultSet.getInt("role_id")
//                        val obj = Member(UUID.fromString(member_id), UUID.fromString(origin), name, role_id)
//                        System.out.println(obj)
//                        countOfObj = resultSet.getRow();
//                    }
//                    System.out.println("size = "+countOfObj)
//
//                }
//            }
//        } catch (e: SQLException) {
//            System.err.format("SQL State: %s\n%s", e.sqlState, e.message)
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
//    }