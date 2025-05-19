/** @type {import('tailwindcss').Config} */
export default {
  content: [
    "./index.html",
    "./src/**/*.{js,ts,jsx,tsx}",
  ],
  theme: {
    extend: {
      
      colors: {
        darkBlue: '#0A0F2C',
        mediumBlue: '#153B75',
        neonBlue: '#00CFFF',
        turquoise: '#00AEEF',
        deepViolet: '#2C1F6A',
        lightBlue: '#B8EFFF',
        glowText: '#B8EFFF',
      },
      backgroundImage: {
        'brain-glow': 'linear-gradient(135deg, #153B75, #00CFFF)',
      },
    },
  },
  plugins: [],
}