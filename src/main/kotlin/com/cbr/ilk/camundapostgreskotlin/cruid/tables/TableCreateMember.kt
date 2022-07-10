package com.cbr.ilk.camundapostgreskotlin.cruid.tables

import org.camunda.bpm.engine.impl.util.json.JSONObject
import java.sql.DriverManager
import java.sql.SQLException
import java.util.regex.Pattern


object TableCreateMember {
    private const val SQL_CREATE = ("CREATE TABLE member"
            + "("
            + " member_id uuid,"
            + " origin uuid,"
            + " name text,"
            + " role_id int not null,"
            + " PRIMARY KEY (member_id)"
            + ")")

    @JvmStatic
    fun main(args: Array<String>) {
        try {
            DriverManager.getConnection(
                "jdbc:postgresql://127.0.0.1:5432/test", "postgres", "admin"
            ).use { conn ->
                conn.createStatement().use { statement ->

                    // if DDL failed, it will raise an SQLException
                    statement.execute(SQL_CREATE)
                }
            }
        } catch (e: SQLException) {
            System.err.format("SQL State: %s\n%s", e.sqlState, e.message)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        var a = "d3aa88e2-c754-41e0-8ba6-4198a34aa0aw2"
        val UUID_REGEX_PATTERN: Pattern =
            Pattern.compile("^[{]?[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}[}]?$")
        fun isValidUUID(str: String?): Boolean {
            return if (str == null) {
                false
            } else UUID_REGEX_PATTERN.matcher(str).matches()
        }
        println("isValidUUID = "+ isValidUUID(a))

        val prvtId = "sdsds======"
        val wlltId = "dkfndfkdfndnf"
        val wlltSts = 0
        val ptcptSts = 0
        val outputPayload  = "{\n" +
                "  \"WlltAdmstnNtfctn\": {\n" +
                "    \"WlltAdmstnRpt\": {\n" +
                "      \"PltfmPtcptId\": {\n" +
                "        \"CIntId\": {\n" +
                "          \"PrvtId\": \"$prvtId\"\n" +
                "        }\n" +
                "      },\n" +
                "      \"PtcptWlltId\": {\n" +
                "        \"Id\": \"$wlltId\",\n" +
                "        \"WlltSts\": $wlltSts\n" +
                "      },\n" +
                "      \"PtcptSts\": $ptcptSts\n" +
                "    }\n" +
                "  }\n" +
                "}"
        val payload = JSONObject("""$outputPayload""")
        println("payload = "+ payload)
    }
}