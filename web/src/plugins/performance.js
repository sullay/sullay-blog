const getPerformanceTiming = () => {
  let performance = window.performance
  if (!performance) {
    console.log('您的浏览器不支持performance属性')
    return
  }
  let t = performance.timing
  let obj = {
    timing: performance.timing
  }
  obj.redirectTime = {
    describe: '重定向耗时',
    time: t.redirectEnd - t.redirectStart
  }
  obj.lookupDomainTime = {
    describe: 'DNS查询耗时',
    time: t.domainLookupEnd - t.domainLookupStart
  }
  obj.connectTime = {
    describe: 'TCP链接耗时',
    time: t.connectEnd - t.connectStart
  }
  obj.requestTime = {
    describe: 'HTTP请求耗时',
    time: t.responseEnd - t.responseStart
  }
  obj.domReadyTime = {
    describe: '解析dom树耗时',
    time: t.domComplete - t.domInteractive
  }
  obj.whiteTime = {
    describe: '白屏时间耗时',
    time: t.responseStart - t.navigationStart
  }
  obj.domLoadTime = {
    describe: 'DOMready时间',
    time: t.domContentLoadedEventEnd - t.navigationStart
  }

  // 页面加载完成的时间 即：onload时间
  obj.loadTime = {
    describe: '页面加载完成的时间 即：onload时间',
    time: t.loadEventEnd - t.navigationStart
  }
  obj.entries = {
    describe: '资源',
    value: performance.getEntries()
  }
  return obj
}

export default {
  install (Vue) {
    Vue.prototype.getPerformanceTiming = getPerformanceTiming
  }
}
