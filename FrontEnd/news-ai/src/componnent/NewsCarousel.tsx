import * as React from "react"
import Autoplay from "embla-carousel-autoplay"

import { Card, CardContent } from "@/components/ui/card"
import {
  Carousel,
  CarouselContent,
  CarouselItem,
  CarouselNext,
  CarouselPrevious,
} from "@/components/ui/carousel"
import { useNews } from "../context/NewsViewContext";



const NewsCarousel = ()=> {
  const plugin = React.useRef(
    Autoplay({ delay: 5000, stopOnInteraction: false })
  )
  const { newsView } = useNews();
  
 
  


  return (
    
    <Carousel
      
      plugins={[plugin.current]}
      className="w-full max-w-xs relative"
      onMouseEnter={()=>plugin.current.stop()}
      onMouseLeave={()=>plugin.current.play()}
    >
      <CarouselContent>
        {Array.from(newsView).map((newsViewItem, index) => (
          <CarouselItem key={index}>
            <div className="p-1">
              <Card className="bg-card">
                <CardContent className="flex  items-center justify-center p-3">
                  <p className="text-1xl font-semibold">{newsViewItem.title}</p>
                  
                </CardContent>
                <CardContent>
                  <a href={newsViewItem.url} target="_blank" rel="noopener noreferrer">url</a>
                </CardContent>
              </Card>
            </div>
          </CarouselItem>
        ))}
      </CarouselContent>
      <CarouselPrevious className="absolute left-1/4 top--1 transform -translate-y-1/2 z-10 bg-gray-200 p-2 rounded-full shadow-md hover:bg-[#006ccc] hover:text-white  hover:ring-[3px] " />
      <CarouselNext     className="absolute right-1/4 top--1 transform -translate-y-1/2 z-10 bg-gray-200 p-2 rounded-full shadow-md hover:bg-[#006ccc] hover:text-white hover:ring-[3px] " />
    </Carousel>
    
  )
}
export default NewsCarousel;
