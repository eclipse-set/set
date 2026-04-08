# Instructions

- Following Playwright test failed.
- Explain why, be concise, respect Playwright best practices.
- Provide a snippet of code with the fix, if possible.

# Test info

- Name: pphn.spec.ts >> no development mode
- Location: tests\pphn.spec.ts:36:1

# Error details

```
Error: A snapshot doesn't exist at C:\ws_intern\planpro\set\web\siteplan\tests\pphn.spec.ts-snapshots\pphn-no-development-mode-chromium-win32.png, writing actual.
```

# Page snapshot

```yaml
- generic [ref=e7]:
  - generic [ref=e8]:
    - button "map" [ref=e11]:
      - generic [ref=e12]: map
    - button "settings" [ref=e15]:
      - generic [ref=e16]: settings
  - generic [ref=e17]:
    - generic:
      - button "⇧" [ref=e22]:
        - generic [ref=e23]: ⇧
      - generic [ref=e24]:
        - button "+" [ref=e25]
        - button "–" [ref=e26]
      - generic [ref=e28] [cursor=pointer]:
        - generic [ref=e29]: "1 : 1,000"
        - generic [ref=e34]: "0"
        - generic [ref=e41]: 25 m
        - generic [ref=e45]: 50 m
      - button "⤹" [ref=e47]
      - button "⤹" [ref=e49]
      - button "Export" [ref=e51]
      - button "▣" [ref=e53]
      - button "⦻" [ref=e55]
      - button "📏" [ref=e57]
```

# Test source

```ts
  1  | import { expect, Page, test } from '@playwright/test'
  2  | import configuration from '../public/configuration.json' with { type: 'json' }
  3  | import pphn from './data/PPHN_1.10.0.3_01-02_Ibn-Z._-_2._AeM_2022-05-17_13-44_tg3.json' with { type: 'json' }
  4  | 
  5  | async function loadSiteplan (page: Page) {
  6  |   await page.route('*/**/siteplan.json', async route => {
  7  |     await route.fulfill({ json: pphn })
  8  |   })
  9  | 
  10 |   await page.goto('/')
  11 |   // ensure that no .loading animation is there anymore
  12 |   await expect(page.locator('.loading')).not.toBeVisible({ timeout: 10_000 })
  13 | }
  14 | 
  15 | const screenshotOptions = (page: Page) => ({
  16 |   mask: [page.locator('.rotate-control-container, .center-route-control-container')]
  17 | })
  18 | 
  19 | test('initial loading', async ({ page }) => {
  20 |   await loadSiteplan(page)
  21 | 
  22 |   await expect(page).toHaveScreenshot('pphn-initial-view.png', screenshotOptions(page))
  23 | })
  24 | 
  25 | test('total view displaying', async ({ page }) => {
  26 |   await loadSiteplan(page)
  27 | 
  28 |   await page.getByRole('button', { name: '▣' }).click()
  29 |   await expect(page).toHaveScreenshot('pphn-total-view.png', screenshotOptions(page))
  30 | 
  31 |   await page.getByRole('button', { name: '⦻' }).click()
  32 |   await page.mouse.move(0, 0) // move mouse to upper left corner again to get remove hover effect from center button
  33 |   await expect(page).toHaveScreenshot('pphn-initial-view.png', screenshotOptions(page))
  34 | })
  35 | 
  36 | test('no development mode', async ({ page }) => {
  37 |   await page.route('*/**/configuration.json', async route => {
  38 |     await route.fulfill({ json: { ...configuration, developmentMode: false } })
  39 |   })
  40 | 
  41 |   await loadSiteplan(page)
  42 | 
> 43 |   await expect(page).toHaveScreenshot('pphn-no-development-mode.png', screenshotOptions(page))
     |   ^ Error: A snapshot doesn't exist at C:\ws_intern\planpro\set\web\siteplan\tests\pphn.spec.ts-snapshots\pphn-no-development-mode-chromium-win32.png, writing actual.
  44 | })
  45 | 
```