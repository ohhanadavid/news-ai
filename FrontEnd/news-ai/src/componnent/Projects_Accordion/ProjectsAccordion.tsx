
import { Accordion, AccordionItem, AccordionTrigger, AccordionContent } from "@/components/ui/accordion";
import { cn } from "@/lib/utils"
import { FaGithub } from "react-icons/fa";

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
                    <div className="text-[#2a3a5c] mb-4 text-left px-6 text-xl">
                        <p className="text-[#2a3a5c] mb-4 " >
                            A complete backend system for delivering personalized news updates
                            via Email, WhatsApp, or SMS. Built with Spring Boot microservices, 
                            secured with Keycloak and JWT, and integrated with external services 
                            (SendGrid, Twilio, Gemini model). Includes Kafka for asynchronous processing,
                            PostgreSQL for data storage, and Docker for environment managemen
                        </p>
                        <p className="text-[#2a3a5c] mb-4 text-left" >       
                            <ul className="list-disc list-inside">
                                <li>Spring Boot</li>
                                <li>Keycloak</li>
                                <li>Kafka</li>
                                <li>PostgreSQL</li>
                                <li>Docker</li>
                                <li>JWT</li>
                                <li>Spring Cloud Gateway</li>
                            </ul>
                        </p>
                        
                        <a
                            href="https://github.com/ohhanadavid/news-ai.git"
                            target="_blank"
                            rel="noopener noreferrer"
                            className="inline-flex items-center text-blue-600 hover:text-blue-800 font-medium transition duration-200"
                            >
   
                            <FaGithub className="w-5 h-5 mr-2" />  View on GitHub
                            </a>

                    </div>
                </AccordionContent>
                <hr></hr>
            </AccordionItem>
        </Accordion>
    );
}
export default ProjectsAccordion;