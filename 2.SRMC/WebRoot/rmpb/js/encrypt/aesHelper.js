/**
 * aes加密辅助js
 * 基于CryptoJs-3.1.2
 * 需先导入aes.js md5.js mode-ecb.js
 */

//秘钥必须为16位
var aesKey = "wYCAapJ85Q3EMNVK";//默认秘钥

//aes加密，输入秘钥
function aesEncrypt(content,key){
	if(key == null || key.trim() == ""){
		key = aesKey;
	}
	key = CryptoJS.enc.Utf8.parse(key);
	var srcs = CryptoJS.enc.Utf8.parse(content);
	var encrypted = CryptoJS.AES.encrypt(srcs, key, {mode:CryptoJS.mode.ECB,padding: CryptoJS.pad.Pkcs7});
    return encrypted.toString();
}
//aes解密，输入秘钥
function aesDecrypt(content,key){
	if(key == null || key.trim() == ""){
		key = aesKey;
	}
	key = CryptoJS.enc.Utf8.parse(key);
	var decrypt = CryptoJS.AES.decrypt(content, key, {mode:CryptoJS.mode.ECB,padding: CryptoJS.pad.Pkcs7});
	return CryptoJS.enc.Utf8.stringify(decrypt).toString();
}