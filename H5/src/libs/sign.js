var Sign = {}
Sign.install = function (Vue, options) {
  Vue.prototype.$sign = function (params) {
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
