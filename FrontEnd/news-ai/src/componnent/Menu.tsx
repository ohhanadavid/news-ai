
import { Button } from "@/components/ui/button";
import {
  DropdownMenu,
  DropdownMenuContent,
  DropdownMenuGroup,
  DropdownMenuItem,
  DropdownMenuLabel,
  DropdownMenuSeparator,
  DropdownMenuShortcut,
  DropdownMenuTrigger,
} from "@/components/ui/dropdown-menu";
import {
  Dialog,
} from "@/components/ui/dialog";
import { useAuth } from "../context/AuthContext";
import DeleteUserDialog from "./DeleteUserDialog";
import { IoSettingsOutline } from "react-icons/io5";
import { HiOutlinePencilSquare } from "react-icons/hi2";
import { RiLockPasswordLine } from "react-icons/ri";
import { TbLogout } from "react-icons/tb";
import { MdOutlineDeleteOutline } from "react-icons/md";


interface MenuProps {
  setActiveTab: (tab:string)=>void;
}

const Menu: React.FC<MenuProps> = ({setActiveTab}) => {
  const { user, logout} = useAuth();
  

  const title: () => string = () => {
    if (user && user.name) {
      const names = user.name.split(" ");
      let initials = "";
      for (const namePart of names) {
        if (namePart.length > 0) {
          initials += namePart.charAt(0).toUpperCase();
        }
      }
      return initials;
    }
    return "JD";
  };

 
  return (
    <Dialog>
      <DropdownMenu modal={false}>
      <DropdownMenuTrigger asChild>
           <Button
            variant="outline"
            className="bg-[#486ba3] text-white flex items-center justify-center w-10 h-10 rounded-full  hover:bg-[#2a3a5c] hover:text-white focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-black"
                     
          >
            {title()}
          </Button>
        </DropdownMenuTrigger>
        <DropdownMenuContent  className="w-56">
      <DropdownMenuLabel className="flex items-center justify-between">
  <span>Setting</span>
  <DropdownMenuShortcut>
    <IoSettingsOutline className="w-5 h-5" />
  </DropdownMenuShortcut>
</DropdownMenuLabel>
          <DropdownMenuSeparator />
          <DropdownMenuGroup>
            <DropdownMenuItem>Name: {user?.name}</DropdownMenuItem>
            <DropdownMenuItem>Email: {user?.email}</DropdownMenuItem>
            <DropdownMenuItem>Phone: {user?.phone}</DropdownMenuItem>
          </DropdownMenuGroup>
          <DropdownMenuSeparator />
          <DropdownMenuGroup>
            <DropdownMenuItem onClick={()=>{setActiveTab("UpdateUser")}}>
              Edit profile
              <DropdownMenuShortcut><HiOutlinePencilSquare /></DropdownMenuShortcut>
            </DropdownMenuItem>
            <DropdownMenuItem onClick={()=>{setActiveTab("ChangePassword")}}>
              Change password
              <DropdownMenuShortcut><RiLockPasswordLine /></DropdownMenuShortcut>
            </DropdownMenuItem>
          </DropdownMenuGroup>
          <DropdownMenuSeparator />
          <div className="flex items-center justify-between mr-2 hover:bg-gray-100">
          <DeleteUserDialog  />
          <DropdownMenuShortcut ><MdOutlineDeleteOutline className="w-5 h-5 "/></DropdownMenuShortcut>
          </div>
          <DropdownMenuSeparator />
          <DropdownMenuItem onSelect={() => logout()}>
            Log out
            <DropdownMenuShortcut><TbLogout /></DropdownMenuShortcut>
          </DropdownMenuItem>
        </DropdownMenuContent>
  
      </DropdownMenu>

    </Dialog>
  )


 
};

export default Menu;

