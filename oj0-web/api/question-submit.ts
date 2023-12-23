const API_URL_ENUM = {
  LIST: '/question/question_submit/list/page',
  LIST_ACCEPTED_IDS: '/question/question_submit/accepted/my',
}

export const QuestionSubmitApi = {
  /**
   * 查询题目提交记录
   */
  list(params?: {
    questionId?: string | number
    language?: string
    status?: number
    userId?: number
    sortField?: string
    sortOrder?: string
  } & Partial<Pagination>) {
    return useRequest.post<QuestionSubmit, PageResult<QuestionSubmit>>(API_URL_ENUM.LIST, params)
  },

  /**
   * 查询用户所有通过的题目 id 集合
   */
  listMyAcceptedQuestionIds() {
    return useRequest.get<number, Result<number[]>>(API_URL_ENUM.LIST_ACCEPTED_IDS)
  },
}
