import React, { useContext, useEffect, useState } from "react";

const ChatsContext = React.createContext();

export const ChatsProvider = ({ children }) => {
  const [user, setUser] = useState({displayName: "Guest", avatarUrl: "https://randomuser.me/api/portraits/men/32.jpg"});
  const [dark, setDark] = useState(true);

  useEffect(() => {
    console.log("Dark mode is now:", dark? "Dark" : "Light");
    
    if (dark) {
      document.documentElement.classList.add("dark");
    } else {
      document.documentElement.classList.remove("dark");
    }
  }, [dark]);


  return (
    // <UserContext.Provider value={{authenticated, setAuthenticated, email, setEmail, name, setName, avatar, setAvatar, token, setToken, userObject, setUserObject, }}>
    <ChatsContext.Provider value={{dark, setDark, user, setUser}}>
      {children}
    </ChatsContext.Provider>
  );
};

export const useChats = () => {
  const context = useContext(ChatsContext);
  if (!context) {
    throw new Error("useUser must be used within a UserProvider");
  }
  return context;
};
