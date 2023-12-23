const API_URL_ENUM = {
  LIST: '/question/question_submit/list/page',

}

export const QuestionSubmitApi = {
  /**
   * 查询题目提交记录
   */
  list(params?: {
    questionId?: number
    language?: string
    status?: number
    userId?: number
  } & Partial<Pagination>) {
    return useRequest.post<QuestionSubmit, PageResult<QuestionSubmit>>(API_URL_ENUM.LIST, params)
  },

  /**
   * 查询用户某道题目的提交记录
   */
  listOfQuestionByUser() {

  },

  /**
   * 查询用户所有通过的题目 id 集合
   */
  listUserAcceptedQuestionIds() {

  },
}
