/**
 * 通用确认框
 */
export function useConfirm(opts?: {
  title?: string
  content?: string
  ok?: () => void
}) {
  const instance = Modal.confirm({
    title: opts?.title,
    content: opts?.content || '',
    footer: () => {
      const onOk = () => {
        try {
          opts?.ok?.()
          instance.close()
        }
        catch {
          instance.close()
        }
      }
      return h('div', { class: 'flex-y-center justify-end gap-2' }, [
        h('div', { class: 'btn-solid', onClick: onOk }, { default: () => '保存' }),
        h('div', { class: 'filter-saturate-0 btn-text', onClick: instance.close }, { default: () => '取消' }),
      ])
    },
  })
}
