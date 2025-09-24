import { useContext, useEffect, useState } from 'react'
import { MaterialUISwitch } from './MaterialUISwitch';
import { useChats } from '../Contexts/Context';


export default function Theme() {
    const { dark, setDark } = useChats();

    return (
        <div className="flex justify-center">
            <MaterialUISwitch sx={{ m: 1 }} checked={dark} onChange={() => setDark(!dark)} />
        </div>
        
    )
}