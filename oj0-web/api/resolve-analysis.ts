const API_URL_ENUM = {
  LIST: '/resolve-analysis/list/page/vo',
  LIST_DETAIL: '/resolve-analysis/list/detail/vo',
  GET_SUMMARY: '/resolve-analysis/summary',
  GET_CHART: '/resolve-analysis/chart',
}

export const ResolveAnalysisApi = {
  list(params?: {
    selectedStatus?: number
    searchContent?: string
  } & Partial<Pagination>) {
    return useRequest.post<ResolveAnalysisItem, PageResult<ResolveAnalysisItem>>(API_URL_ENUM.LIST, params)
  },

  listDetail(params?: {
    status?: number
    questionId?: number | string
  }) {
    return useRequest.post<QuestionSubmit, Result<QuestionSubmit[]>>(API_URL_ENUM.LIST_DETAIL, params)
  },

  getSummary() {
    return useRequest.get<ResolveAnalysisSummary, Result<ResolveAnalysisSummary>>(API_URL_ENUM.GET_SUMMARY)
  },

  getChart() {
    return useRequest.get<ResolveAnalysisChartData, Result<ResolveAnalysisChartData>>(API_URL_ENUM.GET_CHART)
  },
}
