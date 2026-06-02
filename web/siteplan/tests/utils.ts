import { expect, Page } from '@playwright/test'
import pphn from './data/PPHN_1.10.0.3_01-02_Ibn-Z._-_2._AeM_2022-05-17_13-44_tg3.json' with { type: 'json' }

export async function loadSiteplan (page: Page) {
  await page.route('*/**/siteplan.json', async route => {
    await route.fulfill({ json: pphn })
  })

  await page.goto('/')
  // ensure that no .loading animation is there anymore
  await expect(page.locator('.loading')).not.toBeVisible({ timeout: 10_000 })
}
