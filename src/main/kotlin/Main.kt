import java.io.File

fun main() {
    generateAsymmetricKeys("Cryptography is fun!")
}

const val pathKeyPair = "keypair"
const val pathPublicKey: String = "$pathKeyPair/publicKey"
const val pathPrivateKey: String = "$pathKeyPair/privateKey"
const val pathEncryptedText: String = "$pathKeyPair/text_encrypted.txt"
const val pathDecryptedText: String = "$pathKeyPair/text_decrypted.txt"

fun generateAsymmetricKeys(msg: String) {
    val asymmetricCryptography = AsymmetricCryptography()
    val privateKey = asymmetricCryptography.getPrivate(pathPrivateKey)
    val publicKey = asymmetricCryptography.getPublic(pathPublicKey)

    val encryptText = asymmetricCryptography.encryptText(msg, privateKey)
    val decryptText = asymmetricCryptography.decryptText(encryptText, publicKey)

    println(
        """
        |Original Message: $msg
        |Encrypted Message: $encryptText
        |Decrypted Message: $decryptText
    """.trimIndent().trimMargin()
    )

    if (File(pathDecryptedText).exists()) {
        asymmetricCryptography.encryptFile(
            asymmetricCryptography.getFileInBytes(File(pathDecryptedText)), File(pathEncryptedText), privateKey
        )
        asymmetricCryptography.decryptFile(
            asymmetricCryptography.getFileInBytes(File(pathEncryptedText)), File(pathDecryptedText), publicKey
        )
    } else {
        println("Create a file text.txt under folder $pathKeyPair")
    }
}

fun executeGenerateKeys() {
    val generateKeys: GenerateKeys
    try {
        generateKeys = GenerateKeys(1024)
        generateKeys.createKeys()
        generateKeys.writeToFile(pathPrivateKey, generateKeys.privateKey.encoded)
        generateKeys.writeToFile(pathPublicKey, generateKeys.publicKey.encoded)
    } catch (e: Exception) {
        println(e.message)
    }
}