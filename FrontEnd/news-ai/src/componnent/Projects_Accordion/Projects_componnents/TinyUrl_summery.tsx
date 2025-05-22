import { FaGithub } from "react-icons/fa";

const TinyUrl_summery = () => {
  return (
     <div className="text-[#2a3a5c] mb-4 text-left px-6 text-xl">
      <p className="text-[#2a3a5c] mb-4">
        A robust Java project implementing a comprehensive <strong>URL Shortener system</strong> with a <strong>REST API</strong>. It focuses on efficient URL shortening, flexible data storage, and user engagement analytics. The system generates <strong>unique, short identifiers</strong> for long URLs, and provides <strong>automatic redirection</strong> when the shortened link is accessed.
      </p>
      <p className="text-[#2a3a5c] mb-4">
        The project supports <strong>multi-database storage</strong> for optimal performance, utilizing <strong>Redis</strong> for fast, immediate URL mappings, <strong>MongoDB</strong> for user management and statistics, and <strong>Cassandra</strong> for scalable click counting. It includes a full <strong>REST API</strong> for creating links and tracking user clicks, integrates <strong>Swagger</strong> for comprehensive API documentation and testing, and uses <strong>Docker-Compose</strong> for simplified deployment of all services and dependencies.
      </p>
      <p className="text-[#2a3a5c] mb-4 text-left">
        <ul className="list-disc list-inside">
          <li><strong>Java & Spring Boot:</strong> Core development framework for robust backend services.</li>
          <li><strong>Redis:</strong> High-performance in-memory data store for quick URL lookups.</li>
          <li><strong>MongoDB:</strong> Flexible NoSQL database for managing user data and aggregated statistics.</li>
          <li><strong>Cassandra:</strong> Distributed NoSQL database optimized for high-volume, low-latency click counting.</li>
          <li><strong>REST API:</strong> Standardized interface for programmatically interacting with the URL shortener.</li>
          <li><strong>Swagger:</strong> Auto-generated API documentation and interactive testing UI.</li>
          <li><strong>Docker-Compose:</strong> Streamlines the setup and orchestration of all microservices and databases.</li>
          <li><strong>User Management:</strong> Functionality to create users and track their associated links and statistics.</li>
          <li><strong>Click Tracking:</strong> Detailed analytics on link usage, recorded per link and per user.</li>
          <li><strong>Exception Handling:</strong> Robust error management for graceful degradation and user feedback.</li>
        </ul>
      </p>

      <a
        href="https://github.com/ohhanadavid/tinyUrl"
        target="_blank"
        rel="noopener noreferrer"
        className="inline-flex items-center text-blue-600 hover:text-blue-800 font-medium transition duration-200"
      >
        <FaGithub className="w-5 h-5 mr-2" /> View on GitHub
      </a>
    </div>
  );
};


export default TinyUrl_summery;