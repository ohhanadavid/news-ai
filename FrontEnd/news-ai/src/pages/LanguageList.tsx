
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

interface LanguageListProps {
  languages: string[]
  language: string
  setLanguage: (language: string) => void
  open: boolean,
    setOpen: (open:boolean) => void
}

const LanguageList: React.FC<LanguageListProps> =({ languages,language,setLanguage,open,setOpen }) => {
  
    const handleItemClick = (e: React.MouseEvent, currentValue: string) => {
        
        e.stopPropagation();
        e.preventDefault();
        
        setLanguage(currentValue);
        setOpen(false);
      };

  return (
    <Popover open={open} onOpenChange={setOpen} modal={true}>
      <PopoverTrigger asChild className="ml-3 border-black">
        <Button
          variant="outline"
          role="combobox"
          aria-expanded={open}
          className="w-[200px] justify-between"
        >
          {language
            ? languages.find((languageFromLanguages) => languageFromLanguages === language)
            : "Select language..."}
          <ChevronsUpDown className="opacity-50" />
        </Button>
      </PopoverTrigger>
      <PopoverContentWithoutPortal className="w-[200px] p-0">
        <Command>
          <CommandInput placeholder="Search language..." className="h-9" />
          <CommandList>
            <CommandEmpty>No language found.</CommandEmpty>
            <CommandGroup>
              {languages.map((languageFromLanguages) => (
                <CommandItem
                  key={languageFromLanguages}
                  value={languageFromLanguages}
                  onSelect={(currentValue) => {
                    {console.log("currentValue",currentValue)}
                    {console.log("languageFromLanguages",languageFromLanguages)}
                    // if (currentValue === language) {
                    //     setLanguage(""); 
                    //   } else {
                    //     setLanguage(currentValue); 
                    //   }
                    setLanguage(currentValue);
                    {console.log("language",language)}
                    setOpen(false)
                  }}
                    onClick={(e) => handleItemClick(e, languageFromLanguages)}
                >
                  {languageFromLanguages}
                  <Check
                    className={cn(
                      "ml-auto",
                      language === languageFromLanguages ? "opacity-100" : "opacity-0"
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

export default LanguageList;