
import NewsSubscription from "@/pages/NewsSubscription";
import { useAuth } from "../context/AuthContext";
import { Card, CardContent } from "@/components/ui/card";

const Home =  () => {
    const { user, loading } = useAuth();

    if (loading) {
        return <div>טוען...</div>;  
      }

    return (
      
       <Card>
        <CardContent className="space-y-2">
        <h1 className="font-algerian text-4xl text-blue-600">News-AI</h1>
        <p className="font-pattaya text-xl text-gray-700">Welcome {user.name}!</p>
        </CardContent>
       </Card>
    

    );
}
export default Home;