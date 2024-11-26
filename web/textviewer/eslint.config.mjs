import {FlatCompat} from '@eslint/eslintrc'
import js from '@eslint/js'
import stylistic from '@stylistic/eslint-plugin'
import typescriptEslint from '@typescript-eslint/eslint-plugin'
import tsParser from '@typescript-eslint/parser'
import globals from 'globals'
import path from 'node:path'
import {fileURLToPath} from 'node:url'

const __filename = fileURLToPath(import.meta.url)
const __dirname = path.dirname(__filename)
const compat = new FlatCompat({
  baseDirectory: __dirname,
  recommendedConfig: js.configs.recommended,
  allConfig: js.configs.all
})

export default [...compat.extends('plugin:@typescript-eslint/recommended'), {
  plugins: {
    '@stylistic': stylistic,
    '@typescript-eslint': typescriptEslint
  },

  languageOptions: {
    globals: {
      ...globals.browser
    },

    parser: tsParser,
    ecmaVersion: 'latest',
    sourceType: 'module'
  },

  rules: {
    indent: [
      'error', 2, {
        SwitchCase: 1
      }
    ],
    '@typescript-eslint/no-explicit-any': ['off'],
    '@typescript-eslint/no-unused-vars': ['off'],
    quotes: ['error', 'single']
  }

}, {
  files: ['**/**.ts', '**/**.tsx', '**/**.js']
}
]
