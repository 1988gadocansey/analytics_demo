module.exports = {
  purge: ['./index.html', './src/**/*.{vue,js,ts,jsx,tsx}'],
   
  theme: {
    extend: {},
  },
  variants: {
    extend: {},
  },
  content: [
    'node_modules/flowbite-vue/**/*.{js,jsx,ts,tsx,vue}',
    'node_modules/flowbite/**/*.{js,jsx,ts,tsx}'
  ],
  plugins: [
      require('flowbite/plugin')
  ],
}
