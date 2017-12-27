let handler = {}

 /**
  * data: [{name=jeffery, id=70878, headimg=http://www.xxx.com},null, null]
  */
  // oArr结构：[{roomId=195779fe-45e3-4f93-92c0-59816a27018e, userId=905372, sessionId=195779fe-45e3-4f93-92c0-59816a27018e_905372, score=0, nickname=Jeffery, headimgurl=http://wx.qlogo.cn/mmopen/vi_32/DYAIOgq83eqevDmfhVRfeibjyianWrRWYlCSkjOAhgOiaDkAnHQkib6DVSsl8u8wSLPo5FEYCr0triauYl7DqkbiaKyg/, ready=false, banker=false, ip=112.28.180.66, takeBanker=false, roomOwner=true}]
  //  res结构：[{
  //   banker: "false",
  //   headimgurl: "http://wx.qlogo.cn/mmopen/vi_32/DYAIOgq83eqevDmfhVRfeibjyianWrRWYlCSkjOAhgOiaDkAnHQkib6DVSsl8u8wSLPo5FEYCr0triauYl7DqkbiaKyg/",
  //   ip: "112.28.180.66",
  //   nickname: "Jeffery",
  //   ready: "false",
  //   roomId: "195779fe-45e3-4f93-92c0-59816a27018e",
  //   roomOwner: "true",
  //   score: "0",
  //   sessionId: "195779fe-45e3-4f93-92c0-59816a27018e_905372",
  //   takeBanker: "false",
  //   userId: "905372"
  // }]

handler.handler = function (data) {
  var oArr = []
  var sArr = []
  var eArr = []
  var res = []
  var reg = /{|}/g
  data.replace(reg, function (match, index, str) {
    if (match === '{') {
      sArr.push(index)
    } else {
      eArr.push(index + 1)
    }
  })
  sArr.forEach((item, index) => {
    oArr.push(data.slice(sArr[index], eArr[index]))
  })
  oArr.forEach((item) => {
    let obj = {}
    let arr = item.slice(1, item.length - 1).split(',')
    arr.forEach((its) => {
      obj[its.trim().split('=')[0]] = its.split('=')[1]
    })
    res.push(obj)
  })
  return res
}

handler.format = function (data) {
  return window.JSON.parse(data.replace(/[a-zA-Z0-9-]+/g, function (match, index, str) {
    return '"' + match + '"'
  }).replace(/=/g, ':'))
}

export default handler
