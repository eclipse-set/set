import { expect, Page, test } from '@playwright/test'
import { loadSiteplan, setDevelopmentMode } from './utils'

async function expectScreenshot (page: Page, name: string) {
  await expect(page.locator('#side-info-container')).toHaveScreenshot(`${name}-side-info.png`, { })
}

test('show map selection', async ({ page }) => {
  await setDevelopmentMode(page, true)
  await loadSiteplan(page)

  await page.getByTitle('Kartenquelle').click()

  await expect(page.locator('.map-container')).toBeVisible()
  expectScreenshot(page, 'map-container')
})

test('show layer control', async ({ page }) => {
  await setDevelopmentMode(page, true)
  await loadSiteplan(page)

  await page.getByTitle('Ebenen verwalten').click()

  await expect(page.getByRole('heading', { name: 'Hinweis:' })).toBeVisible()
  expectScreenshot(page, 'layer-control')
})

test('show model summary control', async ({ page }) => {
  await setDevelopmentMode(page, true)
  await loadSiteplan(page)

  await page.getByTitle('Zusammenfassung').click()

  await expect(page.getByText('[Hinzugefügt, Unverändert, Entfernt, Geändert')).toBeVisible()
  await expect(page.getByText('Bahnsteige:')).toBeVisible()
  expectScreenshot(page, 'model-summary')
})

test('show settings control', async ({ page }) => {
  await setDevelopmentMode(page, true)
  await loadSiteplan(page)

  await page.getByRole('button', { name: 'settings' }).click()

  await expect(page.getByText('Einstellungen')).toBeVisible()
  expectScreenshot(page, 'settings')
})
