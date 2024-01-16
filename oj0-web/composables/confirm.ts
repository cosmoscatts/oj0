interface ConfirmProps {
  title?: string
  content?: string
  ok?: () => void
  okText?: string
  cancelText?: string
}

/**
 * 通用确认框
 */
export function useConfirm(opts?: ConfirmProps) {
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
      const okText = opts?.okText || '确定'
      const cancelText = opts?.cancelText || '取消'
      return h('div', { class: 'flex-y-center justify-end gap-2' }, [
        h('div', { class: 'btn-solid', onClick: onOk }, { default: () => okText }),
        h('div', { class: 'filter-saturate-0 btn-text', onClick: instance.close }, { default: () => cancelText }),
      ])
    },
  })
}
