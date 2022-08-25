package com.utsman.ayunastickermaker.services

import it.auties.whatsapp.api.ErrorHandler
import it.auties.whatsapp.api.QrHandler
import it.auties.whatsapp.api.Whatsapp
import it.auties.whatsapp.listener.OnLoggedIn
import it.auties.whatsapp.model.message.standard.ImageMessage
import it.auties.whatsapp.model.message.standard.StickerMessage
import it.auties.whatsapp.model.message.standard.TextMessage
import it.auties.whatsapp.model.message.standard.VideoMessage
import it.auties.whatsapp.util.Medias
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.suspendCancellableCoroutine
import org.springframework.stereotype.Service
import java.io.File
import java.nio.file.Files
import java.util.*
import java.util.function.Consumer
import kotlin.coroutines.resume

@Service
class WaBotServices {

    suspend fun startWaBot(): String {
        return suspendCancellableCoroutine { task ->
            val consumer = Consumer<String> {
                if (!task.isCompleted) {
                    task.resume(it)
                }
            }
            val options = Whatsapp.Options.newOptions()
                .qrHandler(QrHandler.toString(consumer))
                .errorHandler(ErrorHandler.toTerminal())
                .description("Ayuna Whatsapp Sticker Maker")
                .build()

            Whatsapp.newConnection(options)
                .addChatMessagesListener { whatsapp, chat, last ->
                    println("------ new message in listener ...")
                }
                .addNewMessageListener { whatsapp, info ->
                    val content = info.message().content()
                    println("------ new message... -> ${content.javaClass.simpleName}")

                    if (content is TextMessage) {
                        println(" ----- new chat text -> $content")
                        println(" ---- ")
                        println(" ---- ${info.chatJid().toUserJid().server.name}")
                        println(" ---- ${info.chatJid().toUserJid().server.address()}")
                        println(" ---- ${info.chatJid().toUserJid().value()}")
                        if (content.text().lowercase() == "ping") {
                            whatsapp.sendMessage(info.chatJid(), "pong!", info)
                        }
                    }

                    if (content is ImageMessage) {
                        whatsapp.sendMessage(info.chatJid(), "Ayuna will make a sticker, please wait...", info)
                        println(" ----- new image msg -> ${content.width()}")
                        val mediaUrl = content.url()
                        println(" ----- media url -> $mediaUrl")
                        println(" ----- start download ---")
                        val media = Medias.download(content, whatsapp.store().mediaConnection())
                        println(" ----- end download ---")
                        val name = UUID.randomUUID().toString()
                        val file = File("$name.jpg")
                        if (file.exists()) {
                            file.delete()
                        }

                        println(" ---- mimetype - ${content.mimetype()}")
                        Files.write(file.toPath(), media)

                        println(" ---- get thumb")

                        println(" ---- ${media.size}")

                        runBlocking {
                            println(" --- start convert")
                            val mediaWebp = generateWebp(name, "jpg")
                            println(" --- start send...")

                            val thumb = Medias.getThumbnail(media, Medias.Format.JPG)
                            val sticker = StickerMessage.newStickerMessageBuilder()
                                .mediaConnection(whatsapp.store().mediaConnection())
                                .media(mediaWebp)
                                .thumbnail(thumb)
                                .build()

                            whatsapp.sendMessage(info.chatJid(), sticker, info)
                            file.delete()
                            File(file.nameWithoutExtension + ".webp").delete()
                        }
                    }

                    if (content is VideoMessage) {
                        if (content.gifPlayback()) {
                            whatsapp.sendMessage(info.chatJid(), "Gif not yet support!", info)
                        }
                    }
                }
                .addLoggedInListener(OnLoggedIn {
                    println("loged in...")
                })
                .addSocketEventListener { whatsapp, event ->
                    println("------- socket event $event")
                }
                .addDisconnectedListener { whatsapp, reason ->
                    println(" ------- diconnect -> ${reason.name}")
                    whatsapp.reconnect()
                }
                .connect()
                .toCompletableFuture()
        }
    }

    private fun generateWebp(name: String, format: String): ByteArray {
        val fileInput = File("$name.$format")
        val fileOutput = File("$name.webp")
        val cwebpFile = File(
            "libwebp" +
                    File.separator +
                    "macos" +
                    File.separator +
                    "bin" +
                    File.separator +
                    when (format) {
                        "gif" -> "gif2webp"
                        else -> "cwebp"
                    }
        )
        val command = listOf(
            cwebpFile.absolutePath,
            "-q",
            "50",
            fileInput.absolutePath,
            "-o",
            fileOutput.absolutePath
        )

        execInherit("chmod", "755", cwebpFile.absolutePath)
        execInherit(*command.toTypedArray())
        return fileOutput.readBytes()
    }

    private fun execInherit(vararg commands: String) {
        println(" --- start -> ${commands.toList().toString().replace("[", "").replace("[", "").replace(",", "")}")
        ProcessBuilder(*commands.toList().toTypedArray())
            .redirectOutput(ProcessBuilder.Redirect.INHERIT)
            .redirectError(ProcessBuilder.Redirect.INHERIT)
            .start()
            .waitFor()
    }
}