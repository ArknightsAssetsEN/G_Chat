import Logo from './Logo';
import Theme from './Theme';

export default function Header() {

    return (
        <div>
            <div className='flex justify-between items-center p-1 text-xl font-bold cursor-pointer'>
                <div className='flex justify-center items-center'>
                    <Logo/>
                </div>
                <div>
                    <Theme/>
                </div>
                
            </div>
        
        </div>
        
    )
}