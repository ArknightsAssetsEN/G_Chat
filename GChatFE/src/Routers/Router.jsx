import React from 'react'
import { Navigate, useRoutes } from 'react-router-dom'
import LayoutMain from '../Layouts/LayoutMain'
import Home from '../Pages/Home'



export default function Router() {
    return useRoutes([
        {
            path: "/",
            // element: <LayoutMain/>,
            element: <Navigate to="/menu/home" replace/>,
        }, 
        {
            path: "/menu",
            element: <LayoutMain/>,
            children: [
                { path: "home", element: <Home /> },
            ],
        },
        // { path: "*", element: <DynamicPage /> }
    ])
}