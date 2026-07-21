import { expect, test } from '@playwright/test'
import { loadSiteplan } from './utils'

test('show popup on object click', async ({ page }) => {
  await loadSiteplan(page)

  await page.waitForTimeout(1000)

  await page.mouse.click(800, 350)
  await expect(page.getByText('PZB: Keine Bezeichnung')).toBeVisible()

  await page.getByText('PZB: Keine Bezeichnung').click()
  await expect(page.getByText('GUID: AB6A6068-9DDF-4F60-8CD1-053A034A2562')).toBeVisible()

  await page.locator('#popup-back').click()
  await expect(page.locator('#featureInfoPopup')).toBeVisible()

  await page.locator('.popup-close').click()
  await expect(page.locator('#featureInfoPopup')).not.toBeVisible()
})
