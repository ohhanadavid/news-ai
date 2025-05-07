
import * as React from "react"
import { Check, ChevronsUpDown } from "lucide-react"

import { cn } from "@/lib/utils"
import { Button } from "@/components/ui/button"
import {
  Command,
  CommandEmpty,
  CommandGroup,
  CommandInput,
  CommandItem,
  CommandList,
} from "@/components/ui/command"
import {
  Popover,
  PopoverContentWithoutPortal,
  PopoverTrigger,
} from "@/components/ui/popover"

interface categoryListProps {
  categories: string[]
  category: string
  setCategory: (category: string) => void
  open: boolean,
    setOpen: (open:boolean) => void
}

const CategoriesList: React.FC<categoryListProps> =({ categories,category,setCategory,open,setOpen }) => {
  
    const handleItemClick = (e: React.MouseEvent, currentValue: string) => {
        
        e.stopPropagation();
        e.preventDefault();
        
        setCategory(currentValue);
        setOpen(false);
      };

  return (
    <Popover open={open} onOpenChange={setOpen} modal={true}>
      <PopoverTrigger asChild>
        <Button
          variant="outline"
          role="combobox"
          aria-expanded={open}
          className="w-[200px] justify-between"
        >
          {category
            ? categories.find((categoryFromcategories) => categoryFromcategories === category)
            : "Select category..."}
          <ChevronsUpDown className="opacity-50" />
        </Button>
      </PopoverTrigger>
      <PopoverContentWithoutPortal className="w-[200px] p-0">
        <Command>
          <CommandInput placeholder="Search category..." className="h-9" />
          <CommandList>
            <CommandEmpty>No category found.</CommandEmpty>
            <CommandGroup>
              {categories.map((categoryFromcategories) => (
                <CommandItem
                  key={categoryFromcategories}
                  value={categoryFromcategories}
                  onSelect={(currentValue) => {
                    {console.log("currentValue",currentValue)}
                    {console.log("categoryFromcategories",categoryFromcategories)}
                    // if (currentValue === category) {
                    //     setCategory(""); 
                    //   } else {
                    //     setCategory(currentValue); 
                    //   }
                    setCategory(currentValue);
                    {console.log("category",category)}
                    setOpen(false)
                  }}
                    onClick={(e) => handleItemClick(e, categoryFromcategories)}
                >
                  {categoryFromcategories}
                  <Check
                    className={cn(
                      "ml-auto",
                      category === categoryFromcategories ? "opacity-100" : "opacity-0"
                    )}
                  />
                </CommandItem>
              ))}
            </CommandGroup>
          </CommandList>
        </Command>
      </PopoverContentWithoutPortal>
    </Popover>
  )
}

export default CategoriesList;