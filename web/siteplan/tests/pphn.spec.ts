import { expect, Page, test } from '@playwright/test'
import configuration from '../public/configuration.json' with { type: 'json' }
import pphn from './data/PPHN_1.10.0.3_01-02_Ibn-Z._-_2._AeM_2022-05-17_13-44_tg3.json' with { type: 'json' }

async function loadSiteplan (page: Page) {
  await page.route('*/**/siteplan.json', async route => {
    await route.fulfill({ json: pphn })
  })

  await page.goto('/')
  // ensure that no .loading animation is there anymore
  await expect(page.locator('.loading')).not.toBeVisible({ timeout: 10_000 })
}

const screenshotOptions = (page: Page) => ({
  mask: [page.locator('.rotate-control-container, .center-route-control-container')]
})

test('initial loading', async ({ page }) => {
  await loadSiteplan(page)

  await expect(page).toHaveScreenshot('pphn-initial-view.png', screenshotOptions(page))
})

test('total view displaying', async ({ page }) => {
  await loadSiteplan(page)

  await page.getByRole('button', { name: '▣' }).click()
  await expect(page).toHaveScreenshot('pphn-total-view.png', screenshotOptions(page))

  await page.getByRole('button', { name: '⦻' }).click()
  await page.mouse.move(0, 0) // move mouse to upper left corner again to get remove hover effect from center button
  await expect(page).toHaveScreenshot('pphn-initial-view.png', screenshotOptions(page))
})

test('no development mode', async ({ page }) => {
  await page.route('*/**/configuration.json', async route => {
    await route.fulfill({ json: { ...configuration, developmentMode: false } })
  })

  await loadSiteplan(page)

  await expect(page).toHaveScreenshot('pphn-no-development-mode.png', screenshotOptions(page))
})
