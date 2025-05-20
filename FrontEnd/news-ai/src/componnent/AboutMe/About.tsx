import { Card, CardHeader, CardTitle, CardContent, CardFooter } from "@/components/ui/card";
import { Accordion, AccordionItem, AccordionTrigger, AccordionContent } from "@/components/ui/accordion";
import ProjectsAccordion from "../Projects_Accordion/ProjectsAccordion";
import { FaGithub } from "react-icons/fa";



const About = () => {
  return (
    
      <Card>
        <CardHeader>
            <CardTitle className="text-white text-3xl">About ME</CardTitle>
        </CardHeader>
        <CardContent className="space-y-2 ">
            <Accordion type="single" collapsible className="w-full">
                <AccordionItem value="item-1" className="w-full">
                    <AccordionTrigger className=" text-bold text-white  bg-transparent border-none shadow-none focus-visible:ring-0 focus-visible:border-none hover:bg-transparent focus:outline-none focus-visible:outline-none text-left text-xl font-medium py-2 flex flex-1 items-start justify-between gap-4">
                    A little about me
                    </AccordionTrigger>
                    <AccordionContent>
                        <div className="text-[#2a3a5c] mb-4 text-left px-6 text-xl">
                        <p  className="text-[#2a3a5c] mb-4 " >
                            Hello! 👋 <br/>
                            I'm David Ohhana, a backend developer with a B.Sc.
                            in Computer Science and hands-on experience building modern, scalable systems using microservices architecture.
                            I'm a fast learner, highly analytical, and passionate about solving real-world problems through clean, efficient code.
                        </p>
                        <p  className="text-[#2a3a5c] mb-4 text-left" >       
                            Throughout my academic journey and various project initiatives,
                            I’ve gained practical experience working with technologies such as Spring Boot, .NET 8, Docker, Kafka, Keycloak, PostgreSQL, and more.
                            I enjoy designing backend infrastructures, optimizing performance, and working in collaborative, fast-paced environments.
                        </p>
                        </div>
                    </AccordionContent>
                    <hr></hr>
                </AccordionItem>
                <AccordionItem value="item-2" className="w-full">
                    <AccordionTrigger className=" text-bold text-white  bg-transparent border-none shadow-none focus-visible:ring-0 focus-visible:border-none hover:bg-transparent focus:outline-none focus-visible:outline-none text-left text-xl font-medium py-2 flex flex-1 items-start justify-between gap-4">
                    My Projects
                    </AccordionTrigger>
                    <AccordionContent>
                         <ProjectsAccordion className="pl-5" />
                    </AccordionContent>
                    <hr></hr>
                </AccordionItem>
                <AccordionItem value="item-3" className="w-full">
                    <AccordionTrigger className=" text-bold text-white  bg-transparent border-none shadow-none focus-visible:ring-0 focus-visible:border-none hover:bg-transparent focus:outline-none focus-visible:outline-none text-left text-xl font-medium py-2 flex flex-1 items-start justify-between gap-4">
                    Contact Me
                    </AccordionTrigger>
                    <AccordionContent>
                        <div className="text-[#2a3a5c] mb-4 text-left px-6 text-xl">   
                            <p>📧  <a href="mailto:sustujbv167@gmail.com" className="text-blue-600 hover:underline">sustujbv167@gmail.com</a></p>
                            <p>📱 <a href="tel:0543331719" className="text-blue-600 hover:underline">054-3331719</a></p>
                            <p>💼 <a href="https://www.linkedin.com/in/david-ohhana-backend" target="_blank" className="text-blue-600 hover:underline">LinkedIn</a></p>
                            <p className="flex items-center ml-1 mt-1" > <FaGithub className="w-5 h-5 mr-2" />   <a href="https://github.com/ohhanadavid" target="_blank"  className="text-blue-600 hover:underline" > GitHub</a></p>
                        </div>
                    </AccordionContent>
                    <hr></hr>
                </AccordionItem>
                
                    
            </Accordion>
                    
        </CardContent>
        <CardFooter className="flex justify-center items-center">
                    
        </CardFooter>
    </Card>
  );
}
export default About;