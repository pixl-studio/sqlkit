package sqlkit.binder.crypto

trait Crypto {
  def encrypt(s: String): String
  def decrypt(s: String): String
}
