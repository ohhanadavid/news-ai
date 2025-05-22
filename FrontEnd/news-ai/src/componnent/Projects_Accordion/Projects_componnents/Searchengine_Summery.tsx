import { FaGithub } from "react-icons/fa";

const Searchengine_Summery = () => {
  return (
      <div className="text-[#2a3a5c] mb-4 text-left px-6 text-xl">
      <p className="text-[#2a3a5c] mb-4">
        A <strong>Java-based search engine project</strong> focused on comprehensive <strong>web crawling and indexing</strong>. This system provides a <strong>REST API</strong> for initiating web crawls, retrieving crawl IDs, and monitoring the status and results of each crawling task.
      </p>
      <p className="text-[#2a3a5c] mb-4">
        The project integrates with advanced technologies for scalable and efficient operation: <strong>Redis</strong> for storing crawl status and results, <strong>Kafka</strong> for managing distributed crawling tasks and queues, and <strong>ElasticSearch</strong> for indexing collected text data, enabling powerful search capabilities. It supports running crawls as background threads, allows status updates per crawl ID, and features <strong>Swagger</strong> for API documentation and testing, alongside <strong>Docker-Compose</strong> for easy setup of all services (Kafka, Zookeeper, Redis, ElasticSearch).
      </p>
      <p className="text-[#2a3a5c] mb-4 text-left">
        <ul className="list-disc list-inside">
          <li><strong>Java & Spring Boot:</strong> Foundation for the search engine's backend services.</li>
          <li><strong>Redis:</strong> In-memory data store for high-speed access to crawl states and results.</li>
          <li><strong>Kafka:</strong> Distributed streaming platform for managing and processing crawl tasks asynchronously.</li>
          <li><strong>ElasticSearch:</strong> Powerful search and analytics engine for indexing and querying web content.</li>
          <li><strong>REST API:</strong> Provides endpoints for initiating crawls, checking status, and retrieving results.</li>
          <li><strong>Swagger:</strong> Generates interactive API documentation for easy understanding and testing.</li>
          <li><strong>Docker-Compose:</strong> Simplifies the deployment and orchestration of all integrated services.</li>
          <li><strong>Multi-threaded Crawling:</strong> Enables concurrent processing of multiple web crawling tasks.</li>
          <li><strong>Distributed Queueing:</strong> Leverages Kafka for flexible and scalable management of crawl jobs.</li>
        </ul>
      </p>

      <a
        href="https://github.com/ohhanadavid/searchEngine"
        target="_blank"
        rel="noopener noreferrer"
        className="inline-flex items-center text-blue-600 hover:text-blue-800 font-medium transition duration-200"
      >
        <FaGithub className="w-5 h-5 mr-2" /> View on GitHub
      </a>
    </div>
  );
}

export default Searchengine_Summery;