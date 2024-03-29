import axios from 'axios'
import type { AxiosRequestConfig, AxiosResponse, InternalAxiosRequestConfig } from 'axios'

const AXIOS_TIMEOUT = 10 * 1000

function create() {
  const _axios = axios.create({
    baseURL: `${import.meta.env.VITE_BASE_API_URL}/api`,
    timeout: AXIOS_TIMEOUT,
  })
  _axios.defaults.withCredentials = true // 传递 cookie
  _axios.interceptors.request.use(
    (config: AxiosRequestConfig) => {
      return config as InternalAxiosRequestConfig
    },
    error => Promise.reject(error),
  )
  _axios.interceptors.response.use(
    (response: AxiosResponse) => {
      return response.data as unknown as AxiosResponse<any, any>
    },
    (error) => {
      return Promise.reject(error)
    },
  )
  return _axios
}

export const useRequest = create()
