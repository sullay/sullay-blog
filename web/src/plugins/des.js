import CryptoJS from 'crypto-js'

// des加密
const encryptDes = (message, key = 'sullay') => {
  var keyHex = CryptoJS.enc.Utf8.parse(key)
  var encrypted = CryptoJS.DES.encrypt(JSON.stringify(message), keyHex, {
    mode: CryptoJS.mode.ECB,
    padding: CryptoJS.pad.Pkcs7
  })
  return encrypted.toString()
}
// 解密
const decryptDes = (ciphertext, key = 'sullay') => {
  const keyHex = CryptoJS.enc.Utf8.parse(key)
  // direct decrypt ciphertext
  if (!ciphertext) {
    return ''
  }
  const decrypted = CryptoJS.DES.decrypt({
    ciphertext: CryptoJS.enc.Base64.parse(ciphertext)
  }, keyHex, {
    mode: CryptoJS.mode.ECB,
    padding: CryptoJS.pad.Pkcs7
  })
  return JSON.parse(decrypted.toString(CryptoJS.enc.Utf8))
}

export default {
  install (Vue) {
    Vue.prototype.$encDes = encryptDes
    Vue.prototype.$decDes = decryptDes
  },
  decryptDes
}
