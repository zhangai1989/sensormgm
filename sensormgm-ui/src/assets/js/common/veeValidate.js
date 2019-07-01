var regexObj = {
  isPositiveIntegerHasZero: /^([1-9]\d*|[0]{1,1})$/, // 正整数(包括0)
  isPositiveInteger: /[^0-9]+/g,
  isInteger: /^[+]?\d*$/,
  isMobileNumber: /^1\d{10}$/, //  2018-11-19改需求 /^1[3|4|5|8|7][0-9]\d{8}$/
  isPhoneNumber: /^[0-9-()（）]{7,18}$/,
  isChineseName: /^[\u4e00-\u9fa5 ]{2,8}$/,
  isIdCardNumber: /^(^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}$)|(^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])((\d{4})|\d{3}[Xx])$)$/,
  isFloat: /^(([1-9]\d{0,9})|0)(\.\d*)?$/,
  isNature: /^(([1-9]\d{0,9})|0)(\.\d+)?$/,
  isMoney: /^((-|\+)?)(([1-9]\d{0,9})|0)(\.\d{1,2})?$/,
  isBankCard: /^[1-9]\d*$/, //  匹配首位非0正整数
  verifyCode: /^\d{6}$/,
  greaterZero: /^([1-9]\d*(\.\d*[1-9])?)|(0\.\d*[1-9])$/, //  大于0的浮点数
  positiveIntegerZero: /^\+?[1-9]\d*$/, //  大于0正整数(不包括0)
  isFloatingPointNumbersHasZero: /^\d+(\.\d+)?$/, // 大于等于0的浮点数
  isPlateNo: /^[京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领A-Z]{1}[A-Z]{1}[A-Z0-9]{4,5}[A-Z0-9挂学警港澳]{1}(,[京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领A-Z]{1}[A-Z]{1}[A-Z0-9]{4,5}[A-Z0-9挂学警港澳]{1})?$/
}

const dictionary = {
  cn: {
    messages: {
      required: () => '不能为空！',
      max: (name, length) => {
        return `不能超过${length}字符`
      },
      min: (name, length) => {
        return `请输入${length}位以上字符`
      },
      alpha_dash: () => '只能输入字母、数字或下划线!',
      alpha_num: () => '只能输入字母、数字!',
      alpha: () => '只能输入字母!',
      numeric: () => '请输入正整数!',
      max_value: (name, length) => {
        return `数字值不能超过${length}`
      },
      min_value: (name, length) => {
        return `数字值不得低于${length}`
      },
      email: () => '请输入正确的邮箱！',
      url: () => '请输入正确url地址！',
      regex: (name, val) => {
        switch (val[0].toString()) {
          case regexObj.isMobileNumber.toString():
            return `请输入正确电话号码`
          case regexObj.isPositiveInteger.toString():
            return `请输入正整数`
          case regexObj.isInteger.toString():
            return `请输入正整数`
          case regexObj.isIdCardNumber.toString():
            return `请正确输入身份证号`
          case regexObj.greaterZero.toString():
            return `请输入大于0的数字`
          case regexObj.positiveIntegerZero.toString():
            return `请输入大于0的正整数`
          case regexObj.isPositiveIntegerHasZero.toString():
            return `请输入大于等于0的正整数`
          case regexObj.isFloatingPointNumbersHasZero.toString():
            return `请输入大于等于0的数字`
          default:
            return `请输入正确格式!`
        }
      },
      between: (name, val) => {
        return `请输入${val[0]}~${val[1]}之间数字`
      },
      decimal: (name, val) => {
        return (val.length > 0) ? `只能输入小数点后${val[0]}位的小数或整数` : `只能输入小数或整数!`
      },
      confirmed: (name, val, val2) => {
        return '两次输入不一致！'
      }

    }
  }
}
export {dictionary, regexObj}
