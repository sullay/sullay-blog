'use strict'

import Vue from 'vue'
import axios from 'axios'
import axiosConfig from '../config/axiosConf'
import Qs from 'qs'
axios.defaults.headers['key'] = localStorage.getItem('key')
// Full config:  https://github.com/axios/axios#request-config
// axios.defaults.baseURL = process.env.baseURL || process.env.apiUrl || '';
// axios.defaults.headers.common['Authorization'] = AUTH_TOKEN;
// axios.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded';

let config = {
  // baseURL: process.env.baseURL || process.env.apiUrl || ""
  // timeout: 60 * 1000, // Timeout
  // withCredentials: true, // Check cross-site Access-Control
  baseURL: axiosConfig.baseUrl,
  timeout: 10000,
  // withCredentials: true,
  headers: {
    'Cache-Control': 'no-cache,no-store,must-revalidate,max-age=-1,private',
    'Content-Type': 'application/json;charset=utf-8'
  }
}

const _axios = axios.create(config)

_axios.interceptors.request.use(
  function (config) {
    // Do something before request is sent
    if (config.method.toLowerCase() === 'get' && config.params) {
      config.paramsSerializer = function (params) {
        return Qs.stringify(params)
      }
    }
    return config
  },
  function (error) {
    // Do something with request error
    return Promise.reject(error)
  }
)

// Add a response interceptor
_axios.interceptors.response.use(
  function (response) {
    // Do something with response data
    return response
  },
  function (error) {
    // Do something with response error
    return Promise.reject(error)
  }
)

Plugin.install = function (Vue, options) {
  Vue.axios = _axios
  window.axios = _axios
  Object.defineProperties(Vue.prototype, {
    axios: {
      get () {
        return _axios
      }
    },
    $axios: {
      get () {
        return _axios
      }
    }
  })
}

Vue.use(Plugin)

export default _axios
