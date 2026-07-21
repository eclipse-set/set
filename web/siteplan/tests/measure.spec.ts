import { expect, test } from '@playwright/test'
import { loadSiteplan } from './utils'

test('measure distance between two points', async ({ page }) => {
  await loadSiteplan(page)

  await page.getByTitle('Messwerkzeug').click()

  await expect(page).toHaveScreenshot('measure-initial.png')

  await page.mouse.click(400, 400)
  await page.mouse.dblclick(800, 400)

  await expect(page).toHaveScreenshot('measure.png')

  await page.getByTitle('Messwerkzeug').click()

  await expect(page).toHaveScreenshot('measure-initial.png')
})
