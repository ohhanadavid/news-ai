
import { useAuth } from "../../context/AuthContext";
import { Card, CardContent } from "@/components/ui/card";
import NewsCarousel from "../NewsCarousel";
import "./Home.css";

const Home =  () => {
    const { user, loading } = useAuth();

    if (loading) {
        return <div>טוען...</div>;  
      }

    return (
     
       <Card className="relative w-full max-w-xl overflow-hidden bg-transform border-none shadow-none ">
        <CardContent className="flex flex-col items-center justify-center space-y-4">
        <h1 className="font-algerian text-4xl ">News-AI</h1>
        <h2 className="font-pattaya text-xl ">Welcome {user.name}!</h2>
       
        <NewsCarousel/>
        
        </CardContent>
       </Card>
       
    

    );
}
export default Home;