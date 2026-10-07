import { expect, test } from '@playwright/test'
import { loadSiteplan, setDevelopmentMode } from './utils'

test('initial loading', async ({ page }) => {
  await loadSiteplan(page)
  // Move to home button to get hover effect like "total view displaying" test
  await page.mouse.move(0, 0)
  await expect(page).toHaveScreenshot('pphn-initial-view.png')
})

test('total view displaying', async ({ page }) => {
  await loadSiteplan(page)

  await page.getByRole('button', { name: '▣' }).click()
  await expect(page).toHaveScreenshot('pphn-total-view.png')

  await page.getByRole('button', { name: '⦻' }).click()
  await page.mouse.move(0, 0) // move mouse to upper left corner again to get remove hover effect from center button
  await expect(page).toHaveScreenshot('pphn-initial-view.png')
})

test('no development mode', async ({ page }) => {
  await setDevelopmentMode(page, false)

  await loadSiteplan(page)

  await expect(page).toHaveScreenshot('pphn-no-development-mode.png')
})
