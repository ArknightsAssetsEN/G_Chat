import { useNavigate } from 'react-router-dom';
import { useChats } from '../Contexts/Context';

export default function Logo() {
    const navigate = useNavigate();
    const { dark, user, setUser } = useChats();

    return (
        <div>
            <div className={`flex items-center justify-center p-1 font-bold cursor-pointer text-xl`} onClick={()=>navigate("/")}>
                {dark 
                ? <img src="../LOGO_LIGHT.svg" alt="Avatar" className="h-11 w-11 object-cover"/> 
                : <img src="../LOGO_DARK.svg" alt="Avatar" className="h-11 w-11 object-cover"/>}
                <span className={"ml-2 text-xl text-blue-600 dark:text-blue-400"}>GChat</span>
                
            </div>
        
        </div>
        
    )
}