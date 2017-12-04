let util = {}

util.getBytesLength = function (str) {
  var totalLength = 0
  var charCode
  for (var i = 0; i < str.length; i++) {
    charCode = str.charCodeAt(i)
    if (charCode < 0x007f) {
      totalLength++
    } else if ((charCode >= 0x0080) && (charCode <= 0x07ff)) {
      totalLength += 2
    } else if ((charCode >= 0x0800) && (charCode <= 0xffff)) {
      totalLength += 3
    } else {
      totalLength += 4
    }
  }
  return totalLength
}

export default util
