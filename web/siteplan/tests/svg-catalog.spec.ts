import { expect, test } from '@playwright/test'
import { loadSiteplan } from './utils'

test('show svg katalog', async ({ page }) => {
  await loadSiteplan(page)

  await page.getByText('Symbolkatalog').click()

  await expect(page).toHaveScreenshot('svg-katalog.png')
  await expect(page.getByText('Symbolgruppe auswählen')).toBeVisible()

  await page.getByText('☰').click()
  await page.getByText('Einzelnes Signal').click()

  await expect(page.getByText('Symbolgruppe auswählen')).not.toBeVisible()
})

// TODO: Test all the symbols and signal constellations
