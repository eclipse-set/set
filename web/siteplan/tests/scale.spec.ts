import { expect, test } from '@playwright/test'
import { loadSiteplan } from './utils'

test('use scale bar', async ({ page }) => {
  await loadSiteplan(page)

  await expect(page.locator('.ol-scale-text')).toHaveText('1 : 1,000')

  await page.mouse.move(500, 500)
  await page.mouse.wheel(0, 1000)

  await expect(page.locator('.ol-scale-text')).toHaveText('1 : 2,000')
})
