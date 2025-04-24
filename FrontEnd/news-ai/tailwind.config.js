/** @type {import('tailwindcss').Config} */
export default {
  content: [
    "./index.html",
    "./src/**/*.{js,ts,jsx,tsx}",
  ],
  theme: {
    extend: {
      fontFamily: {
        'pattaya': ['Pattaya', 'sans-serif'], // שים לב לשם המחלקה - p קטנה
        'algerian': ['ALGER', 'sans-serif'],
      },
    },
  },
  plugins: [],
}