import java.io.File
import java.io.FileOutputStream
import java.security.KeyPair
import java.security.KeyPairGenerator
import java.security.PrivateKey
import java.security.PublicKey

class GenerateKeys(keyLength: Int) {

    var keyGen: KeyPairGenerator = KeyPairGenerator.getInstance("RSA")
    lateinit var pair: KeyPair
    lateinit var privateKey: PrivateKey
    lateinit var publicKey: PublicKey

    init {
        this.keyGen.initialize(keyLength)
    }

    fun createKeys() {
        this.pair = this.keyGen.generateKeyPair()
        this.privateKey = this.pair.private
        this.publicKey = this.pair.public
    }

    fun writeToFile(path: String, key: ByteArray) {
        val f = File(path)
        f.parentFile.mkdirs()

        val fos = FileOutputStream(f)
        fos.write(key)
        fos.flush()
        fos.close()
    }
}