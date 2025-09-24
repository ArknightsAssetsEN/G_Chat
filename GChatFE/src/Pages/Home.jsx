import { useNavigate } from 'react-router-dom';
import { useChats } from '../Contexts/Context';

export default function Home() {
    const navigate = useNavigate();
    const { dark, user, setUser } = useChats();

    return (
        <div>
            
        
        </div>
        
    )
}