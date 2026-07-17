import { expect, test } from '@playwright/test'
import { loadSiteplan, setDevelopmentMode } from './utils'

test('show map selection', async ({ page }) => {
  await setDevelopmentMode(page, true)
  await loadSiteplan(page)

  await page.getByTitle('Kartenquelle').click()

  await expect(page.locator('.map-container')).toBeVisible()
})

test('show layer control', async ({ page }) => {
  await setDevelopmentMode(page, true)
  await loadSiteplan(page)

  await page.getByTitle('Ebenen verwalten').click()

  await expect(page.getByRole('heading', { name: 'Hinweis:' })).toBeVisible()
})

test('show model summary control', async ({ page }) => {
  await setDevelopmentMode(page, true)
  await loadSiteplan(page)

  await page.getByTitle('Zusammenfassung').click()

  await expect(page.getByText('[Hinzugefügt, Unverändert, Entfernt, Geändert')).toBeVisible()
  await expect(page.getByText('Bahnsteige:')).toBeVisible()
})

test('show settings control', async ({ page }) => {
  await setDevelopmentMode(page, true)
  await loadSiteplan(page)

  await page.getByRole('button', { name: 'settings' }).click()

  await expect(page.getByText('Einstellungen')).toBeVisible()
})
