import { FaGithub } from "react-icons/fa";

const Chatbot_Summery = () => {
  return (
      <div className="text-[#2a3a5c] mb-4 text-left px-6 text-xl">
      <p className="text-[#2a3a5c] mb-4">
        A <strong>Java (Spring Boot) service</strong> for implementing an intelligent <strong>chatbot with a REST API</strong>. This project enables searching for movies based on ratings and audience preferences, and integrates with various external services to provide dynamic responses.
      </p>
      <p className="text-[#2a3a5c] mb-4">
        The chatbot offers an <strong>API endpoint</strong> to receive responses based on diverse parameters like product, city, and more. It includes functionality for <strong>searching Amazon products</strong> by scraping result pages and extracting names/prices using Regex. It also integrates with <strong>Yahoo Weather</strong> for real-time weather information, handling location identification, data retrieval, and custom data structures. The project features <strong>Swagger</strong> for API documentation, uses <strong>OkHttp3</strong> for HTTP requests, and demonstrates an example integration with <strong>Dialogflow (Google)</strong>, managing dynamic parameters like city and product.
      </p>
      <p className="text-[#2a3a5c] mb-4 text-left">
        <ul className="list-disc list-inside">
          <li><strong>Java & Spring Boot:</strong> Core framework for the chatbot's backend logic.</li>
          <li><strong>REST API:</strong> Provides a standardized interface for chatbot interactions.</li>
          <li><strong>Web Scraping (Regex):</strong> Extracts product information from Amazon search results.</li>
          <li><strong>External API Integrations:</strong> Connects to services like Yahoo Weather for real-time data.</li>
          <li><strong>Dialogflow Integration:</strong> Example of leveraging Google's NLU for conversational AI.</li>
          <li><strong>Swagger:</strong> Facilitates interactive API documentation and testing.</li>
          <li><strong>OkHttp3:</strong> Efficient HTTP client for making external service requests.</li>
          <li><strong>Dynamic Parameter Handling:</strong> Manages varying inputs like city or product for tailored responses.</li>
          <li><strong>Structured Bot Responses:</strong> Implements `BotResponse` objects to encapsulate replies and their sources.</li>
        </ul>
      </p>

      <a
        href="https://github.com/ohhanadavid/chatbot"
        target="_blank"
        rel="noopener noreferrer"
        className="inline-flex items-center text-blue-600 hover:text-blue-800 font-medium transition duration-200"
      >
        <FaGithub className="w-5 h-5 mr-2" /> View on GitHub
      </a>
    </div>
  );
}

export default Chatbot_Summery;