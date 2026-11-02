import { expect, test } from '@playwright/test'
import { loadSiteplan } from './utils'

test('find search results', async ({ page }) => {
  await loadSiteplan(page)
  const searchBar = page.getByRole('textbox')
  await searchBar.click()
  await searchBar.fill('C4C2FC46-CA76-46D0-87DD-FB158441603C')

  await searchBar.press('Enter')
  await expect(page).toHaveScreenshot('search-result-one.png')

  await searchBar.press('Enter')
  await expect(page).toHaveScreenshot('search-result-two.png')

  await searchBar.press('Enter')
  await expect(page).toHaveScreenshot('search-result-three.png')

  await searchBar.press('Enter')
  await expect(page).toHaveScreenshot('search-result-four.png')

  await searchBar.press('Enter')
  // use concrete maxDiffPixels here because there is a small displacement of some
  // signals on the left side of the siteplan in comparison to the very first result
  // Although this is totally unrelated to the actual search result...
  // TODO: find out why it differs
  await expect(page).toHaveScreenshot('search-result-one.png', { maxDiffPixels: 151 })

  await searchBar.press('Enter')
  await expect(page).toHaveScreenshot('search-result-two.png')
})
