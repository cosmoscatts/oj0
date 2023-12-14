const API_URL_ENUM = {
  UPLOAD: '/upload/file',
}

export const UploadApi = {
  upload(params: { file?: File }) {
    return useRequest.post<string, Result<string>>(API_URL_ENUM.UPLOAD, params, { headers: { 'Content-Type': 'multipart/form-data' } })
  },
}
