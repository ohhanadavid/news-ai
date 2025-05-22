
import { Accordion, AccordionItem, AccordionTrigger, AccordionContent } from "@/components/ui/accordion";
import { cn } from "@/lib/utils"
import { FaGithub } from "react-icons/fa";
import NewsAiSummery from "./Projects_componnents/News_Ai_Summery";
import TinyUrl_summery from "./Projects_componnents/TinyUrl_summery";
import Searchengine_Summery from "./Projects_componnents/Searchengine_Summery";
import Chatbot_Summery from "./Projects_componnents/Chatbot_Summery";
import Sentiment_Summery from "./Projects_componnents/Sentiment_Summery";

interface ProjectsAccordionProps {
    className?: string;
}

const ProjectsAccordion: React.FC<ProjectsAccordionProps> = ({className}) => {
    return (
        <Accordion type="single" collapsible className={cn("w-full", className)}>
            <AccordionItem value="item-1" className="w-full">
                <AccordionTrigger className=" text-bold text-white  bg-transparent border-none shadow-none focus-visible:ring-0 focus-visible:border-none hover:bg-transparent focus:outline-none focus-visible:outline-none text-left text-xl font-medium py-2 flex flex-1 items-start justify-between gap-4">
                    News Ai
                </AccordionTrigger>
                <AccordionContent>
                   <NewsAiSummery />
                </AccordionContent>
                <hr></hr>
            </AccordionItem>
             <AccordionItem value="item-2" className="w-full">
                <AccordionTrigger className=" text-bold text-white  bg-transparent border-none shadow-none focus-visible:ring-0 focus-visible:border-none hover:bg-transparent focus:outline-none focus-visible:outline-none text-left text-xl font-medium py-2 flex flex-1 items-start justify-between gap-4">
                    TinyURL
                </AccordionTrigger>
                <AccordionContent>
                   <TinyUrl_summery />
                </AccordionContent>
                <hr></hr>
            </AccordionItem>
               <AccordionItem value="item-3" className="w-full">
                <AccordionTrigger className=" text-bold text-white  bg-transparent border-none shadow-none focus-visible:ring-0 focus-visible:border-none hover:bg-transparent focus:outline-none focus-visible:outline-none text-left text-xl font-medium py-2 flex flex-1 items-start justify-between gap-4">
                    SearchEngine
                </AccordionTrigger>
                <AccordionContent>
                   <Searchengine_Summery />
                </AccordionContent>
                <hr></hr>
            </AccordionItem>
                 <AccordionItem value="item-4" className="w-full">
                <AccordionTrigger className=" text-bold text-white  bg-transparent border-none shadow-none focus-visible:ring-0 focus-visible:border-none hover:bg-transparent focus:outline-none focus-visible:outline-none text-left text-xl font-medium py-2 flex flex-1 items-start justify-between gap-4">
                    ChatBot
                </AccordionTrigger>
                <AccordionContent>
                   <Chatbot_Summery />
                </AccordionContent>
                <hr></hr>
            </AccordionItem>
                <AccordionItem value="item-5" className="w-full">
                <AccordionTrigger className=" text-bold text-white  bg-transparent border-none shadow-none focus-visible:ring-0 focus-visible:border-none hover:bg-transparent focus:outline-none focus-visible:outline-none text-left text-xl font-medium py-2 flex flex-1 items-start justify-between gap-4">
                    Sentiment
                </AccordionTrigger>
                <AccordionContent>
                   <Sentiment_Summery />
                </AccordionContent>
                <hr></hr>
            </AccordionItem>
        </Accordion>
    );
}
export default ProjectsAccordion;