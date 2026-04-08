# Instructions

- Following Playwright test failed.
- Explain why, be concise, respect Playwright best practices.
- Provide a snippet of code with the fix, if possible.

# Test info

- Name: pphn.spec.ts >> initial loading
- Location: tests\pphn.spec.ts:19:1

# Error details

```
Error: A snapshot doesn't exist at C:\ws_intern\planpro\set\web\siteplan\tests\pphn.spec.ts-snapshots\pphn-initial-view-chromium-win32.png, writing actual.
```

# Page snapshot

```yaml
- generic [ref=e3]:
  - list [ref=e5]:
    - listitem [ref=e6] [cursor=pointer]:
      - generic [ref=e7]: home
    - listitem [ref=e8] [cursor=pointer]:
      - generic [ref=e9]: Symbolkatalog
    - listitem [ref=e10]:
      - generic [ref=e13]:
        - textbox "Element suchen..." [ref=e14]
        - text: 0 Treffer
  - generic [ref=e18]:
    - generic [ref=e19]:
      - button "map" [ref=e22]:
        - generic [ref=e23]: map
      - button "layers" [ref=e26]:
        - generic [ref=e27]: layers
      - button "info" [ref=e30]:
        - generic [ref=e31]: info
      - button "settings" [ref=e34]:
        - generic [ref=e35]: settings
    - generic [ref=e36]:
      - generic:
        - button "⇧" [ref=e41]:
          - generic [ref=e42]: ⇧
        - generic [ref=e43]:
          - button "+" [ref=e44]
          - button "–" [ref=e45]
        - generic [ref=e47] [cursor=pointer]:
          - generic [ref=e48]: "1 : 1,000"
          - generic [ref=e53]: "0"
          - generic [ref=e60]: 25 m
          - generic [ref=e64]: 50 m
        - button "⤹" [ref=e66]
        - button "⤹" [ref=e68]
        - button "Export" [ref=e70]
        - button "▣" [ref=e72]
        - button "⦻" [ref=e74]
        - button "📏" [ref=e76]
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
> 22 |   await expect(page).toHaveScreenshot('pphn-initial-view.png', screenshotOptions(page))
     |   ^ Error: A snapshot doesn't exist at C:\ws_intern\planpro\set\web\siteplan\tests\pphn.spec.ts-snapshots\pphn-initial-view-chromium-win32.png, writing actual.
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
  43 |   await expect(page).toHaveScreenshot('pphn-no-development-mode.png', screenshotOptions(page))
  44 | })
  45 | 
```