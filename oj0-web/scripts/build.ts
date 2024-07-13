import fs from 'fs-extra'
import dayjs from 'dayjs'

async function run() {
  const lastUpdate = dayjs().format('YYYY/M/DD')
  await fs.writeFile('./constants/last-update.ts', `export const LAST_UPDATED = '${lastUpdate}'\n`, 'utf-8')
}

run()
