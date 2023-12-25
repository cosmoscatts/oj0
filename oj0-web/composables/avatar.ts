export function getTextAvatar(user?: User) {
  const userName = user?.userName?.trim() ?? ''
  if (userName === '')
    return 'X'
  return userName.substring(0, 1)
}
