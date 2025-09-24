import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'
import tailwindcss from '@tailwindcss/vite'
import { VitePWA } from 'vite-plugin-pwa'

// https://vite.dev/config/
export default defineConfig({
  plugins: [
    react(),
    tailwindcss(),
    VitePWA({
      registerType: "autoUpdate",
      manifest: {
        name: "G_Chat",
        short_name: "GChat",
        description: "Realtime chat app with GChat",
        theme_color: "#0d6efd",
        background_color: "#ffffff",
        display: "standalone",
        start_url: "/",
        icons: [
          {
            src: "/LOGO_DARK.svg",
            sizes: "192x192",
            type: "image/svg",
          },
          {
            src: "/LOGO_DARK.svg",
            sizes: "512x512",
            type: "image/svg",
          },
          {
            src: "/LOGO_DARK.svg",
            sizes: "512x512",
            type: "image/svg",
            purpose: "any maskable",
          },
        ],
      },
    }),
  ],
})
