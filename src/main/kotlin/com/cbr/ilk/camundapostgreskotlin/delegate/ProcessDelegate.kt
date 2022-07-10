package com.cbr.ilk.camundapostgreskotlin.delegate

import com.cbr.ilk.camundapostgreskotlin.jdbc.JDBCRecord
import com.cbr.ilk.camundapostgreskotlin.model.Certificate
import com.cbr.ilk.camundapostgreskotlin.model.Member
import com.cbr.ilk.camundapostgreskotlin.model.Wallet
import org.camunda.bpm.engine.ProcessEngine
import org.camunda.bpm.engine.delegate.BpmnError
import org.camunda.bpm.engine.delegate.DelegateExecution
import org.camunda.bpm.engine.delegate.JavaDelegate
import org.camunda.bpm.engine.impl.util.json.JSONObject
import org.camunda.bpm.engine.variable.Variables
import org.camunda.spin.json.SpinJsonNode
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.util.*
import org.camunda.spin.Spin.*
import java.nio.charset.StandardCharsets
import java.sql.DriverManager
import java.sql.SQLException
import java.util.regex.Pattern


@Component
class ProcessDelegate : JavaDelegate {
    private val logger = LoggerFactory.getLogger(ProcessDelegate::class.java)
    private val processEngine: ProcessEngine? = null
    private val PROCESS_KEY = "Process_select1" //"Process_027hatc"

    val UUID_REGEX_PATTERN: Pattern =
        Pattern.compile("^[{]?[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}[}]?$")

    fun isValidUUID(str: String?): Boolean {
        return if (str == null) {
            false
        } else UUID_REGEX_PATTERN.matcher(str).matches()
    }
    fun createNewMember(prvtId: UUID) {
        try {
            DriverManager.getConnection(
                "jdbc:postgresql://127.0.0.1:5432/test", "postgres", "admin"
            ).use { conn ->
                conn.createStatement().use { statement ->
                        val row =
                            statement.executeUpdate(
                                generateInsertMember(
                                    prvtId,
                                    UUID.fromString(UUID.randomUUID().toString()),
                                    "User_${generateRandomIntValue()}",
                                    0
                                )
                            )
                        println(row)
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


    private fun generateInsertMember(member_id: UUID, origin: UUID, name: String, role_id: Int): String {
        return "INSERT INTO member (member_id, origin, name, role_id) " +
                "VALUES ('" + member_id + "','" + origin + "','" + name + "','" + role_id + "')"
    }

    fun createNewWallet(wallet_id: UUID, member_id: UUID, status_id: Int, balance: Int) {
        // auto close connection and statement
        try {
            DriverManager.getConnection(
                "jdbc:postgresql://127.0.0.1:5432/test", "postgres", "admin"
            ).use { conn ->
                conn.createStatement().use { statement ->
                        val row =
                            statement.executeUpdate(
//                            wallet_id: UUID, origin: UUID, name: String, member_id: UUID, type_id: Int, status_id: Int, balance: Int
                                generateInsertWallet(
                                    wallet_id,
                                    UUID.fromString(UUID.randomUUID().toString()),
                                    "Wallet_${generateRandomIntValue()}",
                                    member_id,
                                    0,
                                    status_id,
                                    balance

                                )
                            )
                        println(row)
                }
            }
        } catch (e: SQLException) {
            System.err.format("SQL State: %s\n%s", e.sqlState, e.message)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun generateInsertWallet(wallet_id: UUID, origin: UUID, name: String, member_id: UUID, type_id: Int, status_id: Int, balance: Int): String {
        return "INSERT INTO wallet (wallet_id, origin, name, member_id, type_id, status_id, balance) " +
                "VALUES ('" + wallet_id + "','" + origin + "','" + name + "','" + member_id + "','" + type_id + "','" + status_id + "','" +balance + "')"
    }


    fun createNewCertificate (ptcptKeyCert: String, wlltId: UUID) {
        try {
            DriverManager.getConnection(
                "jdbc:postgresql://127.0.0.1:5432/test", "postgres", "admin"
            ).use { conn ->
                conn.createStatement().use { statement ->
                    for(i in 1..5000) {
                        val row =
                            statement.executeUpdate(
                                generateInsertCertificate(
                                    UUID.fromString(UUID.randomUUID().toString()),
                                    wlltId,
                                    ptcptKeyCert
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
        catch (e: SQLException) {
            System.err.format("SQL State: %s\n%s", e.sqlState, e.message)
        }
        catch (e:Exception) {
            e.printStackTrace()
        }
    }

    private fun generateInsertCertificate(certificate_id: UUID, wallet_id: UUID, blob: String): String {
        return "INSERT INTO certificate (certificate_id, wlltId, blob) " +
                "VALUES ('" + certificate_id + "','" + wallet_id + "','"+ blob +  "')"
    }

    fun isWalletExist(wallet_id: String):Boolean {
        val sql = "SELECT * FROM wallet WHERE wallet_id = '$wallet_id'"
        var countOfObj = 0
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

    fun isCertificateExist(certificate_id: String):Boolean {
        val sql = "SELECT * FROM certificate WHERE certificate_id = '$certificate_id'"
        var countOfObj = 0
        try {
            DriverManager.getConnection(
                "jdbc:postgresql://127.0.0.1:5432/test", "postgres", "admin"
            ).use { conn ->
                conn.createStatement().use { statement ->
                    val resultSet = statement.executeQuery(sql)
                    while (resultSet.next()) {
                        val certificate_id = resultSet.getString("certificate_id")
                        val wallet_id = resultSet.getString("wallet_id")
                        val blob = resultSet.getString("blob")
                        val obj = Certificate(UUID.fromString(certificate_id), UUID.fromString(wallet_id), blob)
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

    fun getPayloadEmbeddedData (json: String): Triple<UUID, UUID, String>{
        val json: SpinJsonNode = JSON(json)
        val prvtIdProperty = json.prop("WlltAdmstnReq").prop("WlltOprReq").prop("PltfmPtcptId").prop("CIntId").prop("PrvtId")
        val prvtId = UUID. fromString(prvtIdProperty.stringValue())
        val idProperty = json.prop("WlltAdmstnReq").prop("WlltOprReq").prop("PtcptWlltId").prop("Id")
        val id = UUID. fromString(idProperty.stringValue())
        val ptcptKeyCertProperty = json.prop("WlltAdmstnReq").prop("WlltOprReq").prop("PtcptKeyCert")
        val ptcptKeyCert = ptcptKeyCertProperty.stringValue()
        return Triple(prvtId, id, ptcptKeyCert)
    }

    override fun execute(execution: DelegateExecution?) {
        var processes = JDBCRecord ()
        val processesList = processes.startProcesses(100)
        processesList.forEach { process ->
            logger.info("New_handled_process= " +
                    "message_uuid=${process.message_uuid}, " +
                    "created=${process.created} " +
                    "payload=${process.payload} " +
                    "message_type=${process.message_type}")

            val payloadTriplet = getPayloadEmbeddedData(process.payload!!)
            val prvtId = payloadTriplet.first
            val wlltId = payloadTriplet.second
            val ptcptKeyCert = payloadTriplet.third
            println("payloadTriplet.prvtId ${prvtId}")
            println("payloadTriplet.id ${wlltId}")
            println("payloadTriplet.ptcptKeyCert ${ptcptKeyCert}")
            println("=============================================================================================")
            println("isMemberExist = " + isMemberExist("3dd5ebf7-fc5b-448a-8e6b-4a7230fad9d4"))
            println("isWalletExist = " + isWalletExist("27ba6003-8b5b-43ec-a230-91e02c5b7e3b"))
            println("isCertificateExist = " + isCertificateExist("80bbec9e-e15d-41ee-8f6a-9fc06a47c152"))

            if (!isValidUUID(prvtId.toString()) || !isValidUUID(wlltId.toString()) || ptcptKeyCert!="")  {
                //member_id, wallet_id или некорректный сертификат, то выдаёи ошибку обработки, некорректный ЭС
                throw BpmnError("incorrectEMError")
            } else {

                if (isMemberExist(prvtId.toString())) {
                    // ничего не делаем
                } else {
                    //создаём новую запись в таблице processing.member
                    createNewMember(prvtId)
                }
                if (isWalletExist(wlltId.toString())) {
                    //получаем идентификатор и сохраняем в переменную wallet_id
                } else {
                    // создаем для указанного member_id в таблице processing.wallet с нулевым балансом,
                    // заполняем глобальную переменную wallet_status_id=0 (активный)
                    createNewWallet(wlltId, prvtId, 0, 0)
                }
                if (isCertificateExist(ptcptKeyCert)) {

                } else {
                    // кладётся в таблицу processing.certificate (id uuid PK, blob binary) и получается в результате id (uuid)
                    // производится привязка идентификатора сертификата к кошельку через таблицу processing.wallet_certificates
                    // (id uuid PK, wallet_id uuid FK processing.wallet, certificate_id uuid FK processing.certificate)
                    createNewCertificate(ptcptKeyCert, wlltId)
                }


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

                execution!!.processEngineServices.runtimeService.startProcessInstanceByKey(
                    PROCESS_KEY,
                    process.message_type.toString(), //processDefinitionKey
                    process.message_uuid.toString(), //businessKey
                    Variables.createVariables()
                        .putValueTyped("payload", Variables.stringValue(outputPayload)) //variables
                )
            }


        }
        logger.info("ProcessInstanceId = {}", execution?.processInstanceId)
    }
}