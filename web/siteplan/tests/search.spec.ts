import { expect, test } from '@playwright/test'
import { loadSiteplan } from './utils'

test('find search results', async ({ page }) => {
  await loadSiteplan(page)
  const searchBar = page.getByRole('textbox')
  await searchBar.click()
  await searchBar.fill('C4C2FC46-CA76-46D0-87DD-FB158441603C')
  await searchBar.press('Enter')

  await expect(page).toHaveScreenshot('search-result.png')
  await searchBar.press('Enter')
  await expect(page).toHaveScreenshot('search-result-second.png')
})
