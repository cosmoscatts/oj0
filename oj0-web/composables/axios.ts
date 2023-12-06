import axios from 'axios'
import type { AxiosRequestConfig, AxiosResponse, InternalAxiosRequestConfig } from 'axios'

if (import.meta.env.VITE_API_BASE_URL)
  axios.defaults.baseURL = import.meta.env.VITE_API_BASE_URL

axios.interceptors.request.use(
  (config: AxiosRequestConfig) => {
    // const token = getToken()
    // if (token) {
    //   if (!config.headers)
    //     config.headers = {}

    //   config.headers.Authorization = `Bearer ${token}`
    // }
    return config as InternalAxiosRequestConfig
  },
  error => Promise.reject(error),
)
axios.interceptors.response.use(
  (response: AxiosResponse) => {
    const res = response.data
    if (res.code !== 0) {
      Message.error({
        content: res.msg || 'Error',
        duration: 5 * 1000,
      })
      return Promise.reject(new Error(res.msg || 'Error'))
    }
    return res
  },
  (error) => {
    Message.error({
      content: error.msg || 'Request Error',
      duration: 5 * 1000,
    })
    return Promise.reject(error)
  },
)
