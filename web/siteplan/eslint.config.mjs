import { FlatCompat } from '@eslint/eslintrc'
import js from '@eslint/js'
import stylistic from '@stylistic/eslint-plugin'
import typescriptEslint from '@typescript-eslint/eslint-plugin'
import globals from 'globals'
import path from 'node:path'
import { fileURLToPath } from 'node:url'
import parser from 'vue-eslint-parser'

const __filename = fileURLToPath(import.meta.url)
const __dirname = path.dirname(__filename)
const compat = new FlatCompat({
  baseDirectory: __dirname,
  recommendedConfig: js.configs.recommended,
  allConfig: js.configs.all
})

export default [
  {
    ignores: ['**/*.js']
  }, ...compat.extends(
    'plugin:@typescript-eslint/recommended',
    'plugin:vue/vue3-essential',
    'plugin:vue/vue3-recommended'
  ), {
    plugins: {
      '@stylistic': stylistic,
      '@typescript-eslint': typescriptEslint
    },

    languageOptions: {
      globals: {
        ...globals.node
      },

      parser: parser,
      ecmaVersion: 'latest',
      sourceType: 'commonjs',

      parserOptions: {
        parser: '@typescript-eslint/parser'
      }
    },

    rules: {
      'no-console': process.env.NODE_ENV === 'production' ? ['warn', { allow: ['warn', 'error'] }] : 'off',
      'no-debugger': process.env.NODE_ENV === 'production' ? 'warn' : 'off',
      '@typescript-eslint/member-ordering': [
        'warn',
        { default: ['field', 'signature', 'constructor', 'method'] }
      ],
      '@typescript-eslint/member-ordering': [
        'warn', {
          default: ['field', 'signature', 'constructor', 'method']
        }
      ],

      'vue/multi-word-component-names': 'off',
      '@typescript-eslint/no-unused-vars': 'warn',
      'default-case': 'warn',
      'no-case-declarations': 'off',

      '@stylistic/member-delimiter-style': [
        'warn', {
          multiline: {
            delimiter: 'none',
            requireLast: true
          },

          singleline: {
            delimiter: 'comma',
            requireLast: true
          }
        }
      ],

      'space-before-function-paren': ['error', 'always'],

      'no-constant-condition': [
        'error', {
          checkLoops: false
        }
      ],

      'no-trailing-spaces': [
        'error', {
          skipBlankLines: false
        }
      ],

      'no-multiple-empty-lines': [
        'error', {
          max: 1,
          maxEOF: 1
        }
      ],

      'eol-last': ['error', 'always'],

      'padded-blocks': [
        'error', {
          blocks: 'never',
          classes: 'never',
          switches: 'never'
        }
      ],

      indent: [
        'error', 2, {
          SwitchCase: 1
        }
      ],

      quotes: ['error', 'single'],

      'no-use-before-define': [
        'error', {
          functions: false,
          classes: true
        }
      ],

      'padding-line-between-statements': [
        'error', {
          blankLine: 'always',
          prev: 'import',
          next: '*'
        }, {
          blankLine: 'any',
          prev: 'import',
          next: 'import'
        }, {
          blankLine: 'always',
          prev: 'if',
          next: '*'
        }, {
          blankLine: 'always',
          prev: 'switch',
          next: '*'
        }
      ],

      'lines-between-class-members': [
        'error', 'always', {
          exceptAfterSingleLine: true
        }
      ],

      'brace-style': ['error', '1tbs'],
      'array-bracket-spacing': ['error', 'never'],
      'arrow-parens': ['error', 'as-needed'],
      'operator-assignment': ['error', 'always'],
      'space-infix-ops': 'error',

      'keyword-spacing': [
        'error', {
          before: true,
          after: true
        }
      ],

      semi: ['error', 'never'],
      'comma-dangle': ['error', 'never'],

      'arrow-spacing': [
        'warn', {
          before: true,
          after: true
        }
      ],

      'max-len': [
        'error', {
          code: 120,
          tabWidth: 2,
          comments: 90,
          ignoreStrings: true
        }
      ],

      'spaced-comment': [
        'error', 'always', {
          line: {
            markers: ['/'],
            exceptions: ['-', '+']
          },

          block: {
            markers: ['!'],
            exceptions: ['*'],
            balanced: true
          }
        }
      ],

      'newline-per-chained-call': [
        'warn', {
          ignoreChainWithDepth: 2
        }
      ],

      'function-paren-newline': ['warn', 'multiline-arguments'],

      'array-bracket-newline': [
        'warn', {
          multiline: true
        }
      ],

      'computed-property-spacing': ['warn', 'never'],
      'space-infix-ops': ['warn'],
      'object-curly-spacing': ['warn', 'always']
    }
  }, {
    files: ['**/**.ts', '**/**.tsx', '**/**.vue']
  }
]
