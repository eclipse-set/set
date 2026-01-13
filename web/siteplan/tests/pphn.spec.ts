import { expect, test } from '@playwright/test'
import pphn from './data/PPHN_1.10.0.3_01-02_Ibn-Z._-_2._AeM_2022-05-17_13-44_tg3.json' with { type: 'json' }

test('load pphn', async ({ page }) => {
  await page.route('*/**/siteplan.json', async route => {
    await route.fulfill({ json: pphn })
  })

  await page.goto('/')

  await expect(page).toHaveScreenshot('pphn-default.png', { timeout: 10_000 })
})
