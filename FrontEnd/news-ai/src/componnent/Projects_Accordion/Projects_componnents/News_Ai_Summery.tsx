import { FaGithub } from "react-icons/fa";

const NewsAiSummery = () => {
  return (
   <div className="text-[#2a3a5c] mb-4 text-left px-6 text-xl">
      <p className="text-[#2a3a5c] mb-4">
        A complete backend system designed to deliver <strong>personalized news updates</strong> to users via Email, WhatsApp, or SMS. This system tackles the challenge of information overload by providing users with relevant articles from around the world, in various languages and topics, that they might otherwise miss or spend significant time searching for.
      </p>
      <p className="text-[#2a3a5c] mb-4">
        Built with <strong>Spring Boot microservices</strong>, the architecture ensures scalability and modularity. News articles are fetched via the <strong>newsdata.io API</strong>, then meticulously filtered and summarized by the <strong>Gemini model</strong> to align with user preferences, ensuring highly relevant and concise content. Security is robustly handled by <strong>Keycloak</strong> for authentication and <strong>JWT</strong> for token management. <strong>Kafka</strong> facilitates asynchronous processing for news requests, ensuring a smooth user experience even for longer operations. <strong>PostgreSQL</strong> serves as the primary data store, and <strong>Docker</strong> is used for efficient environment management and deployment. A <strong>Spring Cloud Gateway</strong> acts as the entry point, routing requests and enforcing security.
      </p>
      <p className="text-[#2a3a5c] mb-4 text-left">
        <ul className="list-disc list-inside">
          <li ><strong>Spring Boot Microservices:</strong> Modular and scalable architecture for core functionalities.</li>
          <li><strong>Personalization (Gemini Model):</strong> Filters and summarizes news based on user preferences for highly relevant content.</li>
          <li><strong>Keycloak & JWT:</strong> Robust authentication server and token management for secure access.</li>
          <li><strong>Kafka:</strong> Enables asynchronous processing for news requests, improving system responsiveness.</li>
          <li><strong>PostgreSQL:</strong> Reliable relational database for managing all customer and news-related data.</li>
          <li><strong>Docker:</strong> Facilitates consistent development, testing, and deployment environments.</li>
          <li><strong>Spring Cloud Gateway:</strong> Handles request routing and initial token validation for microservices.</li>
          <li><strong>External API Integration:</strong> Fetches global news articles from newsdata.io.</li>
          <li><strong>Multi-channel Notifications:</strong> Delivers personalized news via Email, WhatsApp, and SMS.</li>
        </ul>
      </p>

      <a
        href="https://github.com/ohhanadavid/news-ai.git"
        target="_blank"
        rel="noopener noreferrer"
        className="inline-flex items-center text-blue-600 hover:text-blue-800 font-medium transition duration-200"
      >
        <FaGithub className="w-5 h-5 mr-2" /> View on GitHub
      </a>
    </div>
  );
}

export default NewsAiSummery;