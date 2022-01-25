import org.apache.commons.codec.binary.Base64
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.nio.charset.Charset
import java.nio.file.Files
import java.security.KeyFactory
import java.security.PrivateKey
import java.security.PublicKey
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.X509EncodedKeySpec
import javax.crypto.Cipher

class AsymmetricCryptography {
    val cipher: Cipher = Cipher.getInstance(Algorithm.RSA.name)

    fun getPrivate(filename: String): PrivateKey {
        val keyBytes: ByteArray = Files.readAllBytes(File(filename).toPath())
        val keySpec = PKCS8EncodedKeySpec(keyBytes)
        val keyFactory = KeyFactory.getInstance(Algorithm.RSA.name)
        return keyFactory.generatePrivate(keySpec)
    }

    fun getPublic(filename: String): PublicKey {
        val keyBytes: ByteArray = Files.readAllBytes(File(filename).toPath())
        val keySpec = X509EncodedKeySpec(keyBytes)
        val keyFactory = KeyFactory.getInstance(Algorithm.RSA.name)
        return keyFactory.generatePublic(keySpec)
    }

    fun encryptFile(input: ByteArray, output: File, key: PrivateKey) {
        this.cipher.init(Cipher.ENCRYPT_MODE, key)
        writeToFile(output, this.cipher.doFinal(input))
    }

    fun decryptFile(input: ByteArray, output: File, key: PublicKey) {
        this.cipher.init(Cipher.DECRYPT_MODE, key)
        writeToFile(output, this.cipher.doFinal(input))
    }

    fun encryptText(msg: String, key: PrivateKey): String {
        this.cipher.init(Cipher.ENCRYPT_MODE, key)
        return Base64.encodeBase64String(this.cipher.doFinal(msg.toByteArray(Charset.forName("UTF-8"))))
    }

    fun decryptText(msg: String, key: PublicKey): String {
        this.cipher.init(Cipher.DECRYPT_MODE, key)
        return String(this.cipher.doFinal(Base64.decodeBase64(msg)), Charset.forName("UTF-8"))
    }

    fun getFileInBytes(f: File): ByteArray {
        val bytes = ByteArray(f.length().toInt())
        val fileInputStream = FileInputStream(f)
        fileInputStream.read(bytes)
        fileInputStream.close()
        return bytes
    }

    private fun writeToFile(output: File, toWrite: ByteArray) {
        val fos = FileOutputStream(output)
        fos.write(toWrite)
        fos.flush()
        fos.close()
    }
}