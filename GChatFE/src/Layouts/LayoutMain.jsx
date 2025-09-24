import React from 'react'
import { Outlet } from 'react-router-dom'
import { ChatsProvider } from '../Contexts/Context'
import Header from '../Components/Header'

export default function LayoutMain() {
  return (
    //  bg-gradient-to-b from-white to-blue-200
    <div className='flex flex-col min-h-screen'>
      <ChatsProvider>
        <div className='h-screen overflow-hidden'>
          <Header/>

          <div>
            <Outlet/>
          </div>
        </div>
      </ChatsProvider>
    </div>
  )
}