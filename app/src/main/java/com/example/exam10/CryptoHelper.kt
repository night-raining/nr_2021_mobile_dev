package com.example.exam10

import android.content.Context
import android.util.Log
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey


class CryptoHelper(private val algorithm: String = "AES", private val context: Context) {
    private val TAG = "FRAGMENT_CRYPTO"
    var key: SecretKey?
    var blockSize: Int = 32
    var blockSizeTmp: Int = 32
    val keySize = 128
//    val encryptPartName = "encrypted.part."

//    var initSize: Int = 0

    init {
        val keygen: KeyGenerator = KeyGenerator.getInstance(algorithm)
        keygen.init(keySize)
        key = keygen.generateKey()
    }

//    fun encrypt(byteArray: ByteArray): ArrayList<File> {
//        val cipher: Cipher = Cipher.getInstance(algorithm)
//        cipher.init(Cipher.ENCRYPT_MODE, key)
//        val fileList: ArrayList<File> = arrayListOf()
//
//        Log.d(TAG, "Initial bytearray size - ${byteArray.size}")
//        initSize = byteArray.size
//        val bs = byteArray.inputStream()
//        val buffer = ByteArray(blockSize)
//        var counter = 1
//        var isLastBlock = false
//        while (bs.available() > 0) {
//            if (bs.available() < blockSize) {
//                blockSize = bs.available()
//                isLastBlock = true
//            }
//            bs.read(buffer, 0, blockSize)
////            Log.d(TAG, "Buffer size - ${bs.readBytes().size}")
//            val partFile = createFileWithContent(
//                if (isLastBlock) cipher.doFinal(buffer) else cipher.update(
//                    buffer
//                ), "$encryptPartName$counter"
//            )
////            Log.d(TAG, "Content of ${partFile.name} - ${partFile.readText()}")
//            counter += 1
//            fileList.add(partFile)
//        }
//
//        return fileList
//    }

    fun encryptNew(byteArray: ByteArray, encryptedFileName: String): File {

        blockSizeTmp = blockSize

        val encryptedFile = createFile(encryptedFileName)
        val fos = FileOutputStream(encryptedFile, true)
        val cipher: Cipher = Cipher.getInstance(algorithm)
        cipher.init(Cipher.ENCRYPT_MODE, key)
        for (i in 0..byteArray.size step blockSizeTmp) {
            if (byteArray.size-i<blockSizeTmp) {
                blockSizeTmp=byteArray.size-i
                val cipherText = cipher.doFinal(byteArray, i, blockSizeTmp)
                fos.write(cipherText)
                Log.d(TAG, "Block size - ${cipherText.size}")
            } else {
                val cipherText = cipher.update(byteArray, i, blockSizeTmp)
                fos.write(cipherText)
                Log.d(TAG, "Block size - ${cipherText.size}")
            }
        }
        fos.close()
        return encryptedFile
    }

    fun createFile(fileName: String): File {
        val file = File("${context.getExternalFilesDir(null)}/vault/$fileName")
        file.mkdirs()
        if (file.exists()) {
            file.delete()
        } else {
            file.createNewFile()
        }
        return file
    }


    fun createFileWithContent(content: ByteArray, fileName: String): File {
        val file = File("${context.getExternalFilesDir(null)}/vault/$fileName")
        file.mkdirs()
//        Log.d(TAG, "File ${file.absolutePath} is exists - ${file.exists()}")
        if (file.exists()) {
            file.delete()
//            Log.d(TAG, "Deleted ${file.name} - ${file.delete()}")
        } else {
            file.createNewFile()
//            Log.d(TAG, "Created ${file.name} - ${file.createNewFile()}")
        }
//        Log.d(TAG, "File ${file.absolutePath} is exists - ${file.exists()}")
        file.writeBytes(content)
//        val fos = FileOutputStream(file, false)
//        fos.write(content)
//        fos.close()
//        Log.d(TAG, "File ${file.absolutePath} is exists - ${file.exists()}")
        return file
    }

//    fun decryptNew(byteArray: ByteArray, decryptedFileName: String): File {
//        val decryptedFile = createFile(decryptedFileName)
//        val fos = FileOutputStream(decryptedFile, true)
//        val cipher: Cipher = Cipher.getInstance(algorithm)
//        cipher.init(Cipher.DECRYPT_MODE, key)
//        for (i in 0..byteArray.size step blockSize) {
//            if (byteArray.size-i<blockSize) {
//                blockSize=byteArray.size-i
//                val cipherText = cipher.doFinal(byteArray, i, blockSize)
//                fos.write(cipherText)
//                Log.d(TAG, "Block size - ${cipherText.size}")
//            } else {
//                val cipherText = cipher.update(byteArray, i, blockSize)
//                fos.write(cipherText)
//                Log.d(TAG, "Block size - ${cipherText.size}")
//            }
//        }
//        fos.close()
//        return decryptedFile
//    }

    fun decrypt(byteArray: ByteArray): ByteArray {

        blockSizeTmp = blockSize

        val bos = ByteArrayOutputStream()
        val cipher: Cipher = Cipher.getInstance(algorithm)
        cipher.init(Cipher.DECRYPT_MODE, key)
        for (i in 0..byteArray.size step blockSizeTmp) {
            if (byteArray.size-i<blockSizeTmp) {
                blockSizeTmp=byteArray.size-i
                val cipherText = cipher.doFinal(byteArray, i, blockSizeTmp)
                bos.write(cipherText)
                Log.d(TAG, "Block size - ${cipherText.size}")
            } else {
                val cipherText = cipher.update(byteArray, i, blockSizeTmp)
                bos.write(cipherText)
                Log.d(TAG, "Block size - ${cipherText.size}")
            }
        }
        bos.close()
        return bos.toByteArray()
    }

//    fun decrypt(byteArray: ByteArray, fileName: String): ByteArray {
//        Log.d(TAG, "Decrypting byte array. Size - ${byteArray.size} ")
//        val decryptCipher = Cipher.getInstance(algorithm)
//        decryptCipher.init(Cipher.DECRYPT_MODE, key)
//        return decryptCipher.doFinal(byteArray)
//    }
//todo: cyclre decrypt
//    fun decrypt(parts: ArrayList<File>): ByteArray {
////        Log.d(TAG, "Decrypting parts")
//        val decryptCipher = Cipher.getInstance(algorithm)
//        decryptCipher.init(Cipher.DECRYPT_MODE, key)
//        val partsSize = calcSize(parts)
//        var resByteArray = ByteArray(initSize)
//        for (part in parts) {
//            val partBytes = part.readBytes()
//            if (part==parts.last()) {
//                val decrBytes = decryptCipher.doFinal(partBytes)
//                resByteArray += decrBytes
//            } else {
//                val decrBytes = decryptCipher.update(partBytes)
//                resByteArray += decrBytes
//            }
////            resByteArray += if (part==parts.last()) decryptCipher.doFinal(part.readBytes()) else decryptCipher.update(part.readBytes())
////            if (part==parts.last()) {
////                Log.d(TAG, "Content of ${part.name} - ${String(decryptCipher.doFinal(part.readBytes()))}}")
////            } else {
////                Log.d(TAG, "Content of ${part.name} - ${String(decryptCipher.update(part.readBytes()))}}")
////            }
//
//        }
////        Log.d(TAG, "Parts decrypted - ${resByteArray}")
//        return resByteArray
//    }

//    private fun calcSize(parts: ArrayList<File>): Int {
//        var size = 0
//        for (part in parts) {
//            size += part.readBytes().size
//        }
//        return size
//    }

//
//    fun clear() {
//        key = null
//    }
}