module.exports = {
  root: true,
  env: {
    node: true
  },
  extends: [
    'eslint:recommended',
    'plugin:@typescript-eslint/recommended',
    'plugin:vue/vue3-essential',
    'plugin:vue/vue3-recommended'
  ],
  parser: 'vue-eslint-parser',
  parserOptions: {
    parser: '@typescript-eslint/parser',
    ecmaVersion: '2020'
  },
  rules: {
    'no-console': process.env.NODE_ENV === 'production' ? ['warn', { allow: ['warn', 'error'] }] : 'off',
    'no-debugger': process.env.NODE_ENV === 'production' ? 'warn' : 'off',
    '@typescript-eslint/member-ordering': [
      'warn',
      { default: ['field', 'signature', 'constructor', 'method'] }
    ],
    'vue/multi-word-component-names': 'off',
    'default-case': 'warn',
    'no-case-declarations': 'off',
    '@typescript-eslint/member-delimiter-style': [
      'warn',
      {
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
    'no-constant-condition': ['error', { 'checkLoops': false }],
    'no-trailing-spaces': [
      'error', {
        'skipBlankLines': false
      }
    ],
    'no-multiple-empty-lines': [
      'error', {
        'max': 1,
        'maxEOF': 1
      }
    ],
    'eol-last': ['error', 'always'],
    'padded-blocks': [
      'error', {
        'blocks': 'never',
        'classes': 'never',
        'switches': 'never'
      }
    ],
    'indent': ['error', 2, { 'SwitchCase': 1 }],
    'quotes': ['error', 'single'],
    'no-use-before-define': [
      'error', {
        'functions': false,
        'classes': true
      }
    ],
    'padding-line-between-statements': [
      'error',
      { blankLine: 'always', prev: 'import', next: '*' },
      { blankLine: 'any', prev: 'import', next: 'import' },
      { blankLine: 'always', prev: 'if', next: '*' },
      { blankLine: 'always', prev: 'switch', next: '*' }
    ],
    'lines-between-class-members': [
      'error',
      'always',
      { 'exceptAfterSingleLine': true }
    ],
    'brace-style': ['error', '1tbs'],
    'array-bracket-spacing': ['error', 'never'],
    'arrow-parens': ['error', 'as-needed'],
    'operator-assignment': ['error', 'always'],
    'space-infix-ops': 'error',
    'keyword-spacing': [
      'error',
      { 'before': true, 'after': true }
    ],
    'semi': ['error', 'never'],
    'comma-dangle': ['error', 'never'],
    'arrow-spacing': ['warn', { 'before': true, 'after': true }],
    'max-len': [
      'error',
      {
        'code': 120,
        'tabWidth': 2,
        'comments': 90,
        'ignoreStrings': true
      }
    ],
    'spaced-comment': [
      'error', 'always', {
        'line': {
          'markers': ['/'],
          'exceptions': ['-', '+']
        },
        'block': {
          'markers': ['!'],
          'exceptions': ['*'],
          'balanced': true
        }
      }
    ],
    'newline-per-chained-call': ['warn', { 'ignoreChainWithDepth': 2 }],
    'function-paren-newline': ['warn', 'multiline-arguments'],
    'array-bracket-newline': ['warn', { 'multiline': true }]
  },
  'overrides': [
    {
      'files': ['**.ts', '**.tsx', '**.vue']
    }
  ]
}
