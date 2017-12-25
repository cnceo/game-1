var Sign = {}
const VERSION = '0.0.4' // APP版本
const SOURCE = 'android' // APP来源（ios或android）
Sign.install = function (Vue, options) {
  Vue.prototype.$sign = function (params) {
    params.version = VERSION
    params.source = SOURCE
    let str = 'meizhuangdaka.com?&'
    let ajaxParams = ''
    Object.keys(params).sort().forEach((key) => {
      ajaxParams += key + '=' + params[key] + '&'
      str += key + '=' + params[key] + '?&'
    })
    ajaxParams += 'sign' + '=' + this.$md5(str)
    return ajaxParams
  }
}
export default Sign
