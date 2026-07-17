import { Page } from '@playwright/test'
import pphn from './data/PPHN_1.10.0.3_01-02_Ibn-Z._-_2._AeM_2022-05-17_13-44_tg3.json' with { type: 'json' }

export async function loadSiteplan (page: Page) {
  await page.route('*/**/siteplan.json', async route => {
    await route.fulfill({ json: pphn })
  })

  await page.goto('/')
  // ensure that .loading shows up and disappears again
  await page.locator('.loading').waitFor({ state: 'visible', timeout: 60_000 })

  await page.locator('.loading').waitFor({ state: 'hidden', timeout: 60_000 })
}
