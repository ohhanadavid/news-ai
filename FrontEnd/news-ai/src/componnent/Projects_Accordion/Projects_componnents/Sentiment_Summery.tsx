import { FaGithub } from "react-icons/fa";

const Sentiment_Summery = () => {
  return (
      <div className="text-[#2a3a5c] mb-4 text-left px-6 text-xl">
      <p className="text-[#2a3a5c] mb-4">
        A <strong>Java project</strong> dedicated to <strong>sentiment (emotion) analysis of texts</strong>, primarily focusing on <strong>real-time data streams</strong> like Twitter. This system leverages <strong>reactive programming</strong>, integrates with <strong>Kafka</strong>, and utilizes <strong>Stanford NLP</strong> for sophisticated text processing.
      </p>
      <p className="text-[#2a3a5c] mb-4">
        The core functionality includes <strong>sentiment analysis</strong> of text using <strong>Stanford CoreNLP</strong>. It connects to the <strong>Twitter Stream API</strong> to filter tweets by keyword, manage the stream (start/stop), and display results. <strong>Kafka integration</strong> enables robust messaging for sending and receiving data through dedicated topics. The project exposes several <strong>REST APIs</strong> for managing the Twitter stream (`/startTwitter`, `/stopTwitter`), sending and retrieving Kafka messages (`/sendKafka`, `/getKafka`), displaying grouped messages within a time window (`/grouped`), and providing the <strong>average sentiment</strong> of messages over a given time frame (`/sentiment`, default 3 seconds).
      </p>
      <p className="text-[#2a3a5c] mb-4 text-left">
        <ul className="list-disc list-inside">
          <li><strong>Java & Spring Boot:</strong> Foundational technologies for the project's backend.</li>
          <li><strong>Stanford CoreNLP:</strong> Powerful library for Natural Language Processing and sentiment analysis.</li>
          <li><strong>Twitter Stream API:</strong> Enables real-time collection and filtering of tweets.</li>
          <li><strong>Apache Kafka:</strong> Distributed streaming platform for high-throughput, fault-tolerant messaging.</li>
          <li><strong>Project Reactor (Flux & Mono):</strong> Facilitates reactive programming for handling data streams efficiently.</li>
          <li><strong>REST API:</strong> Provides endpoints for stream control, data interaction, and sentiment analysis results.</li>
          <li><strong>Real-time Analytics:</strong> Capable of calculating average sentiment and grouping messages in live data streams.</li>
          <li><strong>Reactive Programming:</strong> Enhances responsiveness and scalability through asynchronous data processing.</li>
        </ul>
      </p>

      <a
        href="https://github.com/ohhanadavid/sentiment"
        target="_blank"
        rel="noopener noreferrer"
        className="inline-flex items-center text-blue-600 hover:text-blue-800 font-medium transition duration-200"
      >
        <FaGithub className="w-5 h-5 mr-2" /> View on GitHub
      </a>
    </div>
  );
}

export default Sentiment_Summery;