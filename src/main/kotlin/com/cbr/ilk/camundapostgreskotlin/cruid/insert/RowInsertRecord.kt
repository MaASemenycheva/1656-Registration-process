package com.cbr.ilk.camundapostgreskotlin.cruid.insert

import java.sql.DriverManager
import java.sql.SQLException
import java.time.LocalDateTime
import java.util.*

object RowInsertRecord {
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
                            """{
                              "WlltAdmstnReq": {
                                    "WlltOprReq": {
                                        "PltfmPtcptId": {
                                            "CIntId": {
                                                "PrvtId": "${UUID_generating()}"
                                            }
                                        },
                                        "PtcptWlltId": {
                                            "Id": "${UUID_generating()}"
                                        },
                                        "PtcptKeyCert": "MIIGYTCCBgygAwIBAgIQQGAcM"
                                    }
                              }
                            }""",
                            0
                        )
                        )
//                        "MIIGYTCCBgygAwIBAgIQQGAcMGahkKylO53EYaCXJTAMBggqhQMHAQEDAgUAMEkxCzAJBgNVBAYTAlJVMQswCQYDVQQIDAIwMDENMAsGA1UEBwwEQ0JEQzEMMAoGA1UECgwDQ0JSMRAwDgYDVQQDDAdBRE1JTkNBMB4XDTIxMTEyNjA4MTMyNVoXDTI4MDIyNjA4MTMyNVowgesxCzAJBgNVBAYTAlJVMQ0wCwYDVQQHDARDQkRDMUQwQgYDVQQKDDt0ZXN0LnJ1LmNicmRjLnBydC5QcnNuLjZiZDNmMGViLTU1MDAtNDRhZi1hNWJjLWU4ZjllZDQ3ZTkwNDFDMEEGA1UECww6dGVzdC5ydS5jYnJkYy53bHQuQ2x0LjZlOWIwZjdlLTQwZmUtNDUzNS04NTEzLTJjMmFjNDk1OWRmMDFCMEAGA1UEAww5dGVzdC5ydS5jYnJkYy5wcnQuRkkuMTg5Y2Y4NDQtMTJkMC00MDlhLThkYTUtZDEyMGVlZGU5ODE1MGYwHwYIKoUDBwEBAQEwEwYHKoUDAgIkAAYIKoUDBwEBAgIDQwAEQCNBY/o4YeOEQ0/+RQZ17vSyV3POnTdfKcwXKslXJXTA0JFXEvUer/Ztnaz3AOkXZrVIQqruAPFTL/sGUhxvqa+jggQiMIIEHjCB4wYDVR0RBIHbMIHYoIHVBgNVBAqggc0MgcrQmtC70LjQtdC90YIg0J/Qu9Cw0YLRhNC+0YDQvNGLINGG0LjRhNGA0L7QstC+0LPQviDRgNGD0LHQu9GPLCDQvtCx0YHQu9GD0LbQuNCy0LDRjtGJ0LjQudGB0Y8g0YMg0KTQuNC90LDQvdGB0L7QstC+0LPQviDQv9C+0YHRgNC10LTQvdC40LrQsCDCq3Rlc3QucnUuY2JyZGMucHJ0LkZJLjE4OWNmODQ0LTEyZDAtNDA5YS04ZGE1LWQxMjBlZWRlOTgxNcK7MA4GA1UdDwEB/wQEAwIDyDAMBgNVHRMBAf8EAjAAMCIGA1UdJQQbMBkGCisGAQQB0AQEAgEGCysGAQQB0AQHMgMBMCgGCSsGAQQB0AQEAwQbMBkGCSsGAQQB0AQFAwQMNjY1MzlGU0lSRjAxMCsGA1UdEAQkMCKADzIwMjExMTI2MDgxMzI1WoEPMjAyMzAyMjYwODEzMjVaMB0GA1UdDgQWBBT74gDkuCrL4j0EIkx5lHrw8q3oYjCCAR0GBSqFA2RwBIIBEjCCAQ4MUtCQ0J/QmiAi0KHQuNCz0L3QsNGC0YPRgNCwLdC60LvQuNC10L3RgiBMIiDQstC10YDRgdC40Y8gNiAo0LjRgdC/0L7Qu9C90LXQvdC40LUgMykMWtCQ0J/QmiAi0KHQuNCz0L3QsNGC0YPRgNCwLdGB0LXRgNGC0LjRhNC40LrQsNGCIEwiINCy0LXRgNGB0LjRjyA2ICjQuNGB0L/QvtC70L3QtdC90LjQtSAyKQwt0KHQpC8xMDAtMDAwMCDQvtGCIDMxINC00LXQutCw0LHRgNGPIDIwMjAg0LMuDC3QodCkLzEwMC0wMDAwINC+0YIgMzEg0LTQtdC60LDQsdGA0Y8gMjAyMCDQsy4wgYAGA1UdIwR5MHeAFBla6iQ9B6vjGvY0+zhLMkFtKTIvoU2kSzBJMQswCQYDVQQGEwJSVTELMAkGA1UECAwCMDAxDTALBgNVBAcMBENCREMxDDAKBgNVBAoMA0NCUjEQMA4GA1UEAwwHQURNSU5DQYIQQGAcMMWq2FwBx3yoYXlB4DCB2QYDVR0SBIHRMIHOoIGzBgNVBAqggasMgajQk9C70LDQstC90YvQuSDRhtC10L3RgtGAINC60YDQuNC/0YLQvtCz0YDQsNGE0LjRh9C10YHQutC+0Lkg0LfQsNGJ0LjRgtGLINC40L3RhNC+0YDQvNCw0YbQuNC4INCU0LXQv9Cw0YDRgtCw0LzQtdC90YLQsCDQsdC10LfQvtC/0LDRgdC90L7RgdGC0Lgg0JHQsNC90LrQsCDQoNC+0YHRgdC40LigFgYDVQQNoA8MDdCh0LXRgNC40Y8gMDEwDAYIKoUDBwEBAwIFAANBANQILIjvCPZApvqfycNZwQn2Annw32KS/eu22HH3d4EkZfwjvj/qXwW+ICnHEackj1SA1IEYqQPE+Ycvl/rQg/M=\"
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

    private fun UUID_generating(): String {
        return UUID.randomUUID().toString()
    }

    private fun generateInsert(message_uuid: UUID, payload: String, message_type: Int): String {
        return "INSERT INTO request (message_uuid, payload, created, message_type) " +
                "VALUES ('" + message_uuid + "','" + payload + "','" + LocalDateTime.now() + "','" + message_type + "')"
    }
}
